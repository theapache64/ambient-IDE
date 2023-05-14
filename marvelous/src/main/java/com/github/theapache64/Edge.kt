package com.github.theapache64

import com.github.theapache64.wled.WLED
import com.github.theapache64.wled.model.State
import com.trickl.palette.Palette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.Color
import java.awt.Dimension
import java.awt.GraphicsEnvironment
import java.awt.Robot
import java.awt.image.BufferedImage
import kotlin.math.roundToInt
import kotlin.system.measureTimeMillis


const val verticalLedCount = 18
const val horizontalCount = 32
val sx = 1
val ledsPerSeg = 5

val verticalSegCount = verticalLedCount / ledsPerSeg
val horizontalSegCount = horizontalCount / ledsPerSeg
val leftRange = 0..verticalLedCount
val topRange = (leftRange.last + 1)..(leftRange.last + horizontalCount)
val rightRange = topRange.last..(topRange.last + verticalLedCount)
val bottomRange = rightRange.last..(rightRange.last + horizontalCount)

fun List<Int>.groupChunked(chunk: Int): List<List<Int>> {
    val chunked = this.chunked(chunk)
    val chunkedCopy = chunked.toMutableList()
    if (chunked.size > 1) {
        chunked.apply {
            for ((index, list) in this.withIndex()) {
                if (list.size < chunk) {
                    chunkedCopy.removeAt(index)
                    chunkedCopy[index - 1] = chunkedCopy[index - 1].toMutableList().apply {
                        addAll(chunked[index])
                    }
                }
            }
        }
    }
    return chunkedCopy
}

suspend fun main() {

    val wled = WLED("wled.local")

    val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
    val screens = ge.screenDevices
    val screenRect = screens[1].defaultConfiguration.bounds
    val robot = Robot(screens[1])
    val screenSize = screenRect.size
    val cropSize = (screenSize.height * 0.1f).roundToInt()
    //

    /*val leftImage = ImageIO.read(File("left.png")) // image.getSubimage(0, 0, height, screenSize.height)
    val topImage = ImageIO.read(File("top.png")) // image.getSubimage(0, 0, screenSize.width, height)
    val rightImage = ImageIO.read(File("right.png")) // image.getSubimage(screenSize.width - height, 0, height, screenSize.height)
    val bottomImage = ImageIO.read(File("bottom.png")) // image.getSubimage(0, screenSize.height - height, screenSize.width, height)

    process(
        wled, screenSize, leftImage, topImage, rightImage, bottomImage
    )
    println("Done!")*/


    val verticalSliceHeight = screenSize.height / verticalSegCount
    val horizontalSliceWidth = screenSize.width / horizontalSegCount

    while (true) {
        val image = robot.createScreenCapture(screenRect)
        val leftImage = image.getSubimage(0, 0, cropSize, screenSize.height)
        val topImage = image.getSubimage(0, 0, screenSize.width, cropSize)
        val rightImage = image.getSubimage(screenSize.width - cropSize, 0, cropSize, screenSize.height)
        val bottomImage = image.getSubimage(0, screenSize.height - cropSize, screenSize.width, cropSize)

        /*ImageIO.write(leftImage, "png", File("left_new.png"))
        ImageIO.write(topImage, "png", File("top_new.png"))
        ImageIO.write(rightImage, "png", File("right_new.png"))
        ImageIO.write(bottomImage, "png", File("bottom_new.png"))*/


        measureTimeMillis {
            process(
                wled, screenSize, verticalSliceHeight, horizontalSliceWidth, leftImage, topImage, rightImage, bottomImage
            )
        }.let {
            println("Took $it process one frame")
        }
    }
}


var prevSegment: MutableList<State.Segment?>? = null
suspend fun process(
    wled: WLED,
    screenSize: Dimension,
    verticalSliceHeight: Int,
    horizontalSliceWidth: Int,
    leftImage: BufferedImage,
    topImage: BufferedImage,
    rightImage: BufferedImage,
    bottomImage: BufferedImage,
) {


    val leftSegMap = leftRange.toList().groupChunked(ledsPerSeg)
    val topSegMap = topRange.toList().groupChunked(ledsPerSeg)
    val rightSegMap = rightRange.toList().groupChunked(ledsPerSeg)
    val bottomSegMap = bottomRange.toList().groupChunked(ledsPerSeg)

    // process left image
    val leftMap = mutableMapOf<Int, Color?>()
    repeat(verticalSegCount) { count ->
        val position = count + 1
        val y = leftImage.height - (verticalSliceHeight * position)
        val cropped = leftImage.getSubimage(0, y, leftImage.width, verticalSliceHeight)
        leftMap[count] = Palette.from(cropped).generate().dominantSwatch?.color
    }

    // process top image
    val topMap = mutableMapOf<Int, Color?>()
    repeat(horizontalSegCount) { count ->
        val x = horizontalSliceWidth * count
        val cropped = topImage.getSubimage(x, 0, horizontalSliceWidth, topImage.height)
        topMap[count] = Palette.from(cropped).generate().dominantSwatch?.color
    }

    // process right image
    val rightMap = mutableMapOf<Int, Color?>()
    repeat(verticalSegCount) { count ->
        val y = verticalSliceHeight * count
        val cropped = rightImage.getSubimage(0, y, rightImage.width, verticalSliceHeight)
        rightMap[count] = Palette.from(cropped).generate().dominantSwatch?.color
    }

    // process bottom image
    val bottomMap = mutableMapOf<Int, Color?>()
    repeat(horizontalSegCount) { count ->
        val position = count + 1
        val x = bottomImage.width - (horizontalSliceWidth * position)
        val cropped = bottomImage.getSubimage(x, 0, horizontalSliceWidth, bottomImage.height)
        bottomMap[count] = Palette.from(cropped).generate().dominantSwatch?.color
    }


    val segment = mutableListOf<State.Segment?>().apply {
        // left segments

        val maps = listOf(
            leftMap to leftSegMap,
            topMap to topSegMap,
            rightMap to rightSegMap,
            bottomMap to bottomSegMap
        )

        for ((map, segMap) in maps) {
            for ((segNo, color) in map) {
                if (color != null) {
                    add(
                        State.Segment(
                            start = segMap[segNo].first(),
                            stop = segMap[segNo].last() + 1,
                            len = segMap[segNo].count(),
                            col = listOf(
                                listOf(
                                    color.red,
                                    color.green,
                                    color.blue
                                )
                            ),
                            sx = sx
                        )
                    )
                }
            }
        }
    }

    if (prevSegment == segment) {
        // skip
        println("Skipped!")
        return
    }


    measureTimeMillis {
        wled.updateState(State(seg = segment))
    }


    prevSegment = segment


    /*try {
        val wled = WLED("wled.local")
        // loop
        while (true) {


            *//*ImageIO.write(leftImage, "png", File("left.png"))
            ImageIO.write(topImage, "png", File("top.png"))
            ImageIO.write(rightImage, "png", File("right.png"))
            ImageIO.write(bottomImage, "png", File("bottom.png"))
            *//*


        }

        // val outputFile = File("screenshot.png")
        // ImageIO.write(screenCapture, "png", outputFile)
        // println("Screen captured and saved to ${outputFile.absolutePath}")
    } catch (ex: Exception) {
        ex.printStackTrace()
    }*/
}

