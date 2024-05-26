package com.example.chessguessr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GuessrViewModel : ViewModel() {

    var coord : String by mutableStateOf("")
    val squareColor : String by mutableStateOf("")
    var totalScore : Int by mutableIntStateOf(0)
    var correctScore : Int by mutableIntStateOf(0)

    fun checkChoice(choice : String, color : String) : Boolean{
        if (choice == color){
            return true
        }
        return false
    }

    fun updateWrong(){
        totalScore+=1
    }

    fun updateCorrect(){
        correctScore+=1
        totalScore+=1
    }

    fun getCoordinate(): String {
        val randomNumber = Random.nextInt(0,63)
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

        return squareColor
    }
}