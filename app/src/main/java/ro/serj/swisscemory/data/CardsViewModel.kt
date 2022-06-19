package ro.serj.swisscemory.data

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.serj.swisscemory.data.db.ScoreEntry
import ro.serj.swisscemory.data.db.ScoreRepo

class CardsViewModel(
    private val scoreRepo: ScoreRepo,
    private val onComplete: (score: Int?) -> Unit
) : ViewModel(), MainViewModel {
    private var colorList = listOf(
        Color.Black,
        Color.White,
        Color.Blue,
        Color.Red,
        Color.Yellow,
        Color.Green
    )

    private val _cards = MutableLiveData(getNewCards())
    override val cards: LiveData<List<CardData>> = _cards

    private val _score = MutableLiveData(0)
    override val score: LiveData<Int> = _score

    private val _maxScore = MutableLiveData(Int.MIN_VALUE)
    override val maxScore: LiveData<Int> = _maxScore

    init {
        val cardsObservable = getScoreObservable()
        val cardsObserver = getScoreObserver()

        cardsObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(cardsObserver)
    }

    override fun cardClicked(clickedCard: CardData) {
        val cards = _cards.value?.toMutableList() ?: mutableListOf()

        val flippedCards = cards.filter { it.isFlipped && it.show }

        if (flippedCards.size == 2) {
            if (flippedCards[0].color == flippedCards[1].color) {
                flippedCards.map { card ->
                    cards[card.id] = cards[card.id].copy(show = false)
                }
                _score.value = _score.value?.plus(1)
            } else {
                flippedCards.map { card ->
                    cards[card.id] = cards[card.id].copy(isFlipped = false)
                }
                _score.value = _score.value?.minus(1)
            }
        }
        cards[clickedCard.id] = cards[clickedCard.id].copy(isFlipped = !clickedCard.isFlipped)

        if (cards.all { !it.show }) {
            onComplete(_score.value)
            _cards.value = getNewCards()
            _score.value = 0
        } else {
            _cards.value = cards
        }
    }

    private fun getNewCards(): List<CardData> {
        val newCards = mutableListOf<CardData>()
        val mixedColors = (colorList + colorList).shuffled()

        mixedColors.forEachIndexed { index, color ->
            newCards.add(CardData(index, color))
        }
        return newCards
    }

    override fun saveScore(score: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            scoreRepo.saveScore(score)
        }
    }

    private fun getScoreObserver(): Observer<List<ScoreEntry>> {
        return object : Observer<List<ScoreEntry>> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }

            override fun onNext(scores: List<ScoreEntry>) {
                scores.forEach {
                    var lastMaxScore = _maxScore.value ?: Int.MIN_VALUE
                    if (it.score > lastMaxScore) {
                        lastMaxScore = it.score
                    }
                    _maxScore.value = lastMaxScore
                }
            }
        }
    }

    private fun getScoreObservable(): Observable<List<ScoreEntry>> {
        return scoreRepo.getMaxScore()
    }
}