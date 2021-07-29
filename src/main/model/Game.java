package model;

import org.json.JSONObject;
import persistence.Writable;

//This class represents a game in general, with manWin (times human player win),
//compWin (times computer win),
//and type
public class Game implements Writable {
    private int manWin;
    private int compWin;
    private String type;

    //EFFECTS: creates new game with name name and initialize manWin and compWin to be 0
    public Game(String type) {
        this.type = type;
        manWin = 0;
        compWin = 0;
    }

    //EFFECTS: creates new game with name, manWin and compWin, used only to restore data
    public Game(String type, int manWin, int compWin) {
        this.type = type;
        this.manWin = manWin;
        this.compWin = compWin;
    }

    //REQUIRES: points > 0
    //MODIFIES: this
    //EFFECTS: add points to manWin
    public void addManWin(int points) {
        manWin += points;
    }

    //REQUIRES: points > 0
    //MODIFIES: this
    //EFFECTS: add points to compWin
    public void addCompWin(int points) {
        compWin += points;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("manWin", manWin);
        jsonObject.put("compWin", compWin);
        jsonObject.put("type", type);

        return jsonObject;
    }

    //getters
    public int getManWin() {
        return manWin;
    }

    public int getCompWin() {
        return compWin;
    }

    public String getType() {
        return type;
    }
}
