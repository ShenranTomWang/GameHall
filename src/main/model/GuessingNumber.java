package model;

import java.util.Random;

//This class represents a guessing number game,
//where the computer randomly chooses a number [0, 100],
//and the user is allowed to ask 5 questions and guess the number.
//compNum is the number computer choose
public class GuessingNumber extends Game {

    public static final int UPPER_BOUND = 100;
    public static final int LOWER_BOUND = 0;
    public static final int MAX_QUESTIONS = 5;

    private int compNum;
    private int manNum;

    //EFFECTS: creates new game with type "Guessing Number"
    public GuessingNumber() {
        super("Guessing Number");
    }

    //MODIFIES: this
    //EFFECTS: sets this.manNum to manNum
    public void setManNum(int manNum) {
        this.manNum = manNum;
    }

    //MODIFIES: this
    //EFFECTS: sets compNum a random number in bound LOWER_BOUND - UPPER_BOUND
    public void genCompNum() {
        Random rand = new Random();
        compNum = rand.nextInt(UPPER_BOUND + 1) + LOWER_BOUND;
    }

    //EFFECTS: returns true if num > compNum
    public Boolean isCompNumGreater() {
        return compNum > manNum;
    }

    //EFFECTS: returns true if num < compNum
    public Boolean isCompNumSmaller() {
        return compNum < manNum;
    }

    //EFFECTS: returns true if num = compNum
    public Boolean isCompNumEqual() {
        return compNum == manNum;
    }

    //getters
    public int getCompNum() {
        return compNum;
    }

    public int getManNum() {
        return manNum;
    }
}
