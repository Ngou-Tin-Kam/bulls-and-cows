package com.sg.model;

import org.springframework.stereotype.Component;

@Component
public class Guess {
    private int roundId;
    private int id;
    private String guessOne;
    private String guessTwo;
    private String guessThree;
    private String guessFour;
    private int partial;
    private int exact;

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuessOne() {
        return guessOne;
    }

    public void setGuessOne(String guessOne) {
        this.guessOne = guessOne;
    }

    public String getGuessTwo() {
        return guessTwo;
    }

    public void setGuessTwo(String guessTwo) {
        this.guessTwo = guessTwo;
    }

    public String getGuessThree() {
        return guessThree;
    }

    public void setGuessThree(String guessThree) {
        this.guessThree = guessThree;
    }

    public String getGuessFour() {
        return guessFour;
    }

    public void setGuessFour(String guessFour) {
        this.guessFour = guessFour;
    }

    public int getPartial() {
        return partial;
    }

    public void setPartial(int partial) {
        this.partial = partial;
    }

    public int getExact() {
        return exact;
    }

    public void setExact(int exact) {
        this.exact = exact;
    }
}
