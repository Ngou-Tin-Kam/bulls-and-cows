package com.sg.controller;

import com.sg.model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

class ControllerTest {
    @Autowired
    private com.sg.dao.GameDAO gameDAO;

    @Autowired
    private com.sg.dao.GuessDAO guessDAO;

    @Autowired
    private Game gameModel;

    Controller controller = new Controller();

    @Test
    void testCompareResultForNoMatch() {
        List<String> guessList = new ArrayList<String>();
        guessList.add("1");
        guessList.add("1");
        guessList.add("1");
        guessList.add("1");

        List<String> answerList = new ArrayList<String>();
        answerList.add("0");
        answerList.add("0");
        answerList.add("0");
        answerList.add("0");

        int[] result = controller.compareResult(guessList, answerList);
        int exactCounterResult = result[0];
        int partialCounterResult = result[1];

        Assertions.assertEquals(0, exactCounterResult);
        Assertions.assertEquals(0, partialCounterResult);
    }

    @Test
    void testCompareResultForPartialMatchOnly() {
        List<String> guessList = new ArrayList<String>();
        guessList.add("4");
        guessList.add("3");
        guessList.add("2");
        guessList.add("1");

        List<String> answerList = new ArrayList<String>();
        answerList.add("1");
        answerList.add("2");
        answerList.add("3");
        answerList.add("4");

        int[] result = controller.compareResult(guessList, answerList);
        int exactCounterResult = result[0];
        int partialCounterResult = result[1];

        Assertions.assertEquals(0, exactCounterResult);
        Assertions.assertEquals(4, partialCounterResult);
    }

    @Test
    void testCompareResultForExactMatchOnly() {
        List<String> guessList = new ArrayList<String>();
        guessList.add("1");
        guessList.add("2");
        guessList.add("3");
        guessList.add("4");

        List<String> answerList = new ArrayList<String>();
        answerList.add("1");
        answerList.add("2");
        answerList.add("3");
        answerList.add("4");

        int[] result = controller.compareResult(guessList, answerList);
        int exactCounterResult = result[0];
        int partialCounterResult = result[1];

        Assertions.assertEquals(4, exactCounterResult);
        Assertions.assertEquals(0, partialCounterResult);
    }

    @Test
    void testCompareResultForExactAndPartial() {
        List<String> guessList = new ArrayList<String>();
        guessList.add("1");
        guessList.add("2");
        guessList.add("4");
        guessList.add("3");

        List<String> answerList = new ArrayList<String>();
        answerList.add("1");
        answerList.add("2");
        answerList.add("3");
        answerList.add("4");

        int[] result = controller.compareResult(guessList, answerList);
        int exactCounterResult = result[0];
        int partialCounterResult = result[1];

        Assertions.assertEquals(2, exactCounterResult);
        Assertions.assertEquals(2, partialCounterResult);
    }
}