package com.example.dialektapp.presentation.screens.leaderboard.components.header

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.model.LeaderboardPeriod
import com.example.dialektapp.ui.theme.*

@Composable
fun LeaderboardPeriodSelector(
    selectedPeriod: LeaderboardPeriod,
    onPeriodChanged: (LeaderboardPeriod) -> Unit,
) {
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    val indicatorOffset by animateFloatAsState(
        targetValue = if (selectedPeriod == LeaderboardPeriod.ALL_TIME) 0f else 0.5f,
        animationSpec = tween(durationMillis = 300),
        label = "indicator_offset"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(LeaderboardSelectorBackgroundColor)
            .border(
                width = 1.dp,
                color = LeaderboardSelectorBorderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(3.dp)
            .onSizeChanged { containerSize = it }
    ) {
        if (containerSize != IntSize.Zero) {
            Box(
                modifier = Modifier
                    .width(with(density) { (containerSize.width / 2).toDp() })
                    .height(with(density) { containerSize.height.toDp() })
                    .offset(x = with(density) { (containerSize.width * indicatorOffset).toDp() })
                    .clip(RoundedCornerShape(10.dp))
                    .background(LeaderboardSelectedButtonColor)
                    .border(
                        width = 1.dp,
                        color = LeaderboardSelectedButtonBorderColor,
                        shape = RoundedCornerShape(10.dp)
                    )
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            PeriodButton(
                text = "Весь час",
                isSelected = selectedPeriod == LeaderboardPeriod.ALL_TIME,
                onClick = { onPeriodChanged(LeaderboardPeriod.ALL_TIME) },
                modifier = Modifier.weight(1f)
            )

            PeriodButton(
                text = "Тиждень",
                isSelected = selectedPeriod == LeaderboardPeriod.WEEKLY,
                onClick = { onPeriodChanged(LeaderboardPeriod.WEEKLY) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
private fun LeaderboardPeriodSelectorPreview() {
    LeaderboardPeriodSelector(
        selectedPeriod = LeaderboardPeriod.ALL_TIME,
        onPeriodChanged = {}
    )
}