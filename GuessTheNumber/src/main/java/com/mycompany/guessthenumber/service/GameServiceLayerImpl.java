/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.guessthenumber.service;

import com.mycompany.guessthenumber.data.GuessTheNumberDatabaseDao;
import com.mycompany.guessthenumber.models.GameDto;
import com.mycompany.guessthenumber.models.GuessDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Austin
 */
@Service
public class GameServiceLayerImpl implements GameServiceLayer{

    private GuessTheNumberDatabaseDao dao;
    
    public GameServiceLayerImpl(GuessTheNumberDatabaseDao dao){
        this.dao = dao;
    }
    
    //Creates a game with a 4 random integers 0-9. No repeating of ints in the 4 digits.
    @Override
    public GameDto createGame(GameDto gameDto) {
        List<Integer> randomArr = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        
        Random rand = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<4; i++){
            Integer temp = rand.nextInt(randomArr.size());
            builder.append(randomArr.get(temp));
            randomArr.remove(temp);
        }
       
        gameDto.setAnswer(builder.toString());
        gameDto.setStatusId(1);

        dao.addGame(gameDto);
        gameDto.setAnswer("????");
        return gameDto;
    }
    
    //Creates a guess object set to a given gameId. 
    @Override
    public GuessDto create(int gameId, GuessDto guessDto) {
        
        int correctDigits = 0;
        int correctPlaces = 0;

        String userGuessString = guessDto.getUserGuess();
        String gameAnswerString = dao.findById(gameId).getAnswer();

        //If user guess is a match, sets statusId to 2 for a finished game.
        //Gets exact matches, and partial matches of guess by looping through char indexes.
        if (userGuessString.equals(gameAnswerString)) {
            guessDto.setResult("All Correct");
            dao.setGameFinish(gameId);
        } else {
            for (int i = 0; i < gameAnswerString.length(); i++) {
                if (userGuessString.charAt(i) == gameAnswerString.charAt(i)) {
                    correctPlaces++;
                }

                for (int j = 0; j < gameAnswerString.length(); j++) {
                    if (userGuessString.charAt(i) == gameAnswerString.charAt(j)) {
                        correctDigits++;
                    }
                }
            }

            guessDto.setResult("E:" + correctPlaces + " P:" + (correctDigits - correctPlaces));
        }
        
        guessDto.setGameId(gameId);
        return dao.submitGuess(guessDto);
    }

    //retrieves a list of all gameDtos
    @Override
    public List<GameDto> getAll() {
        List<GameDto> gameDtoList = dao.getAll();
        for(GameDto obj : gameDtoList){
            if(obj.getStatusId() == 1){
                obj.setAnswer("????");
            } else {
                continue;
            }
        }
        return gameDtoList;
    }

    //Finds a specific game by id
    @Override
    public GameDto findById(int id) {
        return dao.findById(id);
    }

    //returns a list of all rounds for a specific game
    @Override
    public List<GuessDto> getAllRounds(int id) {
        return dao.getAllRounds(id);
    }
    
}
