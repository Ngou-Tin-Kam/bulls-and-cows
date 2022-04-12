package com.sg.dao;

import java.util.HashMap;
import java.util.List;

public interface GameDAO {
    public void generateRandomNum(String answerOne, String answerTwo, String answerThree, String answerFour);
    public HashMap<String, Integer> getGameId();
    public List<String> getAnswer(int id);
}
