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
    public String guess(@RequestBody Guess guess) {
        this.guessDAO.guessNum(guess);
        int currentRoundId = this.guessDAO.retrieveCurrentRound(guess);

        int gameResult = this.gameDAO.getGameResultForId(guess.getId());

        if (gameResult == 1) {
            return "Game has been completed";
        }

        if (currentRoundId >= 10) {
            this.gameDAO.updateGameResult(guess.getId());
            return "You have tried more than 10 times already, marking game as completed";
        }
        List<String> answerList = this.gameDAO.getAnswer(guess.getId());

        List<String> guessList = new ArrayList<String>();
        guessList.add(guess.getGuessOne());
        guessList.add(guess.getGuessTwo());
        guessList.add(guess.getGuessThree());
        guessList.add(guess.getGuessFour());

        int[] exactPartialCounter = compareResult(guessList, answerList);
        int exactCounter = exactPartialCounter[0];
        int partialCounter = exactPartialCounter[1];

        String resultMessage = "not quite right, try again!";

        this.guessDAO.updateGuessResult(partialCounter, exactCounter, guess.getId(), currentRoundId);

        if (exactCounter >= 4) {
            this.gameDAO.updateGameResult(guess.getId());
            resultMessage = "Well done, you got it correct, marking game as completed";
        }

        return resultMessage;
    }

    public int[] compareResult(List<String> guessList, List<String> answerList) {
        int partialCounter = 0;
        int exactCounter = 0;

        for (int i = 0; i < answerList.size(); i++) {
            if (answerList.contains(guessList.get(i))) {
                if (Objects.equals(guessList.get(i), answerList.get(i))) {
                    exactCounter++;
                } else {
                    partialCounter++;
                }
            }
        }

        return new int[]{exactCounter, partialCounter};
    }

    @GetMapping("/game")
    public List<Game> game() {
        return gameDAO.getListOfAllGames();
    }

    @GetMapping("/game/{id}")
    public List<Game> game(@PathVariable(name = "id") int id) {
        return gameDAO.getGameById(id);
    }

    @GetMapping("/round/{id}")
    public List<Guess> round(@PathVariable(name = "id") int id) {
        return guessDAO.getRoundById(id);
    }
}
