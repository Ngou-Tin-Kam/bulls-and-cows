package com.sg.dao;

import com.sg.model.Answer;
import com.sg.model.Game;
import com.sg.model.Guess;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameDAOImpl implements GameDAO {

    private JdbcTemplate jdbcTemplate;

    public GameDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void generateRandomNum(String answerOne, String answerTwo, String answerThree, String answerFour) {
            String query = "INSERT INTO game (answer_one, answer_two, answer_three, answer_four, result) VALUES (?, ?, ?, ?, ?);";
            jdbcTemplate.update(query, answerOne, answerTwo, answerThree, answerFour, true);
    }

    @Override
    public HashMap<String, Integer> getGameId() {
        HashMap<String, Integer> map = new HashMap<>();
        String query = "SELECT id FROM game ORDER BY id DESC LIMIT 1;";
        Integer lastId = jdbcTemplate.queryForObject(query, Integer.class);
        map.put("gameId", lastId);
        return map;
    }

    @Override
    public List<String> getAnswer(int id) {
        String query = "SELECT answer_one, answer_two, answer_three, answer_four FROM game WHERE id = ";

        StringBuilder sb = new StringBuilder();
        sb.append(query);
        sb.append(id);
        List<Answer> answerList = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<>(Answer.class));

        List<String> answerListToString = new ArrayList<String>();
        answerListToString.add(answerList.get(0).getAnswerOne());
        answerListToString.add(answerList.get(0).getAnswerTwo());
        answerListToString.add(answerList.get(0).getAnswerThree());
        answerListToString.add(answerList.get(0).getAnswerFour());

        return answerListToString;
    }

    @Override
    public List<Game> getListOfAllGames() {
        String query = "SELECT * FROM game WHERE result = 1;";

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Game.class));
    }

    @Override
    public List<Game> getGameById(int id) {
        String query = "SELECT * FROM game WHERE result = 1 AND id = ";

        StringBuilder sb = new StringBuilder();
        sb.append(query);
        sb.append(id);

        return jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<>(Game.class));
    }
}