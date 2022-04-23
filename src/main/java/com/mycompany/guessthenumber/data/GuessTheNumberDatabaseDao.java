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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        final String sql = "INSERT INTO Game(answer, statusid) VALUES(?,1);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, Integer.toString(gameDto.getAnswer())); 
            return statement;

        }, keyHolder);

        gameDto.setGameId(keyHolder.getKey().intValue());

        return gameDto;
    }

    @Override
    public List<GameDto> getAll() {
        final String sql = "SELECT id, guessnumber, note, finished FROM guessnumber;";
        return jdbcTemplate.query(sql, new GuessNumberMapper());
    }

    @Override
    public GameDto findById(int id) {

        final String sql = "SELECT id, guessnumber, note, finished "
                + "FROM guessnumber WHERE id = ?;";

        return jdbcTemplate.queryForObject(sql, new GuessNumberMapper(), id);
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

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM guessnumber WHERE id = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class GuessNumberMapper implements RowMapper<GameDto> {

        @Override
        public GameDto mapRow(ResultSet rs, int index) throws SQLException {
            GameDto td = new GameDto();
//            td.setId(rs.getInt("id"));
//            td.setTodo(rs.getString("guessnumber"));
//            td.setNote(rs.getString("note"));
//            td.setFinished(rs.getBoolean("finished"));
            return td;
        }
    }
}
