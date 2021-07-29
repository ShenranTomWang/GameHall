package ui;

//This is the main class, initiates GameHall
public class Main {
    private static GameHall gameHall;

    //EFFECTS: run GameHall
    public static void main(String[] args) {
        gameHall = new GameHall();
    }
}