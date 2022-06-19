package ro.serj.swisscemory.data.di

import dagger.Component
import ro.serj.swisscemory.MainActivity
import ro.serj.swisscemory.data.db.ScoreRepo
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ScoreModule::class
    ]
)
interface ScoreComponent {
    fun scoreRepo(): ScoreRepo
    fun inject(mainActivity: MainActivity)
}