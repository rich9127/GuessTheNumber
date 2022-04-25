/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.guessthenumber.service;

import com.mycompany.guessthenumber.models.GameDto;
import com.mycompany.guessthenumber.models.GuessDto;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Austin
 */
public interface GameServiceLayer {
    
    GameDto createGame(GameDto gameDto);
    
    GuessDto create(int gameId, GuessDto guessDto);
    
    List<GameDto> getAll();
            
    GameDto findById(int id);
    
    List<GuessDto> getAllRounds(int id);
    
}
