package persistence;

import model.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Test for persistence.JsonWriter
public class JsonWriterTest extends JsonTest{
    @Test
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            //success
        }
    }

    @Test
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void testWriterEmptyUser() {
        try {
            User user = new User("testEmptyUser");
            JsonWriter writer = new JsonWriter("./data/testWriterEmpty.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
            user = reader.read();
            assertEquals("testEmptyUser", user.getName());
            assertEquals(0, user.getNumberOfGames());
            assertEquals(0, user.getPoints());
            assertEquals(0, user.getFavouriteRounds().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    void testWriterGeneralUser() {
        try {
            User user = new User("testGeneralUser");
            user.addFavouriteRounds(new Game("Rock Paper Sizer", 2, 1));
            user.addFavouriteRounds(new Game("Guessing Number", 4, 5));
            user.addNumberOfGames(12);
            user.addPoints(6);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUser.json");
            user = reader.read();
            assertEquals("testGeneralUser", user.getName());
            List<Game> favourites = user.getFavouriteRounds();
            assertEquals(2, favourites.size());
            checkGame("Rock Paper Sizer", 2, 1, favourites.get(0));
            checkGame("Guessing Number", 4, 5, favourites.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
