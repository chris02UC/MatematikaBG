package com.example.deimomoimain.view
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deimomoimain.R
import com.example.deimomoimain.ui.theme.DEIMOMOIMAINTheme
import com.example.deimomoimain.ui.theme.LilitaOne
import com.example.deimomoimain.ui.theme.momoiDark
import com.example.deimomoimain.ui.theme.momoiPink
import com.example.deimomoimain.ui.theme.momoiYellow
@Composable
/*fun GameModeButton(
    label: String,
    icon: Int,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val background = if (isSelected) color else color.copy(alpha = 0.4f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(background)
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "$label Icon",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            text = label,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = LilitaOne
        )
    }

}*/


fun GameModeButton(
    label: String,
    icon: Int,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    secondaryColor: Color,
) {
    val border = if (isSelected) {
        Modifier.border(width = 4.dp, color = secondaryColor, shape = RoundedCornerShape(16.dp))
    } else Modifier

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .then(border)
            .clip(RoundedCornerShape(16.dp))
            .background(color)
            .clickable { onClick() }
            .padding(
                horizontal = 8.dp,
                vertical   = 12.dp
            )
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "$label Icon",
            modifier = Modifier
                .size(96.dp)
                .padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = secondaryColor,
            fontFamily = LilitaOne
        )
    }
}

