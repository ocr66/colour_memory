package com.accedo.colourmemory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;
import java.util.Random;

/**
 * Created by o.lopez.cienfuegos on 18/03/2016.
 */
public class CardsController{

    Context context;
    Integer [] currentGame = {
            R.drawable.colour1,
            R.drawable.colour2,
            R.drawable.colour3,
            R.drawable.colour4,
            R.drawable.colour5,
            R.drawable.colour6,
            R.drawable.colour7,
            R.drawable.colour8,
            R.drawable.colour1,
            R.drawable.colour2,
            R.drawable.colour3,
            R.drawable.colour4,
            R.drawable.colour5,
            R.drawable.colour6,
            R.drawable.colour7,
            R.drawable.colour8
    };

    Integer [] imageCard = new Integer[16];

    //-1 = hidden, 0 = shown, 1 = found
    Integer [] selected = {-1, -1, -1, -1,
                            -1, -1, -1, -1,
                            -1, -1, -1, -1,
                            -1, -1, -1, -1,};
    private int viewed = 0;
    private int card1 = -1;
    private int card2 = -1;
    private int currentHighscore = 0;
    private View view;

    public CardsController(Context context){
        this.context = context;
        for(int i = 0; i < imageCard.length; i++){
            imageCard[i] = R.drawable.card_bg;
        }

        shuffleArray(currentGame);
    }

    public Integer[] getImageId(){
        return imageCard;
    }

    public int viewCard(int position){
        //if card hidden and if the first card shown
        int status = 3;
        if(selected[position] == -1 && viewed >= 0 && viewed < 2){
            imageCard[position] = currentGame[position];
            viewed++;
            selected[position] = 0;
            if(viewed == 2){
                status = validate();
                if(status == 1) {
                    currentHighscore = currentHighscore+2;
                    Toast.makeText(context, "Good job!", Toast.LENGTH_SHORT).show();
                    viewed = 0;
                    return 1;
                } else if(status == 0){
                    Toast.makeText(context, "Try again!", Toast.LENGTH_SHORT).show();
                    currentHighscore--;
                    return -1;
                } else if(status == 2){
                    currentHighscore = currentHighscore+2;
                    return 2;
                }

            }
        }else if(selected[position] == 0){
            Toast.makeText(context, "Please select another card", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return 0;
    }

    public void hideCards(){
        for(int i = 0; i < imageCard.length; i++) {
            if(selected[i] == 0) {
                imageCard[i] = R.drawable.card_bg;
                selected[i] = -1;
            }
        }
        viewed = 0;
    }

    public int getViewed(){
        return viewed;
    }

    private int validate(){
        int status = 3;
        card1 = -1;
        card2 = -1;
        //Validate if first or second card selected
        for(int i = 0; i < selected.length; i++){
            if(selected[i] == 0 && card1 == -1){
                card1 = i;
            }else if(selected[i] == 0 && card1 != -1 && card2 == -1){
                card2 = i;
                break;
            }
        }

        //If both cards are selected, validate match
        if(currentGame[card1].equals(currentGame[card2])) {
            selected[card1] = 1;
            selected[card2] = 1;
            //if cards match validate end of game
            for(int i = 0; i < selected.length; i++){
                if(selected[i] != 1){
                    status = 1; //valid pair, game not finished
                    break;
                }else if(i == (selected.length - 1)){
                    Toast.makeText(context, "Game Finished!", Toast.LENGTH_SHORT).show();
                    status = 2; //game finished
                    break;
                }
                status = 1;
            }
        }else {
            status = 0; //first card
        }
        return status;
    }

    public int getCurrentHighscore(){
        return currentHighscore;
    }

    public int getCard1() {
        return card1;
    }

    public int getCard2(){
        return card2;
    }

    public void removeCards(){
        for(int i = 0; i < imageCard.length; i++) {
            if(selected[i] == 1) {
                imageCard[i] = R.color.white;
            }
        }
        viewed = 0;
    }

    public void shuffleArray(Integer[] array) {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }
}
