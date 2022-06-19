package ro.serj.swisscemory.data

import androidx.lifecycle.LiveData

interface MainViewModel {
    val cards: LiveData<List<CardData>>
    val score: LiveData<Int>
    val maxScore: LiveData<Int>

    fun cardClicked(clickedCard: CardData)
    fun saveScore(score: Int)
}