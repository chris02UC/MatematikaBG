package com.example.deimomoimain.view
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.deimomoimain.R
import com.example.deimomoimain.navigation.AppScreen
import com.example.deimomoimain.ui.theme.DEIMOMOIMAINTheme
import com.example.deimomoimain.ui.theme.LilitaOne
import com.example.deimomoimain.ui.theme.momoiDark
import com.example.deimomoimain.ui.theme.momoiPink
import com.example.deimomoimain.ui.theme.momoiYellow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(momoiYellow), Color(momoiPink))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(contentAlignment = Alignment.TopCenter) {
            // Card container
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 60.dp)
                    .padding(horizontal= 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFF5E9E0))
                    .padding(24.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp)) // Space for logo

                Text(
                    text = "WELCOME",
                    fontWeight = FontWeight.Bold,
                    fontSize = 56.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontFamily = LilitaOne
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E‑mail", style = TextStyle(fontFamily = LilitaOne, color = Color.DarkGray)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    textStyle = TextStyle(
                        color      = Color.DarkGray,
                        fontFamily = LilitaOne
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor        = Color.DarkGray,
                        focusedLabelColor  = Color.DarkGray,
                        unfocusedLabelColor= Color.DarkGray
                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", style = TextStyle(fontFamily = LilitaOne, color = Color.DarkGray)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = TextStyle(
                        color      = Color.DarkGray,
                        fontFamily = LilitaOne
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor         = Color.DarkGray,
                        focusedLabelColor   = Color.DarkGray,
                        unfocusedLabelColor = Color.DarkGray
                    )
                )

                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .clickable { navController.navigate(AppScreen.Register.route) }
                ) {
                    Text(
                        text = "New user? ",
                        fontSize = 14.sp,
                        color = Color(momoiDark)
                    )
                    Text(
                        text = "Register",
                        fontSize = 14.sp,
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold
                    )
                }


                Button(
                    onClick = { navController.navigate(AppScreen.Home.route) },
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(momoiDark)
                    )
                ) {
                    Text(
                        text = "LOGIN",
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        color = Color.White,
                        fontFamily = LilitaOne,
                    )
                }
            }

            // Logo placed outside the card
            Image(
                painter = painterResource(id = R.drawable.strongestracista), // Replace as needed
                contentDescription = "Logo",
                modifier = Modifier
                    .size(170.dp)
                    .offset(y = (-100).dp) // Move logo upwards
            )
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginView(
            navController = TODO()
        ) // Replace with a valid NavController if needed
    }
}

