package com.sg.dao;

import com.sg.model.Answer;
import com.sg.model.Guess;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class GuessDAOImpl implements GuessDAO {

    private JdbcTemplate jdbcTemplate;

    public GuessDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void guessNum(Guess guess) {
        String queryOne = "SELECT MAX(round_id) FROM guess WHERE id = ";
        StringBuilder sb = new StringBuilder();
        sb.append(queryOne);
        sb.append(guess.getId());

        int roundId = 0;
        if (jdbcTemplate.queryForObject(sb.toString(), Integer.class) == null) {
            roundId = 1;
        } else {
            roundId = jdbcTemplate.queryForObject(sb.toString(), Integer.class) + 1;
        }

        String queryTwo = "INSERT INTO guess (round_id, id, guess_one, guess_two, guess_three, guess_four, partial, exact)"
                + "values (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(queryTwo, roundId, guess.getId(), guess.getGuessOne(), guess.getGuessTwo(), guess.getGuessThree(), guess.getGuessFour(), guess.getPartial(), guess.getExact());
    }

    @Override
    public int retrieveCurrentRound(Guess guess) {
        String queryOne = "SELECT MAX(round_id) FROM guess WHERE id = ";
        StringBuilder sb = new StringBuilder();
        sb.append(queryOne);
        sb.append(guess.getId());

        return jdbcTemplate.queryForObject(sb.toString(), Integer.class);
    }

    @Override
    public void updateGuessResult(int partial, int exact, int id, int roundId) {
        String query = "UPDATE guess SET partial = ?, exact = ? WHERE id = ? AND round_id = ?;";
        jdbcTemplate.update(query, partial, exact, id, roundId);
    }

    @Override
    public List<Guess> getRoundById(int id) {
        String query = "SELECT * FROM guess WHERE id = ";

        StringBuilder sb = new StringBuilder();
        sb.append(query);
        sb.append(id);

        return jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<>(Guess.class));
    }
}