package model;

import exceptions.InvalidChoiceRockPaperSizerException;

import java.util.Random;

//This class represents a rock paper sizer game,
//where the computer chooses randomly one of rock, paper, or sizer.
//compChoice is the computer's choice
public class RockPaperSizer extends Game {

    public static final String ROCK = "rock";
    public static final String PAPER = "paper";
    public static final String SIZER = "sizer";
    public static final String USER = "User";
    public static final String COMPUTER = "Computer";
    public static final String DRAW = "Draw";

    private String compChoice;
    private String manChoice;

    //EFFECTS: create new Game of type "Rock Paper Sizer"
    public RockPaperSizer() {
        super("Rock Paper Sizer");
    }

    //EFFECTS: returns winner. Return "Draw" if draw
    public String judge() {
        if (compChoice.equals(manChoice)) {
            return DRAW;
        } else if (compChoice.equals(ROCK) && manChoice.equals(PAPER)) {
            return USER;
        } else if (compChoice.equals(ROCK) && manChoice.equals(SIZER)) {
            return COMPUTER;
        } else if (compChoice.equals(PAPER) && manChoice.equals(ROCK)) {
            return COMPUTER;
        } else if (compChoice.equals(PAPER) && manChoice.equals(SIZER)) {
            return USER;
        } else if (compChoice.equals(SIZER) && manChoice.equals(ROCK)) {
            return USER;
        } else {
            return COMPUTER;
        }
    }

    //MODIFIES: this
    //EFFECTS: sets manChoice to choice
    public void setManChoice(String choice) throws InvalidChoiceRockPaperSizerException {
        if (choice.equals(ROCK) || choice.equals(SIZER) || choice.equals(PAPER)) {
            manChoice = choice;
        } else {
            throw new InvalidChoiceRockPaperSizerException();
        }
    }

    //MODIFIES: this
    //EFFECT: set compChoice to one of ROCK, PAPER, or SIZER randomly
    public void genCompChoice() {
        Random r = new Random();
        int rand = r.nextInt(3);

        if (rand == 0) {
            compChoice = ROCK;
        } else if (rand == 1) {
            compChoice = PAPER;
        } else {
            compChoice = SIZER;
        }
    }

    //getters
    public String getCompChoice() {
        return compChoice;
    }

    public String getManChoice() {
        return manChoice;
    }
}
