package com.sg.dao;

import com.sg.model.Game;

import java.util.HashMap;
import java.util.List;

public interface GameDAO {
    public void generateRandomNum(String answerOne, String answerTwo, String answerThree, String answerFour);

    public HashMap<String, Integer> getGameId();

    public List<String> getAnswer(int id);

    public List<Game> getListOfAllGames();

    public List<Game> getGameById(int id);

    public void updateGameResult(int id);

    public int getGameResultForId(int id);
}
