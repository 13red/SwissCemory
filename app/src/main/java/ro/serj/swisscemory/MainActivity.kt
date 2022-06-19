package ro.serj.swisscemory

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import ro.serj.swisscemory.data.CardsViewModel
import ro.serj.swisscemory.data.MainViewModel
import ro.serj.swisscemory.data.MainViewModelFactory
import ro.serj.swisscemory.data.db.ScoreRepo
import ro.serj.swisscemory.data.di.DaggerScoreComponent
import ro.serj.swisscemory.data.di.ScoreModule
import ro.serj.swisscemory.ui.composables.Cemory
import ro.serj.swisscemory.ui.theme.SwissCemoryTheme
import javax.inject.Inject


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var scoreRepo: ScoreRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerScoreComponent
            .builder()
            .scoreModule(ScoreModule(applicationContext))
            .build()
            .inject(this)

        val mainViewModelFactory = MainViewModelFactory(scoreRepo) {
            saveScore(it)
        }
        viewModel = ViewModelProvider(this, mainViewModelFactory).get(CardsViewModel::class.java)

        setContent {
            SwissCemoryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Cemory(viewModel) {
                        viewModel.cardClicked(it)
                    }
                }
            }
        }
    }

    private fun saveScore(score: Int?) {
        Toast.makeText(this, R.string.new_game, Toast.LENGTH_LONG).show()
        score?.let {
            viewModel.saveScore(score)
        }
    }
}