package com.sg.dao;

import com.sg.model.Guess;

public interface GuessDAO {
    public void guessNum(Guess guess);
    public int retrieveCurrentRound();
    public void updateGuessResult(int partial, int exact, int id, int roundId);
}
