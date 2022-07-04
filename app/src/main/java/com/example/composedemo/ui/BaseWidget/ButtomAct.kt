package com.example.composedemo.ui.BaseWidget

import android.graphics.Insets.max
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.Insets.max
import com.example.composedemo.R

class ButtomAct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buttom)
    }
}

data class ButtonState(var text: String, var textColor: Color, var buttonColor: Color)

@Preview
@Composable
fun ButtonDemo(){
    // 获取按钮的状态
    val interactionState = remember { MutableInteractionSource() }

// 使用 Kotlin 的解构方法
    val (text, textColor, buttonColor) = when {
        interactionState.collectIsPressedAsState().value  -> ButtonState("Just Pressed", Color.Red, Color.Black)
        else -> ButtonState( "Just Button", Color.White, Color.Red)
    }

    Button(
        onClick = { /*TODO*/ },
        interactionSource = interactionState,
        elevation = null,
        shape = RoundedCornerShape(30),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
        ),
        modifier = Modifier.width(200.dp).height(IntrinsicSize.Min)
    ) {
        Text(
            text = text, color = textColor
        )
    }
}