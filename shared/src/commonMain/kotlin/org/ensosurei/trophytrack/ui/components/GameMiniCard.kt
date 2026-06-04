package org.ensosurei.trophytrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import org.ensosurei.trophytrack.ui.theme.green
import org.ensosurei.trophytrack.ui.theme.surface
import org.jetbrains.compose.resources.vectorResource
import trophytrack.shared.generated.resources.Res
import trophytrack.shared.generated.resources.ic_medal

@Composable
fun GameMiniCard(
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {}
){
    Card(
        onClick = onCardClick,
        colors = CardDefaults.cardColors(
            containerColor = surface
        ),
        modifier = modifier
            .size(120.dp)
    ){
        Box(modifier = Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(surface, Color.Black)
                        )
                    )
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp)
                    .size(24.dp)
                    .background(green, shape = CircleShape)
                    .border(1.5.dp, Color.Black, CircleShape)
            ){
                Icon(
                    vectorResource(Res.drawable.ic_medal),
                    contentDescription = null
                )
            }
        }
    }
}