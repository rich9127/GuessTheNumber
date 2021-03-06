/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.guessthenumber.data;

import com.mycompany.guessthenumber.models.GameDto;
import com.mycompany.guessthenumber.models.GuessDto;
import java.util.List;

/**
 *
 * @author Rich
 */
public interface GuessTheNumberDao {

    GameDto addGame(GameDto gameDto);

    GuessDto submitGuess(GuessDto guessDto);

    List<GameDto> getAll();

    GameDto findById(int gameId);

    // true if item exists and is updated
    boolean setGameFinish(int id);

    List<GuessDto> getAllRounds(int gameId);
}
