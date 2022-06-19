package ro.serj.swisscemory.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(ScoreEntry::class)], version = 1)
abstract class ScoreDb : RoomDatabase() {

    abstract fun getScoreDao(): ScoreDao

    companion object {
        private var INSTANCE: ScoreDb? = null

        fun getInstance(context: Context): ScoreDb {
            if (INSTANCE == null) {
                synchronized(ScoreDb::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ScoreDb::class.java, "score_db"
                    ).build()
                }
            }

            return INSTANCE ?: throw AssertionError("Cannot create ScoreEntry DB")
        }
    }
}