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

    @Override
    public GameDto addGame(GameDto gameDto) {

        final String sqlGame = "INSERT INTO game(answer, statusid) VALUES(?,1);";
        final String sqlRound = "INSERT INTO round(gameId) VALUES(?);";
        final String sqlGameRound = "INSERT INTO gameRound(gameId, roundId) VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        //Insert new game information into database
        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sqlGame,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, Integer.toString(gameDto.getAnswer()));
            return statement;

        }, keyHolder);

        gameDto.setGameId(keyHolder.getKey().intValue());

        return gameDto;
    }

    
    //TODO : Update guessNumber to display which guess this is for the gameId the user is guessing for; --Austin
    @Override
    public GuessDto submitGuess(GuessDto guessDto) {

        //Timestamp for guess
        Date dt = new Date();
        Timestamp databaseTimestamp = new Timestamp(dt.getTime());

        final String sqlGuess = "INSERT INTO guess (`time`, userGuess, result, roundId)"
                + " VALUES(?, ?, ?, ?);";;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sqlGuess,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setTimestamp(1, databaseTimestamp);
            statement.setString(2, Integer.toString(guessDto.getUserGuess()));
            statement.setString(3, guessDto.getResult());
            statement.setString(4, Integer.toString(guessDto.getGuessId()));
            return statement;

        }, keyHolder);

        return guessDto;
    }

    //Retrieves all GameId's and info about the games
    //TODO: Show answers for only games completed, and amount of guesses for each game
    @Override
    public List<GameDto> getAll() {
        final String sql = "select\n"
                + "game.gameId, \n"
                + "status.statusName,\n"
                + "answer\n"
                + "from game\n"
                + "join status\n"
                + "on game.statusId = status.statusId;";
        return jdbcTemplate.query(sql, new GuessGamerMapper());
    }

    //Retrieves gameinfo given game Id
    //TODO: Show answers only if the game is completed, amount of guesses for game
    @Override
    public GameDto findById(int id) {

        final String sql = "SELECT game.gameId, statusId, answer "
                + "FROM game WHERE game.gameId = ?;";
        return jdbcTemplate.queryForObject(sql, new GuessGamerMapper(), id);
    }

    @Override
    public boolean update(GameDto guessnumber) {

//        final String sql = "UPDATE guessnumber SET "
//                + "guessnumber = ?, "
//                + "note = ?, "
//                + "finished = ? "
//                + "WHERE id = ?;";
//
//        return jdbcTemplate.update(sql,
//                guessnumber.getTodo(),
//                guessnumber.getNote(),
//                guessnumber.isFinished(),
//                guessnumber.getId()) > 0;
        return true;
    }

    //Gets all rounds that have been played for a given gameId
    @Override
    public List<GuessDto> getAllRounds(int gameId) {

        final String sql = "select \n"
                + "guess.guessId,\n"
                + "guess.time,\n"
                + "guess.guessNumber,\n"
                + "guess.userGuess,\n"
                + "guess.result\n"
                + "from guess\n"
                + "where guess.gameId = ?\n"
                + "order by guess.time;";

        return jdbcTemplate.query(sql, new GuessGuessMapper(), gameId);
    }

    //Mapper for GameDto
    private static final class GuessGamerMapper implements RowMapper<GameDto> {

        @Override
        public GameDto mapRow(ResultSet rs, int index) throws SQLException {
            GameDto gameDto = new GameDto();
            gameDto.setGameId(rs.getInt("gameId"));
            gameDto.setStatusId(rs.getInt("statusId"));
            gameDto.setAnswer(rs.getInt("answer"));
            return gameDto;
        }
    }

    //Mapper for GuessDto 
    private static final class GuessGuessMapper implements RowMapper<GuessDto> {

        @Override
        public GuessDto mapRow(ResultSet rs, int i) throws SQLException {
            GuessDto guessDto = new GuessDto();
            guessDto.setGuessId(rs.getInt("guessId"));
            guessDto.setGuessNumber(rs.getInt("guessNumber"));
            guessDto.setResult(rs.getString("result"));
            guessDto.setTime(rs.getString("time"));
            guessDto.setUserGuess(rs.getInt("userGuess"));

            return guessDto;
        }

    }
}
