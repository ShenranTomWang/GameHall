package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Test for persistence.JsonReader
class JsonReaderTest extends JsonTest {

    @Test
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //success
        }
    }

    @Test
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void testReaderEmptyUser() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyUser.json");
        try {
            User user = reader.read();
            assertEquals("testUser", user.getName());
            assertEquals(0, user.getNumberOfGames());
            assertEquals(0, user.getPoints());
            assertEquals(0, user.getFavouriteRounds().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public void testReaderGeneralUser() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUser.json");
        try {
            User user = reader.read();
            List<Game> favourites = user.getFavouriteRounds();
            assertEquals(2, favourites.size());
            checkGame("Rock Paper Sizer", 2, 3, favourites.get(0));
            checkGame("Guessing Number", 0, 1, favourites.get(1));
            assertEquals("testGeneralUser", user.getName());
            assertEquals(3, user.getPoints());
            assertEquals(2, user.getNumberOfGames());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
