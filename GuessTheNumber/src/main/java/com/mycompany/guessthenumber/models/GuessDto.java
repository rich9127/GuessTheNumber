/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.guessthenumber.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rich
 */
public class GuessDto {

    private int guessId;
    private int roundId;
    private String time;
    private List<Integer> userGuess = new ArrayList<>();
    private String result;

    public int getGuessId() {
        return guessId;
    }

    public void setGuessId(int guessId) {
        this.guessId = guessId;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Integer> getUserGuess() {
        return userGuess;
    }

    public void setUserGuess(List<Integer> userGuess) {
        this.userGuess = userGuess;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    
    
}
