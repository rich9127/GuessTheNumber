/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.guessthenumber.controllers;

import com.mycompany.guessthenumber.data.GuessTheNumberDao;
import com.mycompany.guessthenumber.models.GameDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Rich
 */
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
    
    private final GuessTheNumberDao dao;

    public GameController(GuessTheNumberDao dao) {
        this.dao = dao;
    }
    
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto create(@RequestBody GameDto gameDto){
        return dao.addGame(gameDto);
    }
}
