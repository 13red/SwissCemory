package ro.serj.swisscemory.data.db

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "ScoreTable")
data class ScoreEntry(
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long = 0
)