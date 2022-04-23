/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.guessthenumber.data;

import com.mycompany.guessthenumber.models.GameDto;
import java.util.List;

/**
 *
 * @author Rich
 */
public interface GuessTheNumberDao {
        GameDto addGame(GameDto guessnumber);

    List<GameDto> getAll();

    GameDto findById(int id);

    // true if item exists and is updated
    boolean update(GameDto guessnumber);

    // true if item exists and is deleted
    boolean deleteById(int id);
    
}
