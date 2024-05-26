package com.example.chessguessr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    val guessrVM: GuessrViewModel = GuessrViewModel()
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
                    Score(guessrVM)
                    Spacer(modifier = Modifier.padding(70.dp))
                    DisplayCoordinateandGetColor(guessrVM)
                }
            }
            Hint()
        }
    }
}

@Composable
fun DisplayCoordinate(coordinate : String){
    Text(
        coordinate,
        fontSize = 90.sp,
    )
}

@Composable
fun DisplayCoordinateandGetColor(guessrVM: GuessrViewModel){
    LaunchedEffect(key1 = true ) {
        guessrVM.coord = guessrVM.getCoordinate()
    }
    DisplayCoordinate(guessrVM.coord)
    Spacer(modifier = Modifier.padding(70.dp))
    Choices(
        guessrVM = GuessrViewModel(),
        color = guessrVM.getSquareColor(guessrVM.coord),
        lightChoice = {
            val outcome = guessrVM.checkChoice("Light", guessrVM.getSquareColor(guessrVM.coord))
            if (outcome) {
                guessrVM.coord = guessrVM.getCoordinate()
                guessrVM.updateCorrect()
            } else {
                guessrVM.coord = guessrVM.getCoordinate()
                guessrVM.updateWrong()
            }
    },
        darkChoice = {
            val outcome = guessrVM.checkChoice("Dark", guessrVM.getSquareColor(guessrVM.coord))
            if (outcome) {
                guessrVM.coord = guessrVM.getCoordinate()
                guessrVM.updateCorrect()
            } else {
                guessrVM.coord = guessrVM.getCoordinate()
                guessrVM.updateWrong()

            }
        })
}

@Composable
fun Score(guessrVM: GuessrViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black)
            .padding(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${guessrVM.correctScore} / ${guessrVM.totalScore}",
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
fun Choices(guessrVM : GuessrViewModel, color: String, lightChoice : ()-> Unit, darkChoice : ()-> Unit) {

    Box() {
        Column {
            OutlinedButton(
                onClick = lightChoice,
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
                onClick = darkChoice,
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