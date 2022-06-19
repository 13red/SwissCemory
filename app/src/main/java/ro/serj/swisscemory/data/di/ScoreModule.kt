package ro.serj.swisscemory.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ro.serj.swisscemory.data.db.ScoreDao
import ro.serj.swisscemory.data.db.ScoreDb
import ro.serj.swisscemory.data.db.ScoreRepo

@Module
class ScoreModule(private val applicationContext: Context) {

    @Provides
    fun provideContext(): Context = applicationContext

    @Provides
    fun provideScoreDb(context: Context): ScoreDb = ScoreDb.getInstance(context)

    @Provides
    fun provideScoreDao(scoreDb: ScoreDb): ScoreDao = scoreDb.getScoreDao()

    @Provides
    fun provideScoreRepo(scoreDao: ScoreDao): ScoreRepo = ScoreRepo(scoreDao)
}