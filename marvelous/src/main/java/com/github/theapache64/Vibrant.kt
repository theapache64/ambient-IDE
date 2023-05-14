import com.github.theapache64.wled.WLED
import com.github.theapache64.wled.model.State
import com.trickl.palette.Palette
import com.trickl.palette.Palette.Swatch
import kotlinx.coroutines.delay
import java.awt.*
import java.lang.Integer.toHexString


suspend fun main() {
    try {
        val wled = WLED("wled.local")

        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val screens = ge.screenDevices
        val screenRect = screens[1].defaultConfiguration.bounds
        val robot = Robot(screens[1])


        // loop
        var prevSwatch : Swatch? = null
        while (true) {
            val screenCapture = robot.createScreenCapture(screenRect)
            val vibrantSwatch = Palette.from(screenCapture).generate().vibrantSwatch
            if (vibrantSwatch != null) {
                ///println("Color(0x${Integer.toHexStringSafe(vibrantSwatch)}), // vibrantSwatch")
                if(prevSwatch==vibrantSwatch){
                    println("Skipping...")
                    continue
                }

                wled.updateState(
                    State(
                        seg = listOf(
                            State.Segment(
                                col = listOf(
                                    listOf(
                                        vibrantSwatch.color.red,
                                        vibrantSwatch.color.green,
                                        vibrantSwatch.color.blue,
                                    )
                                ),
                                sx = 4
                            )
                        )
                    )
                )
            }
            prevSwatch = vibrantSwatch


            delay(16)
        }

        // val outputFile = File("screenshot.png")
        // ImageIO.write(screenCapture, "png", outputFile)
        // println("Screen captured and saved to ${outputFile.absolutePath}")
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

object Integer {
    fun toHexStringSafe(i: Int): String? {
        return try {
            toHexString(i)
        } catch (e: Exception) {
            null
        }
    }

    fun toHexStringSafe(i: Swatch?): String? {
        if (i == null) return null
        return try {
            toHexString(i.rgb)
        } catch (e: Exception) {
            null
        }
    }
}

