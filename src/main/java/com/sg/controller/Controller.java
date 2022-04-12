package com.sg.controller;

import com.sg.model.Game;
import com.sg.model.Guess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Component
@RestController
public class Controller {
    @Autowired
    private com.sg.dao.GameDAO gameDAO;

    @Autowired
    private com.sg.dao.GuessDAO guessDAO;

    @Autowired
    private Game gameModel;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/begin")
    public HashMap<String, Integer> begin(ModelAndView model) {
        Random random = new Random();
        List<String> list = new ArrayList<String>();

        while (list.size() <= 4) {
            String randomNum = String.valueOf(random.nextInt(10));
            if (!list.contains(randomNum)) {
                list.add(randomNum);
            }
        }

        gameModel.setAnswerOne(list.get(0));
        gameModel.setAnswerTwo(list.get(1));
        gameModel.setAnswerThree(list.get(2));
        gameModel.setAnswerFour(list.get(3));
        this.gameDAO.generateRandomNum(gameModel.getAnswerOne(), gameModel.getAnswerTwo(), gameModel.getAnswerThree(), gameModel.getAnswerFour());
        return this.gameDAO.getGameId();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/guess")
    public int guess(@RequestBody Guess guess) {
        this.guessDAO.guessNum(guess);
        int currentRoundId = this.guessDAO.retrieveCurrentRound();

        if (currentRoundId >= 10) {
            return 0;
            // Return 0 for now, to exit this method if user guess more than 10 times
        }
        List<String> answerList = this.gameDAO.getAnswer(guess.getId());

        List<String> guessList = new ArrayList<String>();
        guessList.add(guess.getGuessOne());
        guessList.add(guess.getGuessTwo());
        guessList.add(guess.getGuessThree());
        guessList.add(guess.getGuessFour());

        int partialCounter = 0;
        int exactCounter = 0;

        for (int i = 0; i < answerList.size(); i++) {
            if (answerList.contains(guessList.get(i))) {
                if (Objects.equals(guessList.get(i), answerList.get(i))) {
                    exactCounter++;
                    System.out.println("EXACT");
                } else {
                    partialCounter++;
                    System.out.println("PARTIAL");
                }
            }
        }

        this.guessDAO.updateGuessResult(partialCounter, exactCounter, guess.getId(), currentRoundId);

        return 1;
    }
}
