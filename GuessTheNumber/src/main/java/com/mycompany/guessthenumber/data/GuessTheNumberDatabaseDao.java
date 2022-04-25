/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.guessthenumber.data;

/**
 *
 * @author Rich
 */
import com.mycompany.guessthenumber.models.GameDto;
import com.mycompany.guessthenumber.models.GuessDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Profile("database")
public class GuessTheNumberDatabaseDao implements GuessTheNumberDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GuessTheNumberDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Adds a new game to database
    @Override
    public GameDto addGame(GameDto gameDto) {

        final String sqlGame = "INSERT INTO game(answer, statusid) VALUES(?,1);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        //Insert new game information into database
        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sqlGame,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, gameDto.getAnswer());
            return statement;

        }, keyHolder);

        gameDto.setGameId(keyHolder.getKey().intValue());

        return gameDto;
    }


    //Gets a user guess and inserts that guess into database
    @Override
    public GuessDto submitGuess(GuessDto guessDto) {

        //Timestamp for guess
        Date dt = new Date();
        Timestamp databaseTimestamp = new Timestamp(dt.getTime());

        final String sqlGuess = "INSERT INTO guess (`time`, userGuess, result, gameId)"
                + " VALUES(?, ?, ?, ?);";;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sqlGuess,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setTimestamp(1, databaseTimestamp);
            statement.setString(2, guessDto.getUserGuess());
            statement.setString(3, guessDto.getResult());
            statement.setInt(4, guessDto.getGameId());
            return statement;

        }, keyHolder);
        guessDto.setTime(databaseTimestamp.toString());
        return guessDto;
    }

    //Retrieves all GameId's and info about the games
    @Override
    public List<GameDto> getAll() {
        final String sql = "select game.gameId, game.statusId, game.answer from game;";
        return jdbcTemplate.query(sql, new GuessGamerMapper());
    }

    //Retrieves gameinfo given game Id
    @Override
    public GameDto findById(int id) {

        final String sql = "SELECT game.gameId, statusId, answer "
                + "FROM game WHERE game.gameId = ?;";
        return jdbcTemplate.queryForObject(sql, new GuessGamerMapper(), id);
    }

    //Sets a game to status id 2 because game is completed
    @Override
    public boolean setGameFinish(int id) {
        final String sql = "UPDATE game SET "
                + "statusId = 2 "
                + "WHERE gameId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    //Gets all rounds that have been played for a given gameId
    @Override
    public List<GuessDto> getAllRounds(int gameId) {

        final String sql = "select \n"
                + "guess.guessId,\n"
                + "guess.time,\n"
                + "guess.userGuess,\n"
                + "guess.result,\n"
                + "guess.gameId\n"
                + "from guess\n"
                + "where guess.gameId = ?\n"
                + "order by guess.time DESC;";

        return jdbcTemplate.query(sql, new GuessGuessMapper(), gameId);
    }

    //Mapper for GameDto
    private static final class GuessGamerMapper implements RowMapper<GameDto> {

        @Override
        public GameDto mapRow(ResultSet rs, int index) throws SQLException {
            GameDto gameDto = new GameDto();
            gameDto.setGameId(rs.getInt("gameId"));
            gameDto.setAnswer(rs.getString("answer"));
            gameDto.setStatusId(rs.getInt("statusId"));
            return gameDto;
        }
    }

    //Mapper for GuessDto 
    private static final class GuessGuessMapper implements RowMapper<GuessDto> {

        @Override
        public GuessDto mapRow(ResultSet rs, int i) throws SQLException {
            GuessDto guessDto = new GuessDto();
            guessDto.setGuessId(rs.getInt("guessId"));
            guessDto.setResult(rs.getString("result"));
            guessDto.setTime(rs.getString("time"));
            guessDto.setUserGuess(rs.getString("userGuess"));
            guessDto.setGameId(rs.getInt("gameId"));

            return guessDto;
        }

    }
}
