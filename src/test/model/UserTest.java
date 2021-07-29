package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

//Test for model.User
public class UserTest {

    public User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User("testUser");
    }

    @Test
    public void testAddPoints() {
        assertEquals(0, testUser.getPoints());

        testUser.addPoints(1);
        assertEquals(1, testUser.getPoints());

        testUser.addPoints(3);
        assertEquals(4, testUser.getPoints());
    }

    @Test
    public void testAddNumberOfGames() {
        testUser.addNumberOfGames(2);
        assertEquals(2, testUser.getNumberOfGames());
    }

    @Test
    public void testWinRateEmpty() {
        assertEquals(0, testUser.winRate());
    }

    @Test
    public void testWinRate100() {
        testUser.addPoints(1);
        testUser.addNumberOfGames(1);
        assertEquals(100, testUser.winRate());
    }

    @Test
    public void testWinRateNot100() {
        testUser.addPoints(1);
        testUser.addNumberOfGames(4);
        assertEquals(25, testUser.winRate());
    }

    @Test
    public void testAddFavouriteGame() {
        Game testGame = new Game("Test Game");
        testUser.addFavouriteRounds(testGame);
        assertEquals(1, testUser.getFavouriteRounds().size());
        assertTrue(testUser.getFavouriteRounds().contains(testGame));
    }

    @Test
    public void testGetName() {
        assertEquals("testUser", testUser.getName());
    }

    @Test
    public void testGetPoints() {
        assertEquals(0, testUser.getPoints());
    }

    @Test
    public void testGetNumberOfGames() {
        assertEquals(0, testUser.getNumberOfGames());
    }

    @Test
    public void testGetFavouriteGames() {
        Collection<Game> emptyList = new ArrayList<>();
        assertEquals(emptyList, testUser.getFavouriteRounds());
    }

    @Test
    public void testToJson0Fav() {
        testUser.addPoints(4);
        testUser.addNumberOfGames(4);
        assertEquals("testUser", testUser.toJson().getString("name"));
        assertEquals(4, testUser.toJson().getInt("number of games"));
        assertEquals(4, testUser.toJson().getInt("points"));
        assertEquals(0, testUser.toJson().getJSONArray("favourite rounds").length());
    }

    @Test
    public void testToJson1Fav() {
        Game g = new Game("Test Game");
        testUser.addPoints(4);
        testUser.addNumberOfGames(4);
        testUser.addFavouriteRounds(g);
        assertEquals("testUser", testUser.toJson().getString("name"));
        assertEquals(4, testUser.toJson().getInt("number of games"));
        assertEquals(4, testUser.toJson().getInt("points"));
        assertEquals(1, testUser.toJson().getJSONArray("favourite rounds").length());
    }

    @Test
    public void testToJsonMoreThan1Fav() {
        Game g = new Game("Test Game");
        testUser.addPoints(4);
        testUser.addNumberOfGames(4);
        testUser.addFavouriteRounds(g);
        testUser.addFavouriteRounds(g);
        testUser.addFavouriteRounds(g);
        assertEquals("testUser", testUser.toJson().getString("name"));
        assertEquals(4, testUser.toJson().getInt("number of games"));
        assertEquals(4, testUser.toJson().getInt("points"));
        assertEquals(3, testUser.toJson().getJSONArray("favourite rounds").length());
    }
}
