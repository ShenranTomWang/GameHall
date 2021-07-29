package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

//This class represents a user,
//with name, points (games won), numberOfGames, numberOfRounds, favouriteRounds list,
//where a round is a full game (i.e. a full round of rock paper sizer),
//and a game is one specific play (i.e. a single rock paper sizer play)
public class User extends Observable implements Writable {

    private String name;
    private int points;
    private int numberOfGames;
    List<Game> favouriteRounds;

    //EFFECTS: creates new user with name, 0 points, 0 numberOfGames, 0 numberOfRounds and empty favouriteRounds
    public User(String name) {
        this.name = name;
        points = 0;
        numberOfGames = 0;
        favouriteRounds = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add game to favouriteGames
    public void addFavouriteRounds(Game game) {
        favouriteRounds.add(game);
        setChanged();
        notifyObservers(game);
    }

    //REQUIRES: p > 0
    //MODIFIES: this
    //EFFECTS: add p to points
    public void addPoints(int p) {
        points += p;
    }

    //REQUIRES: g > 0
    //MODIFIES: this
    //EFFECTS: add g to numberOfGames
    public void addNumberOfGames(int g) {
        numberOfGames += g;
    }

    //REQUIRES: points <= numberOfGames
    //EFFECTS: return rate of winning of user
    public double winRate() {
        double doublePoints = points;
        double doubleNumberOfGames = numberOfGames;
        if (!(numberOfGames == 0)) {
            return 100 * (doublePoints / doubleNumberOfGames);
        } else {
            return 0;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", name);
        jsonObject.put("number of games", numberOfGames);
        jsonObject.put("points", points);
        JSONArray jsonArray = new JSONArray();
        for (Game g : favouriteRounds) {
            jsonArray.put(g.toJson());
        }
        jsonObject.put("favourite rounds", jsonArray);

        return jsonObject;
    }

    //getters
    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public List<Game> getFavouriteRounds() {
        return favouriteRounds;
    }
}
