package com.example.chessguessr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
//                    .background(Color.Red),
                contentAlignment = Alignment.Center,

            ) {
                Column(
                    modifier = Modifier
//                        .background(Color.Blue)
                        .fillMaxSize(),
//                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.SpaceEvenly,
                ){
                    Score()
                    Spacer(modifier = Modifier.padding(70.dp))
                    CoordinateandSquareColor()
                }
            }
            Hint()
        }
    }
}

fun getCoordinate(): String {
    val randomNumber = Random.nextInt(63)
    var coordinate = ""
    val coordinates = arrayOf(
        "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8",
        "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8",
        "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8",
        "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8",
        "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8",
        "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8",
        "g1", "g2", "g3", "g4", "g5", "g6", "g7", "g8",
        "h1", "h2", "h3", "h4", "h5", "h6", "h7", "h8"
    )

    coordinate = coordinates[randomNumber]
    return coordinate
}

fun getSquareColor(coordinate: String) : String{
    var squareColor = ""
    val lightSquares = setOf(
        "a2", "a4", "a6", "a8",
        "b1", "b3", "b5", "b7",
        "c2", "c4", "c6", "c8",
        "d1", "d3", "d5", "d7",
        "e2", "e4", "e6", "e8",
        "f1", "f3", "f5", "f7",
        "g2", "g4", "g6", "g8",
        "h1", "h3", "h5", "h7"
    )

    if (coordinate in lightSquares) {
        squareColor = "Light"
    } else {
        squareColor = "Dark"
    }

    println(squareColor + " " + "Sep")
    return squareColor
}

@Composable
fun Coordinate(content: @Composable (String, String) -> Unit){

    val coord = remember { mutableStateOf(getCoordinate()) }
    val squareColor = remember { mutableStateOf(getSquareColor(coord.value))}

    content(coord.value,squareColor.value)

}

@Composable
fun DisplayCoordinate(coordinate : String){
    Text(
        coordinate,
        fontSize = 90.sp,
    )
}

@Composable
fun CoordinateandSquareColor(){

    val currentCoordinate = remember { mutableStateOf(getCoordinate()) }

    Coordinate{ _,color ->
        DisplayCoordinate(currentCoordinate.value)
        Spacer(modifier = Modifier.padding(60.dp))
        Choices(color) {isCorrect ->
            if (isCorrect) {
                currentCoordinate.value = getCoordinate()
            }
        }
    }
}

fun checkChoice(choice : String, color : String) : Boolean{
    if (choice == color){
        return true
    }
    return false
}


@Composable
fun Score() {
    var won = 0
    var total = 0
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black)
            .padding(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$won / $total",
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Hint(){
    val openDialog = remember{ mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {openDialog.value = true},
            modifier = Modifier.size(70.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text("?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }

    if(openDialog.value){
        Dialog(onDismissRequest = { openDialog.value = false }){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
            ){
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(300.dp),
                    painter = painterResource(id = R.drawable.chessboard),
                    contentDescription = "A chessboard"
                )
            }
        }
    }
}

@Composable
fun Choices(color: String, onChoiceMade : (Boolean) -> Unit) {

    val correctChoice = remember { mutableStateOf(false) }

    Box() {
        Column {
            OutlinedButton(
                onClick = {
                    correctChoice.value = checkChoice("Light", color)
                    onChoiceMade(correctChoice.value) },
                border = BorderStroke(1.5.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .height(75.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                Text(
                    "Light",
                    fontSize = 30.sp,
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {
                    correctChoice.value = checkChoice("Dark", color)
                    onChoiceMade(correctChoice.value)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .height(75.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                Text(
                    "Dark",
                    fontSize = 30.sp,
                )
            }
        }
    }
}