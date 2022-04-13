package com.sg.dao;

import com.sg.model.Guess;

import java.util.List;

public interface GuessDAO {
    public void guessNum(Guess guess);
    public int retrieveCurrentRound();
    public void updateGuessResult(int partial, int exact, int id, int roundId);
    public List<Guess> getRoundById(int id);
}
