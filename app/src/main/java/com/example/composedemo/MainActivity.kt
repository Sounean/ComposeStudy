package com.example.composedemo

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.ui.theme.ComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("111Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

data class MessageBean(val author: String, val body: String)   // 成员类

@Composable
fun MessageCard(msg: MessageBean) {   // 传入实体类，将实体类的内容显示出来

    var isExpanded by remember {    //isExpanded用来检测卡片是否被展开
        mutableStateOf(false)   // 用remember将本地状态存储在内存中，再用mutableStateOf用来跟踪传递.一旦isExpanded值变化，Composable函数将会自动重新绘制
    }

    val surfaceColor by animateColorAsState(    // 自动颜色转换
        targetValue = (if (isExpanded) Color(0xFFCCCCCC) else MaterialTheme.colors.surface) as androidx.compose.ui.graphics.Color
    )

    androidx.compose.material.Surface(  // surface是compose中卡片的意思
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        modifier = Modifier
            .padding(all = 8.dp)
            .clickable { isExpanded = !isExpanded }, // 添加一个点击方法的触发事件(下方body的text方法中有对该isExpanded的判定。)
        color = surfaceColor
    ) {
        Row(
            modifier = Modifier.padding(all = 8.dp) // 增加padding
        ) {
            Image(
                painterResource(id = R.drawable.arrow_right), contentDescription = "photo",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)  //大小，形状等都放在Modifier修饰中
                    .border(
                        1.5.dp,
                        MaterialTheme.colors.secondary,
                        shape = CircleShape
                    )  // MaterialTheme是官方封装好的主题.
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))  //在图片和文字中间隔开8dp
            Column {  //垂直排放 (如果不放Column或者row，多个对象都会默认从一个地方摆放开始)
                Text(
                    text = msg.author,
                    color = MaterialTheme.colors.secondaryVariant,   // 从主题中拿颜色来设置
                    style = MaterialTheme.typography.subtitle2  //style设置成二级标题
                )
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                Text(
                    text = msg.body,
                    style = MaterialTheme.typography.body2,
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1, //maxLines参数默认情况下只显示1行文本内容
                    modifier = Modifier.animateContentSize ()   //compose大小的动画效果
                )
            }
        }
    }
}




@Composable
fun Conversation(messages:List<MessageBean>){
    LazyColumn(){   // 该函数可以在视图上以 列表的方式去展现 list数据
        items(messages){
                message->
            MessageCard(msg = message)
        }
    }
}

@Preview    // 显示列表
@Composable
fun PreViewMessageCard(){
    ComposeDemoTheme {
        Conversation(messages = MsgData.messages)
    }
}

object MsgData {
    private const val author = "Jetpack Compose 博物馆"
    val messages = listOf(
        MessageBean(author, "我们开始更新啦"),
        MessageBean(author, "为了给广大的读者一个更好的体验，从今天起，我们公众号决定陆续发一些其他作者的高质量文章"),
        MessageBean(author, "每逢佳节倍思亲，从今天起，参加我们公众号活动的同学可以获得精美礼品一份！！"),
        MessageBean(author, "荣华梦一场，功名纸半张，是非海波千丈，马蹄踏碎禁街霜，听几度头鸡唱"),
        MessageBean(author, "唤归来，西湖山上野猿哀。二十年多少风流怪，花落花开。望云霄拜将台，袖星斗安邦策，破烟月迷魂寨。酸斋笑我，我笑酸斋"),
        MessageBean(author, "伤心尽处露笑颜，醉里孤单写狂欢。两路殊途情何奈，三千弱水忧忘川。花开彼岸朦胧色，月过长空爽朗天。青鸟思飞无侧羽，重山万水亦徒然"),
        MessageBean(author, "又到绿杨曾折处，不语垂鞭，踏遍清秋路。衰草连天无意绪，雁声远向萧关去。恨天涯行役苦，只恨西风，吹梦成今古。明日客程还几许，沾衣况是新寒雨"),
        MessageBean(author, "莫笑农家腊酒浑，丰年留客足鸡豚。山重水复疑无路，柳暗花明又一村。箫鼓追随春社近，衣冠简朴古风存。从今若许闲乘月，拄杖无时夜叩门")
    )
}

@Preview(name="Light Mode") // 修饰的函数不能带参数
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"  // Light Mode/Dark Mode分别表示夜间和日间模式，可以放多个Preview来预览(颜色会跟着变，如果用了官方的主题，可以看到颜色之类会跟着变)
)
@Composable
fun DefaultPreview() {
    ComposeDemoTheme {
        MessageCard(MessageBean("wjy", "第一个composeDemo"))    // 调用函数，传入实参
    }
}