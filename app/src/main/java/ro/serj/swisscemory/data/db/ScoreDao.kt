package ro.serj.swisscemory.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Observable

@Dao
abstract class ScoreDao {

    @Insert
    abstract suspend fun saveScore(scoreEntry: ScoreEntry)

    @Query("select * from ScoreTable")
    abstract fun getScores(): Observable<List<ScoreEntry>>
}