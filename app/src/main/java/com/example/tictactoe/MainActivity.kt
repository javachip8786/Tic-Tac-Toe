package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.tictactoe.databinding.ActivityMainBinding
import java.text.DateFormatSymbols

class MainActivity : AppCompatActivity() {

    enum class Turn{
        Nought,
        CROSS
    }

    private var firstTurn = Turn.CROSS
    private var currTurn = Turn.CROSS
    private var crossScore = 0;
    private var noughtScore = 0;

    private var boardList = mutableListOf<Button>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
    }

    private fun initBoard(){
        boardList.add(binding.a1);
        boardList.add(binding.a2);
        boardList.add(binding.a3);
        boardList.add(binding.b1);
        boardList.add(binding.b2);
        boardList.add(binding.b3);
        boardList.add(binding.c1);
        boardList.add(binding.c2);
        boardList.add(binding.c3);
    }

    fun boardTapped(view: android.view.View) {
        if(view !is Button){
            return
        }
        addToBoard(view)

        if(CheckForVictory(NOUGHT)){
            noughtScore++
            result("NOUGHT's have WON!")
        }
        if(CheckForVictory(CROSS.toString())){
            crossScore++
            result("CROSS's have WON!")
        }

        if(boardFull()){
            result("Draw")
        }
    }

    private fun CheckForVictory(s: String): Boolean {
        //horizontal
        if(match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s)){
            return true;
        }
        if(match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s)){
            return true;
        }
        if(match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s)){
            return true;
        }

        //Vertical
        if(match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s)){
            return true;
        }
        if(match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s)){
            return true;
        }
        if(match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s)){
            return true;
        }
        //diagonal
        if(match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s)){
            return true;
        }
        if(match(binding.a3,s) && match(binding.b2,s) && match(binding.c1,s)){
            return true;
        }


        return false
    }

    private fun match(button: Button, symbols: String) : Boolean = button.text == symbols

    private fun result(title: String) {
        val message = "\nNoughts $noughtScore\n\nCrosses $crossScore"

        AlertDialog.Builder(this).setTitle(title).setMessage(message).setPositiveButton("Reset")
        {
            _,_ ->
            resetBoard()
        }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard() {
        for(button in boardList){
            button.text = "";
        }
        if(firstTurn == Turn.Nought){
            firstTurn = Turn.CROSS
        }
        else if(firstTurn == Turn.CROSS){
            firstTurn = Turn.Nought
        }
        currTurn = firstTurn
        setTurnLabel()
    }

    private fun boardFull(): Boolean {
        for(button in boardList){
            if(button.text == ""){
                return false;
            }
        }
        return true;
    }

    private fun addToBoard(button : Button) {
        if(button.text!="")
            return
        if(currTurn == Turn.Nought){
            button.text = NOUGHT
            currTurn = Turn.CROSS
        }
        else if(currTurn == Turn.CROSS){
            button.text = CROSS.toString()
            currTurn = Turn.Nought
        }
        setTurnLabel();
    }

    private fun setTurnLabel() {
        var turnText = ""
        if(currTurn== Turn.CROSS){
            turnText = "Turn $CROSS"
        }
        else if(currTurn == Turn.Nought){
            turnText = "Turn $NOUGHT"
        }
        binding.turnTV.text = turnText
    }

    companion object{
        const val NOUGHT = "0"
        const val CROSS = 'X'
    }

}