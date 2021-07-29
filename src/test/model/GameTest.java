package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Test for model.Game
public class GameTest {
    public Game testGame;

    @BeforeEach
    public void setUp() {
        testGame = new Game("testGame");
    }

    @Test
    public void testAddManWin() {
        testGame.addManWin(2);
        assertEquals(2, testGame.getManWin());
    }

    @Test
    public void testAddCompWin() {
        testGame.addCompWin(2);
        assertEquals(2, testGame.getCompWin());
    }

    @Test
    public void testGetManWin() {
        assertEquals(0, testGame.getManWin());
    }

    @Test
    public void testGetCompWin() {
        assertEquals(0, testGame.getCompWin());
    }

    @Test
    public void testGetName() {
        assertEquals("testGame", testGame.getType());
    }
}
