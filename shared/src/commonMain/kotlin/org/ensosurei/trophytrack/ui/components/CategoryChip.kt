package org.ensosurei.trophytrack.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ensosurei.trophytrack.ui.theme.gray
import org.ensosurei.trophytrack.ui.theme.pink
import org.ensosurei.trophytrack.ui.theme.white

@Composable
fun CategoryChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val alphaIndicator by animateFloatAsState(if(isSelected) 1f else 0f)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ){
                onClick()
            }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ){
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(color = if(isSelected) pink else Color.Transparent, shape = CircleShape)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            color = if(isSelected) white else gray,
            fontSize = 16.sp,
            fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}