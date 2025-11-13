package com.example.dialektapp.presentation.screens.activity.components.reading

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.domain.model.ActivityContent
import com.example.dialektapp.presentation.screens.activity.components.common.ExampleCard

private val CardBackground = Color(0xFF1F2025)
private val TextWhite = Color.White
private val TextGray = Color(0xFFE0E0E0)
private val GreenAccent = Color(0xFFD1F501)

@Composable
fun ReadingContent(
    content: ActivityContent.Reading,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ReadingTitle(title = content.title)
        }

        item {
            ReadingTextCard(text = content.text)
        }

        if (content.translations.isNotEmpty()) {
            item {
                Text(
                    text = "Словник:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }

            items(content.translations.toList()) { (dialect, literary) ->
                TranslationCard(dialect = dialect, literary = literary)
            }
        }

        if (content.examples.isNotEmpty()) {
            item {
                Text(
                    text = "Приклади:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }

            items(content.examples) { example ->
                ExampleCard(example)
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onComplete,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenAccent,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Завершити",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ReadingTitle(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = TextWhite
    )
}

@Composable
private fun ReadingTextCard(text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = TextWhite,
            modifier = Modifier.padding(16.dp),
            lineHeight = 26.sp
        )
    }
}

@Composable
private fun TranslationCard(dialect: String, literary: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = dialect,
                fontSize = 16.sp,
                color = GreenAccent,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "→ $literary",
                fontSize = 16.sp,
                color = TextGray
            )
        }
    }
}
