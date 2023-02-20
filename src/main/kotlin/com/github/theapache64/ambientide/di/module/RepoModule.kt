package com.github.theapache64.ambientide.di.module

import com.github.theapache64.ambientide.data.repo.RulesRepo
import com.github.theapache64.ambientide.data.repo.RulesRepoImpl
import com.github.theapache64.ambientide.data.repo.WLEDRepo
import com.github.theapache64.ambientide.data.repo.WLEDRepoImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepoModule {
    @Binds abstract fun bindRulesRepo(rulesRepo: RulesRepoImpl): RulesRepo
    @Binds abstract fun bindWledRepo(wledRepo: WLEDRepoImpl): WLEDRepo
}