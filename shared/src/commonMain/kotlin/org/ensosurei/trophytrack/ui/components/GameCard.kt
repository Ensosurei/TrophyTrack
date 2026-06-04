package org.ensosurei.trophytrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ensosurei.trophytrack.ui.theme.purple
import org.ensosurei.trophytrack.ui.theme.surface
import org.ensosurei.trophytrack.ui.theme.white
import org.jetbrains.compose.resources.vectorResource
import trophytrack.shared.generated.resources.Res
import trophytrack.shared.generated.resources.ic_play

@Composable
fun GameCard(
    gameTitle : String,
    modifier: Modifier = Modifier,
    onPlayClick: () -> Unit = {}
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = surface
        ),
        modifier = modifier
            .width(280.dp)
            .height(380.dp)
    ){
        Box(modifier = Modifier.fillMaxSize()){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                surface.copy(0.3f),
                                Color.Black.copy(0.8f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 20.dp, end = 80.dp)
            ){
                Text(
                    text = gameTitle,
                    color = white,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = onPlayClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = purple,
                    contentColor = white
                ),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp)
                    .size(48.dp)
                    .background(purple, shape = CircleShape)
            ){
                Icon(
                    vectorResource(Res.drawable.ic_play),
                    contentDescription = null
                )
            }
        }
    }
}