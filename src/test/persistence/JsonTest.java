package persistence;

import model.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;

//This class implements a common method checkGame for JsonReaderTest and JsonWriterTest
public class JsonTest {
    //CITATION: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    protected void checkGame(String name, int manWin, int compWin, Game game) {
        assertEquals(name, game.getType());
        assertEquals(manWin, game.getManWin());
        assertEquals(compWin, game.getCompWin());
    }
}
