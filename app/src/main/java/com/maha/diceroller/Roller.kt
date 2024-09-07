package com.maha.diceroller

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

//@Preview(showBackground = true)
@Composable
fun Roller(modifier: Modifier = Modifier, innerPadding: PaddingValues = PaddingValues()) {

    var scorePlay1 = remember { mutableStateOf(0) }
    var scorePlay2 = remember { mutableStateOf(0) }
    var isPlayer1Turn = remember { mutableStateOf(true) }
    var currentImage = remember { mutableStateOf(0) }
    var gameOver = remember { mutableStateOf(false) }

    val images = listOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color(0xFFE1F5FE)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (gameOver.value) {
            Text(
                text = if (scorePlay1.value > scorePlay2.value) "Player 1 Won!" else "Player 2 Won!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00BCD4),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Button(
                onClick = {
                    scorePlay1.value = 0
                    scorePlay2.value = 0
                    isPlayer1Turn.value = true
                    gameOver.value = false
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Reset Game", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            Text(
                text = if (isPlayer1Turn.value) "Player 1 Turn" else "Player 2 Turn",
                modifier = Modifier.padding(10.dp),
                fontSize = 28.sp,
                color = Color(0xFF00BCD4),
                fontWeight = FontWeight.Bold,
            )
            Image(
                painter = if (currentImage.value == 0) {
                    painterResource(R.drawable.developer)
                } else {
                    painterResource(images[currentImage.value - 1])
                },
                contentDescription = "Dice",
                colorFilter = if (currentImage.value == 0) {
                    ColorFilter.tint(Color(0xFF00BCD4))
                } else {
                    null
                },
                alignment = Alignment.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .size(250.dp)
                    .background(Color.White, shape = CircleShape)
            )


            Spacer(modifier = Modifier.height(15.dp))

            // Player Buttons
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = {
                        val random = Random.nextInt(6) + 1
                        currentImage.value = random
                        scorePlay1.value += random
                        isPlayer1Turn.value = false
                        if (scorePlay1.value >= 20 || scorePlay2.value >= 20) {
                            gameOver.value = true
                        }
                    },
                    modifier = Modifier.padding(end = 10.0.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                    enabled = isPlayer1Turn.value
                ) {
                    Text(
                        text = "Roll Player 1",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(5.0.dp)
                    )
                }

                Button(
                    onClick = {
                        val random = Random.nextInt(6) + 1
                        currentImage.value = random
                        scorePlay2.value += random
                        isPlayer1Turn.value = true
                        if (scorePlay1.value >= 20 || scorePlay2.value >= 20) {
                            gameOver.value = true
                        }
                    },
                    modifier = Modifier.padding(start = 10.0.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                    enabled = !isPlayer1Turn.value
                ) {
                    Text(
                        text = "Roll Player 2",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(5.0.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Display the scores
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = "Score 1: ${scorePlay1.value}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00BCD4)
                )

                Text(
                    text = "Score 2: ${scorePlay2.value}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00BCD4)
                )
            }
        }
    }
}
