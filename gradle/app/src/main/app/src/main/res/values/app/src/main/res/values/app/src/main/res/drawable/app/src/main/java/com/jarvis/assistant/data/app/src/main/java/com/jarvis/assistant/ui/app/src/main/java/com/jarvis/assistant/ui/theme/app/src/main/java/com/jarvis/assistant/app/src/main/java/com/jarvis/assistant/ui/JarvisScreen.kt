package com.jarvis.assistant.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jarvis.assistant.data.ChatMessage
import com.jarvis.assistant.ui.theme.JarvisColors

@Composable
fun JarvisScreen(viewModel: MainViewModel = viewModel()) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    var input by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(messages.lastIndex)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(JarvisColors.Background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(16.dp)
    ) {
        Text(
            text = "J.A.R.V.I.S",
            color = JarvisColors.Cyan,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            AiOrb()
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages, key = { it.id }) { message ->
                MessageBubble(message)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a command...", color = JarvisColors.CyanDim) },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = JarvisColors.TextPrimary,
                    unfocusedTextColor = JarvisColors.TextPrimary,
                    focusedBorderColor = JarvisColors.Cyan,
                    unfocusedBorderColor = JarvisColors.CyanDim,
                    cursorColor = JarvisColors.Cyan
                )
            )
            Button(
                onClick = {
                    viewModel.sendMessage(input)
                    input = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = JarvisColors.Cyan,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("SEND", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun MessageBubble(message: ChatMessage) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .background(
                    if (message.isUser) JarvisColors.UserBubble else JarvisColors.Panel,
                    shape
                )
                .border(1.dp, JarvisColors.CyanDim, shape)
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                color = JarvisColors.TextPrimary,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun AiOrb(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "orb")
    val pulse by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Canvas(modifier = modifier.size(120.dp)) {
        val radius = size.minDimension / 2f
        val glowRadius = radius * pulse
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    JarvisColors.Cyan.copy(alpha = 0.9f),
                    JarvisColors.Cyan.copy(alpha = 0.25f),
                    Color.Transparent
                ),
                center = center,
                radius = glowRadius
            ),
            radius = glowRadius
        )
        val inset = 4.dp.toPx()
        rotate(rotation) {
            drawArc(
                color = JarvisColors.Cyan,
                startAngle = 0f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = Size(size.width - inset * 2, size.height - inset * 2),
                style = Stroke(width = 4.dp.toPx())
            )
        }
    }
}
