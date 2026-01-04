// File: com/example/deimomoimain/views/GameOverScreen.kt
package com.example.deimomoimain.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.deimomoimain.R
import com.example.deimomoimain.navigation.AppScreen
import com.example.deimomoimain.ui.theme.LilitaOne
import com.example.deimomoimain.viewmodel.GameOver

@Composable
fun GameOverScreen(
    navController: NavController,
    result: GameOver,
    modifier: Modifier = Modifier
) {
    val title    = if (result == GameOver.WIN)  "YOU WIN!"        else "YOU LOSE!"
    val subtitle = if (result == GameOver.WIN)  "Points Earned: 100" else "Points Lost: 150"
    val icon     = painterResource(id = R.drawable.strongestracista)

    // Desaturate to 20% when LOSE, otherwise full saturation
    val saturation = if (result == GameOver.LOSE) 0.2f else 1f
    val colorMatrix = ColorMatrix().apply { setToSaturation(saturation) }
    val colorFilter = ColorFilter.colorMatrix(colorMatrix)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2E2A28))
            .drawWithContent {
                val layerPaint = Paint().apply { this.colorFilter = colorFilter }
                drawContent()
                val rect = Rect(0f, 0f, size.width, size.height)
                drawContext.canvas.saveLayer(rect, layerPaint)
                drawContent()
                drawContext.canvas.restore()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Avatar
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(256.dp)
            )

            Spacer(Modifier.height(24.dp))

            // Gradient background card
            Card(
                colors    = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape     = RoundedCornerShape(24.dp),
                modifier  = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(listOf(
                                Color(0xFFFFE07A),
                                Color(0xFFFF82A1)
                            )),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(vertical = 24.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text      = title,
                            fontFamily= LilitaOne,
                            fontSize  = 64.sp,
                            color     = Color(0xFF382E3B),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text       = subtitle,
                            fontFamily = LilitaOne,
                            fontSize   = 20.sp,
                            color      = Color(0xFF382E3B)
                        )
                        Spacer(Modifier.height(32.dp))
                        Box(
                            modifier = Modifier
                                .width(301.dp)
                                .height(65.dp)
                                .background(Color(0xFF382E3B), RoundedCornerShape(12.dp))
                                .clickable {
                                    // navigate back home
                                    navController.navigate(AppScreen.Home.route) {
                                        popUpTo(AppScreen.Home.route) { inclusive = true }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text       = "Back to Home",
                                fontFamily = LilitaOne,
                                fontSize   = 32.sp,
                                color      = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
