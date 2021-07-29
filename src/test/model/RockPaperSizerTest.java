package model;

import exceptions.InvalidChoiceRockPaperSizerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Test for model.RockPaperSizer
class RockPaperSizerTest {

    public RockPaperSizer testRPS;

    @BeforeEach
    public void setUp() {
        testRPS = new RockPaperSizer();
    }

    @Test
    public void testGenCompChoice() {
        List<String> choices = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            testRPS.genCompChoice();
            choices.add(testRPS.getCompChoice());
        }
        assertTrue(choices.contains("rock"));
        assertTrue(choices.contains("paper"));
        assertTrue(choices.contains("sizer"));
    }

    @Test
    public void testAddManWin1() {
        testRPS.addManWin(1);
        assertEquals(1, testRPS.getManWin());
    }

    @Test
    public void testAddManWinMoreThan1() {
        testRPS.addManWin(3);
        assertEquals(3, testRPS.getManWin());
    }

    @Test
    public void testAddCompWin1() {
        testRPS.addCompWin(1);
        assertEquals(1, testRPS.getCompWin());
    }

    @Test
    public void testAddCompWinMoreThan1() {
        testRPS.addCompWin(3);
        assertEquals(3, testRPS.getCompWin());
    }

    @Test
    public void testGetCompChoice() {
        Collection<String> compChoices = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            testRPS.genCompChoice();
            compChoices.add(testRPS.getCompChoice());
        }
        assertTrue(compChoices.contains("rock"));
        assertTrue(compChoices.contains("paper"));
        assertTrue(compChoices.contains("sizer"));
    }

    @Test
    public void testJudgeRock(){
        try {
            testRPS.setManChoice(RockPaperSizer.ROCK);
            ArrayList<String> judgeResults = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                testRPS.genCompChoice();
                judgeResults.add(testRPS.judge());
                if (testRPS.getCompChoice().equals(RockPaperSizer.ROCK)) {
                    assertEquals("Draw", testRPS.judge());
                } else if (testRPS.getCompChoice().equals(RockPaperSizer.PAPER)) {
                    assertEquals("Computer", testRPS.judge());
                } else {
                    assertEquals("User", testRPS.judge());
                }
            }
            assertTrue(judgeResults.contains("Draw"));
            assertTrue(judgeResults.contains("Computer"));
            assertTrue(judgeResults.contains("User"));
        } catch (InvalidChoiceRockPaperSizerException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testJudgePaper(){
        try {
            testRPS.setManChoice(RockPaperSizer.PAPER);
            ArrayList<String> judgeResults = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                testRPS.genCompChoice();
                judgeResults.add(testRPS.judge());
                if (testRPS.getCompChoice().equals(RockPaperSizer.ROCK)) {
                    assertEquals("User", testRPS.judge());
                } else if (testRPS.getCompChoice().equals(RockPaperSizer.PAPER)) {
                    assertEquals("Draw", testRPS.judge());
                } else {
                    assertEquals("Computer", testRPS.judge());
                }
            }
            assertTrue(judgeResults.contains("Draw"));
            assertTrue(judgeResults.contains("Computer"));
            assertTrue(judgeResults.contains("User"));
        } catch (InvalidChoiceRockPaperSizerException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testJudgeSizer(){
        try {
            testRPS.setManChoice(RockPaperSizer.SIZER);
            ArrayList<String> judgeResults = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                testRPS.genCompChoice();
                judgeResults.add(testRPS.judge());
                if (testRPS.getCompChoice().equals(RockPaperSizer.ROCK)) {
                    assertEquals("Computer", testRPS.judge());
                } else if (testRPS.getCompChoice().equals(RockPaperSizer.PAPER)) {
                    assertEquals("User", testRPS.judge());
                } else {
                    assertEquals("Draw", testRPS.judge());
                }
            }
            assertTrue(judgeResults.contains("Draw"));
            assertTrue(judgeResults.contains("Computer"));
            assertTrue(judgeResults.contains("User"));
        } catch (InvalidChoiceRockPaperSizerException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testSetManChoiceNoException() {
        try {
            testRPS.setManChoice(RockPaperSizer.ROCK);
            assertEquals(RockPaperSizer.ROCK, testRPS.getManChoice());
            testRPS.setManChoice(RockPaperSizer.PAPER);
            assertEquals(RockPaperSizer.PAPER, testRPS.getManChoice());
            testRPS.setManChoice(RockPaperSizer.SIZER);
            assertEquals(RockPaperSizer.SIZER, testRPS.getManChoice());
        } catch (InvalidChoiceRockPaperSizerException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testSetManChoiceExceptionThrown() {
        try{
            testRPS.setManChoice("Tom");
            fail("Exception should be thrown");
        } catch (InvalidChoiceRockPaperSizerException e) {
            //success
        }
    }
}