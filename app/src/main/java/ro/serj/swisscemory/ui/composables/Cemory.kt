package ro.serj.swisscemory.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ro.serj.swisscemory.R
import ro.serj.swisscemory.data.CardData
import ro.serj.swisscemory.data.MainViewModel
import ro.serj.swisscemory.ui.theme.SwissCemoryTheme

@Composable
fun Cemory(mainViewModel: MainViewModel, onCardClicked: (CardData) -> Unit = {}) {
    val cards by mainViewModel.cards.observeAsState()
    val score by mainViewModel.score.observeAsState()
    val maxScore by mainViewModel.maxScore.observeAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            cards?.let {
                items(it) { item ->
                    ColorCard(item) {
                        onCardClicked(it)
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(255, 255, 255, 200))
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.score),
                color = Color.Blue,
                fontWeight = FontWeight.Bold
            )
            Text(text = " $score    ", color = Color.Blue)
            Text(
                text = stringResource(id = R.string.max_score),
                color = Color.Blue,
                fontWeight = FontWeight.Bold
            )
            Text(text = " ${if (maxScore == Int.MIN_VALUE) "-" else maxScore}", color = Color.Blue)
        }
    }
}

object TestViewModel : MainViewModel {
    override val cards: LiveData<List<CardData>>
        get() = MutableLiveData(
            listOf(
                CardData(0, Color.Black),
                CardData(1, Color.White),
                CardData(2, Color.Blue),
                CardData(3, Color.Red),
                CardData(4, Color.Yellow),
                CardData(5, Color.Green)
            )
        )
    override val score: LiveData<Int>
        get() = MutableLiveData(0)
    override val maxScore: LiveData<Int>
        get() = MutableLiveData(100)

    override fun cardClicked(clickedCard: CardData) {
    }

    override fun saveScore(score: Int) {
    }
}

@Preview(showBackground = true)
@Composable
fun CemoryPreview() {
    SwissCemoryTheme {
        Cemory(TestViewModel)
    }
}