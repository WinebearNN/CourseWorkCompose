package com.hse.courseworkcompose.presentation.ui.utill

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.hse.courseworkcompose.R


@Composable
fun TypedBar(array: Array<String>) {


    LazyRow(
        modifier = Modifier
            .padding(top = 10.dp),
        contentPadding = PaddingValues(10.dp)

    ) {
        items(array.size) { lang ->
            var clicked by remember { mutableStateOf(false) }


            Button(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .wrapContentSize(),
                onClick = {
                    clicked = !clicked
                },
                colors = ButtonDefaults.buttonColors(

                    containerColor = if (clicked) Color.Black else Color.White,
                    contentColor = if (clicked) Color.White else Color.Black

                ),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.Black),
                contentPadding = PaddingValues(5.dp),


                ) {
                Text(text = array[lang])
            }

        }
    }

}

@Composable
fun RatingStar(
    modifier: Modifier = Modifier,
    value: Float,
    reviewQuantity: Int,
    starSize: Dp,
    fontSize: TextUnit,
    paddingStarValue: Dp
) {
    val wholeStarsCount = value.toInt()
    val isHalfStar = value.toInt() < value
    val emptyStarCount = (5 - value).toInt()

    fun formatNumber(count: Int): String {
        return when {
            count < 1000 -> count.toString()
            count < 10_000 -> "%.1f тыс".format(count / 1000.0).replace(".0к", "тыс") // 1.2к
            count < 1_000_000 -> "${count / 1000} тыс"
            else -> "${count / 1_000_000}м"
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row() {
                repeat(wholeStarsCount) {
                    Image(
                        modifier = Modifier
                            .size(starSize)
                            .padding(end = 1.dp)
                            .padding(bottom = 1.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.star),
                        contentDescription = "Полная звезда"
                    )
                }
                if (isHalfStar) {
                    Image(
                        modifier = Modifier
                            .size(starSize)
                            .padding(end = 1.dp)
                            .padding(bottom = 1.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.star_half),
                        contentDescription = "Пол звезды"
                    )
                }
                repeat(emptyStarCount) {
                    Image(
                        modifier = Modifier
                            .size(starSize)
                            .padding(end = 1.dp)
                            .padding(bottom = 1.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.star_empty),
                        contentDescription = "Полная звезда"
                    )
                }
            }
            Text(
                modifier = Modifier
                    .padding(start = paddingStarValue),
                text = formatNumber(reviewQuantity),
                fontWeight = FontWeight.Medium,
                fontSize = fontSize
            )
        }


    }


}