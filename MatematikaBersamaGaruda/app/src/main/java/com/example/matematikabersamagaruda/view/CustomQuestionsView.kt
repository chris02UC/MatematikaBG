package com.example.matematikabersamagaruda.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.matematikabersamagaruda.ui.composables.RotatedBackground
import com.example.matematikabersamagaruda.ui.theme.*
import com.example.matematikabersamagaruda.viewmodel.CustomQuestionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomQuestionsView(
    navController: NavController, // Add if back navigation is needed
    customQuestionsViewModel: CustomQuestionsViewModel = viewModel()
) {
    val newQuestionText by customQuestionsViewModel.newQuestionText.collectAsState()
    val questionTemplates by customQuestionsViewModel.questionTemplates.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        RotatedBackground()

        Scaffold(
            containerColor = Color.Transparent,
            topBar = { CustomQuestionsTopBar(navController) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp), // Main horizontal padding
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                AddQuestionSection(
                    text = newQuestionText,
                    onTextChange = { customQuestionsViewModel.onNewQuestionTextChange(it) },
                    onAddClick = { customQuestionsViewModel.addQuestionTemplate() }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Divider(
                    color = AppTextWhite.copy(alpha = 0.8f),
                    thickness = 4.dp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Scrollable Grid for question templates
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // 2 columns
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f) // Takes remaining space
                ) {
                    items(questionTemplates, key = { it.id }) { template ->
                        QuestionTemplateButton(template = template.template)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp)) // Bottom padding
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomQuestionsTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Custom Questions",
                style = MaterialTheme.typography.displaySmall,
                color = AppNavSelected,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = AppNavBackground),
        navigationIcon = {
            // IconButton(onClick = { navController.popBackStack() }) { // Example back navigation
            //     Icon(
            //         imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            //         contentDescription = "Back",
            //         tint = AppNavSelected
            //     )
            // }
        },
        actions = {
            // Spacer(modifier = Modifier.width(48.dp)) // To balance title if nav icon is present
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionSection(
    text: String,
    onTextChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = AppCustomQButton),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier
                    .width(258.dp)
                    .height(80.dp)
                    .background(color = Color(0xFFF4CBCC), shape = RoundedCornerShape(size = 12.dp)),
                placeholder = {
                    Text(
                        "Enter Question",
                        style = MaterialTheme.typography.titleLarge.copy( // LilitaOne
                            color = AppPremiumPink.copy(alpha = 0.5f)
                        )
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = AppTextFieldLightPink, // Background of the text field itself
                    focusedBorderColor = AppPremiumPink,
                    unfocusedBorderColor = Color.Transparent, // No border when unfocused
                    cursorColor = AppCustomQButton,
                    focusedTextColor = AppCustomQButton, // Color of text when typing
                    unfocusedTextColor = AppCustomQButton.copy(alpha = 0.8f)
                ),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy( // LilitaOne
                    fontWeight = FontWeight.Bold,
                    color = AppCustomQButton // Ensure this color is visible
                )
            )
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = onAddClick,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppTextFieldLightPink),
                modifier = Modifier
                    .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
                    .width(80.dp)
                    .height(80.dp)
                    .background(color = Color(0xFFF4CBCC), shape = RoundedCornerShape(size = 12.dp)), // Square button
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Question",
                    tint = AppCustomQButton,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Composable
fun QuestionTemplateButton(template: String) {
    Button(
        onClick = { /* TODO: Handle template selection or editing */ },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AppCustomQButton),
        modifier = Modifier
            .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
            .width(176.dp)
            .height(100.dp)
            .background(color = Color(0xFFFF2D55), shape = RoundedCornerShape(size = 18.dp)), // Fixed height for buttons
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Text(
            text = template,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 24.sp), // LilitaOne
            color = AppTextWhite,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
    }
}