package com.sg.dao;

import com.sg.model.Guess;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class GuessDAOImpl implements GuessDAO{

    private JdbcTemplate jdbcTemplate;

        public GuessDAOImpl(DataSource dataSource) {
            jdbcTemplate = new JdbcTemplate(dataSource);
        }

    @Override
    public void guessNum(Guess guess) {
        String query = "INSERT INTO guess (id, guess_one, guess_two, guess_three, guess_four, partial, exact)"
                + "values (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(query, guess.getId(), guess.getGuessOne(), guess.getGuessTwo(), guess.getGuessThree(), guess.getGuessFour(), guess.getPartial(), guess.getExact());
    }

    @Override
    public int retrieveCurrentRound () {
        String query = "SELECT MAX(round_id) FROM guess;";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    @Override
    public void updateGuessResult(int partial, int exact, int id, int roundId) {
            String query = "UPDATE guess SET partial = ?, exact = ? WHERE id = ? AND round_id = ?;";
            jdbcTemplate.update(query, partial, exact, id, roundId);
    }
}