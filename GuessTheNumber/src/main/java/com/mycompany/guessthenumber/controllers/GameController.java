/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.guessthenumber.controllers;

import com.mycompany.guessthenumber.data.GuessTheNumberDao;
import com.mycompany.guessthenumber.models.GameDto;
import com.mycompany.guessthenumber.models.GuessDto;
import com.mycompany.guessthenumber.service.GameServiceLayerImpl;
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

    private final GameServiceLayerImpl service;
    private final GuessTheNumberDao dao;
    public GameController(GameServiceLayerImpl service, GuessTheNumberDao dao) {
        this.service = service;
        this.dao = dao;
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto createGame(GameDto gameDto) {
        return service.createGame(gameDto);
    }

    @PostMapping("/guess/{gameId}")
    public GuessDto create(@PathVariable int gameId, @RequestBody GuessDto guessDto) {
        return service.create(gameId, guessDto);
    }
    
    @GetMapping
    public List<GameDto> getAll() {
        return service.getAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GameDto> findById(@PathVariable int id) {
        GameDto result = service.findById(id);
        if(result.getStatusId()==1){
            result.setAnswer("????");
        }
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("rounds/{id}")
    public ResponseEntity<List<GuessDto>> getAllRounds(@PathVariable int id) {
        List<GuessDto> result = new ArrayList<>(service.getAllRounds(id));
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    
}
