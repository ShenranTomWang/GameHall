package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Test for model.GuessingNumber
public class GuessingNumberTest {
    public GuessingNumber testGN;

    @BeforeEach
    public void setUp() {
        testGN = new GuessingNumber();
    }

    @Test
    public void testGenCompNum() {
        List<Integer> compNumList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            testGN.genCompNum();
            compNumList.add(testGN.getCompNum());
        }
        assertTrue(compNumList.contains(0));
        assertTrue(compNumList.contains(100));
        assertTrue(compNumList.contains(10));
    }

    @Test
    public void testCompNumGreater() {
        testGN.setManNum(50);
        ArrayList<Boolean> results = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            testGN.genCompNum();
            results.add(testGN.isCompNumGreater());
        }
        assertTrue(results.contains(true));
        assertTrue(results.contains(false));
    }

    @Test
    public void testCompNumSmaller() {
        testGN.setManNum(50);
        ArrayList<Boolean> results = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            testGN.genCompNum();
            results.add(testGN.isCompNumSmaller());
        }
        assertTrue(results.contains(true));
        assertTrue(results.contains(false));
    }

    @Test
    public void testCompNumEqual() {
        testGN.setManNum(50);
        ArrayList<Boolean> results = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            testGN.genCompNum();
            results.add(testGN.isCompNumEqual());
        }
        assertTrue(results.contains(true));
        assertTrue(results.contains(false));
    }

    @Test
    public void testGetCompNum() {
        Collection<Integer> compNums = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            testGN.genCompNum();
            compNums.add(testGN.getCompNum());
        }
        assertTrue(compNums.contains(0));
        assertTrue(compNums.contains(100));
        assertTrue(compNums.contains(20));
    }

    @Test
    public void testSetManNum() {
        testGN.setManNum(1);
        assertEquals(1, testGN.getManNum());
        testGN.setManNum(60);
        assertEquals(60, testGN.getManNum());
    }
}
