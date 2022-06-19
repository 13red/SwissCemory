package ro.serj.swisscemory.data.db

class ScoreRepo(private val scoreDao: ScoreDao) {

    suspend fun saveScore(score: Int) {
        scoreDao.saveScore(ScoreEntry(score))
    }

    fun getMaxScore()  = scoreDao.getScores()
}