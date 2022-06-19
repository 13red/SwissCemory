package ro.serj.swisscemory.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ro.serj.swisscemory.data.db.ScoreRepo

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val scoreRepo: ScoreRepo,
    private val onComplete: (score: Int?) -> Unit
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass == CardsViewModel::class.java) {
            CardsViewModel(scoreRepo, onComplete) as T
        } else throw IllegalArgumentException("Unknown view model class: $modelClass")
    }
}