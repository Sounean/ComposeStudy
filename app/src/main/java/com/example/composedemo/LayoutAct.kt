package com.example.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.ui.theme.ComposeDemoTheme
import androidx.compose.ui.Modifier

class LayoutAct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_layout2)
        setContent { 
            ComposeDemoTheme {
                //ScallfoldDemo()
            }
        }
    }
}

val ActiveUser = compositionLocalOf {   //创建一个CompositionLocal实例
    User("小明","三分钟")
}

class User(
    val name: String,
    val time: String)


@Preview
@Composable
fun SimpleBoxDemo1(){
    Column {
        val user = ActiveUser.current   // 通过current关键字获得当前实例
        Text(text = user.name, fontWeight = FontWeight.Bold)
        CompositionLocalProvider {
            Text(text = user.time, style = MaterialTheme.typography.body2)
        }

        CompositionLocalProvider(ActiveUser provides User("小红","5分钟前")) {   // 通过providers来对compositionLocal实例修改值
            val newUser = ActiveUser.current
            Text(text = newUser.name , fontWeight = FontWeight.Bold)
            Text(text = newUser.time, style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun ButtomNavigationDemo(){

}


@Composable
fun ArtistAvator(){
    Box{
        Image(painterResource(id = R.drawable.button_visitorpickup_icon), contentDescription = "Big Avator")
        Icon(painterResource(id = R.drawable.arrow_right), contentDescription = "Small Avator")
    }
}


@Composable
fun LayoutInCompose() {
    var selectedItem by remember { mutableStateOf(0) }
    val navItems = listOf("Songs", "Artists", "Playlists")

    Scaffold(
        topBar = {  // topBar 属性用于设置 AppBar
            TopAppBar(
                title = {  // 可设置标题
                    Text(text = "LayoutInCompose")
                },
                actions = {  // 设置 AppBar 上的按钮 Button
                    IconButton(onClick = { /*TODO*/ }) {
                        // Icon 系统为我们提供了许多常见的 Icon
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {  // bottomBar 可用于设置 BottomNavigation
            BottomNavigation() {
                navItems.forEachIndexed { index, item ->    // 循环遍历navItems标题list
                    BottomNavigationItem(
                        icon = {Icon(Icons.Filled.Face, contentDescription = null)},
                        label = {Text(item)},
                        selected = selectedItem == index,   // selected=true时为选中状态，在遍历时，每次都将index与selectedItem对比
                        onClick = { selectedItem = index }  // 点击某一个就将下标赋值给selectedItem存到内存中去
                    )
                }
            }
        }
    ) {
        // 此处需要编写主界面
        // 这里的例子只调用了一个固定化的自定义BodyContent
        // 要和 BottomNavigation 合理搭配，显示不同的界面的话
        // 考虑使用 Jetpack Compose Navigation 来实现会更加合理一些
        // 会在文档的后面介绍 Jetpack Compose Navigation

        // 这里的 AppContent 是个伪界面
        // 如果你要先简单的实现多界面，你可以这样编写

           when(selectedItem) {
                0 -> { BodyContent1(modifier = Modifier
                    .padding(all = 8.dp)) }
                1 -> { BodyContent2(modifier = Modifier
                    .padding(all = 8.dp)) }
                2 -> { BodyContent3(modifier = Modifier
                    .padding(all = 8.dp)) }
           }

    }
}

@Composable
fun BodyContent3(modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = "联动的第三页!")
        Text(text = "Playlists")
    }
}

@Composable
fun BodyContent2(modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = "联动的第二页!")
        Text(text = "Artist")
    }
}

@Composable
fun BodyContent1(modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = "联动的第一页!")
        Text(text = "Song")

    }
}

/*
@Preview
@Composable
fun ScallfoldDemo(){    // 脚手架
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("主页", "我喜欢的", "设置")

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton( // 点击动作
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) { //内容
                        Icon(Icons.Filled.Menu, null)
                    }
                },
                title = {
                    Text("魔卡沙的炼金工坊")
                }
            )
        },

        bottomBar = {
            BottomNavigation {
                BottomNavigation {
                    items.forEachIndexed { index, item ->
                        BottomNavigationItem(
                            icon = {
                                when(index){
                                    0 -> Icon(Icons.Filled.Home, contentDescription = null)
                                    1 -> Icon(Icons.Filled.Favorite, contentDescription = null)
                                    else -> Icon(Icons.Filled.Settings, contentDescription = null)
                                }
                            },
                            label = { Text(item) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                }
            }
        },
        drawerContent = {
            AppDrawerContent(scaffoldState, scope)
        }
    ) {
        // 此处需要编写主界面

        // 这里的例子只调用了一个 AppContent
        // 要和 BottomNavigation 合理搭配，显示不同的界面的话
        // 考虑使用 Jetpack Compose Navigation 来实现会更加合理一些
        // 会在文档的后面介绍 Jetpack Compose Navigation

        // 这里的 AppContent 是个伪界面
        // 如果你要先简单的实现多界面，你可以这样编写
        */
/*
           when(selectedItem) {
                0 -> { Home() }
                1 -> { Favorite() }
                else -> { Settings() }
           }
         *//*

        // Home(), Favorite(), Settings() 都是单独的 Composable 函数

        AppContent(item = items[selectedItem])
    }
}*/
