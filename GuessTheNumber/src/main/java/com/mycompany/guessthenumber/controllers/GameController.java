/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.guessthenumber.controllers;

import com.mycompany.guessthenumber.data.GuessTheNumberDao;
import com.mycompany.guessthenumber.models.GameDto;
import com.mycompany.guessthenumber.models.GuessDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Rich
 */
@RestController
@RequestMapping("/api/game")
public class GameController {

    /*
    "begin" - POST – Starts a game, generates an answer, and sets the correct 
    status. Should return a 201 CREATED message as well as the created gameId.
    
    "guess" – POST – Makes a guess by passing the guess and gameId in as JSON. 
    The program must calculate the results of the guess and mark the game 
    finished if the guess is correct. It returns the Round object with the results filled in.
    
    "game" – GET – Returns a list of all games. Be sure in-progress games 
    
    do not display their answer.
    "game/{gameId}" - GET – Returns a specific game based on ID. 
    Be sure in-progress games do not display their answer.
    
    "rounds/{gameId} – GET – Returns a list of rounds for the specified 
    game sorted by time.

     */
    //Create temporary GameDto object for testing
//    GameDto tempGameDto = new GameDto();
    private final GuessTheNumberDao dao;

    public GameController(GuessTheNumberDao dao) {
        this.dao = dao;
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto createGame(GameDto gameDto) {

        List<Integer> randomArr = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        
        //To be transferred to the service layer --BEGIN
        Random rand = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<4; i++){
            Integer temp = rand.nextInt(randomArr.size());
            builder.append(randomArr.get(temp));
            randomArr.remove(temp);
        }
       
        gameDto.setAnswer(builder.toString());
        gameDto.setStatusId(1);
        //To be transferred to the service layer --END

        return dao.addGame(gameDto);
    }

    @PostMapping("/guess/{gameId}")
    public GuessDto create(@PathVariable int gameId, @RequestBody GuessDto guessDto) {
        //To be transferred to the service layer --BEGIN
        int correctDigits = 0;
        int correctPlaces = 0;

        String userGuessString = guessDto.getUserGuess();
        String gameAnswerString = dao.findById(gameId).getAnswer();

        //Will cause error if the user guess is not the correct length
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

            guessDto.setResult("E:" + correctDigits + " P:" + correctPlaces);
        }
        //To be transferred to the service layer --END
        guessDto.setGameId(gameId);
        return dao.submitGuess(guessDto);
    }
    
    @GetMapping
    public List<GameDto> getAll() {
        return dao.getAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GameDto> findById(@PathVariable int id) {
        GameDto result = dao.findById(id);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("rounds/{id}")
    public ResponseEntity<List<GuessDto>> getAllRounds(@PathVariable int id) {
        List<GuessDto> result = new ArrayList<>(dao.getAllRounds(id));
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    
}
