package com.example.genilink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.genilink.ui.theme.Background
import com.example.genilink.ui.theme.Details
import com.example.genilink.ui.theme.GenilinkTheme
import com.example.genilink.ui.theme.NavBG
import com.example.genilink.ui.theme.Primary
import io.eyram.iconsax.IconSax

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenilinkTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight(),
                    color = Color.White
                ) {
                    Layout()
                }
            }
        }
    }
}

@Composable
fun Layout() {
    val selectedIconIndex = remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(Background)
    ) {
        Box(modifier = Modifier.fillMaxWidth().weight(0.98f)){
            when (selectedIconIndex.intValue) {
                0 -> HomeScreen()
                1 -> MessagesScreen()
                2 -> UserScreen()
                3 -> ConfigScreen()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                //.padding(bottom = 20.dp)
                .offset(y = (-20).dp)
                .background(Color.Transparent),
            contentAlignment = Alignment.BottomCenter
        ) {
            Nav(selectedIconIndex = selectedIconIndex)
        }
    }
}




@Composable
fun Nav(selectedIconIndex: MutableState<Int>) {
    val iconOffset = remember { Animatable(0f) }

    LaunchedEffect(selectedIconIndex.value) {
        iconOffset.animateTo(
            selectedIconIndex.value * 52f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow,
            )
        )
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(NavBG)
    ){
        Box(
            modifier = Modifier
                .offset(iconOffset.value.dp, 0.dp)
                .padding(start = 5.dp, top = 5.dp)
                .height(40.dp)
                .width(40.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Primary)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .clip(RoundedCornerShape(30.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf(
                IconSax.Linear.Home,
                IconSax.Linear.Message,
                IconSax.Linear.User,
                IconSax.Linear.Setting2
            ).forEachIndexed { index, icon ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                selectedIconIndex.value = index
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = "Icon $index",
                        tint = if (selectedIconIndex.value == index) Color.White else Details
                    )
                }
            }
        }
    }
}




@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GreetingPreview() {
    GenilinkTheme {
        Layout()
    }
}