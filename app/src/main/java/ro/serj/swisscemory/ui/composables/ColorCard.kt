package ro.serj.swisscemory.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ro.serj.swisscemory.R
import ro.serj.swisscemory.data.CardData
import ro.serj.swisscemory.ui.theme.SwissCemoryTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ColorCard(cardData: CardData, onCardClicked: (CardData) -> Unit = {}) {
    if (cardData.show) {
        Card(
            modifier = Modifier
                .size(100.dp, 200.dp)
                .padding(4.dp),
            border = BorderStroke(1.dp, Color.Black),
            onClick = {
                onCardClicked(cardData)
            }
        ) {
            if (cardData.isFlipped) {
                Card(modifier = Modifier.fillMaxSize(), backgroundColor = cardData.color) {}
            } else {
                Image(
                    painter = painterResource(R.drawable.ic_card_back),
                    contentDescription = "card",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    } else {
        Card(
            modifier = Modifier
                .size(100.dp, 200.dp)
                .padding(4.dp),
            backgroundColor = MaterialTheme.colors.background
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun ColorCardPreview() {
    SwissCemoryTheme {
        ColorCard(CardData(0, Color.Blue))
    }
}
