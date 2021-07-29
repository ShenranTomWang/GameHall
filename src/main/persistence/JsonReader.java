package persistence;

import model.Game;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//This class represents a tool that reads the JSON storage file
public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads user from file and returns it;
    //         throws IOException if an error occurs reading data from file
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: parses user from JSON object and returns it
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    private User parseUser(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        User user = new User(name);
        JSONArray jsonArray = jsonObject.getJSONArray("favourite rounds");
        for (Object json : jsonArray) {
            JSONObject nextRound = (JSONObject) json;
            String gameName = nextRound.getString("type");
            int manWin = nextRound.getInt("manWin");
            int compWin = nextRound.getInt("compWin");
            Game game = new Game(gameName, manWin, compWin);
            user.addFavouriteRounds(game);
        }
        user.addNumberOfGames(jsonObject.getInt("number of games"));
        user.addPoints(jsonObject.getInt("points"));
        return user;
    }
}
