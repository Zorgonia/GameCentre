package fall2018.csc2017;

import org.junit.Test;

import fall2018.csc2017.Account;
import fall2018.csc2017.Interfaces.CurrentGameConstants;
import fall2018.csc2017.Score;

import static org.junit.Assert.*;

public class AccountTest implements CurrentGameConstants {

    private Account a = new Account("user", "pass");

    /**
     * Test whether experience and levels are calculated correctly.
     */
    @Test
    public void testExpCalc(){
        assertEquals(0, a.getLevel());
        assertEquals(0, a.getExperience());
        a.increaseExperience(99);
        assertEquals(1, a.getLevel());
        a.increaseExperience(2);
        assertEquals(2, a.getLevel());
    }

    /**
     * Test comparability of accounts (based on top score).
     */
    @Test
    public void testAccountComp(){
        Account a2 = new Account("u2", "p2");
        a.updateHighScores("Sliding Tiles", new Score(200));
        a.updateHighScores("Sliding Tiles",new Score(50));
        a2.updateHighScores("Sliding Tiles",new Score(50));
        a2.updateHighScores("Sliding Tiles",new Score(100));
        assertEquals(a.getTopScore(SLIDING_TILES).getScoreValue(), a2.getTopScore(SLIDING_TILES).getScoreValue());
        a.updateHighScores("Sliding Tiles",new Score(49));
        assertNotEquals(a.getTopScore(SLIDING_TILES).getScoreValue(), a2.getTopScore(SLIDING_TILES).getScoreValue());
    }

    /**
     * Test correctness of how scores update.
     */
    @Test
    public void testScoreUpdate(){
        assertEquals(0, a.getTopScore(SLIDING_TILES).getScoreValue());
        Score s = new Score(300);
        a.updateHighScores("Sliding Tiles",s);
        a.updateHighScores("Sliding Tiles",new Score(150));
        assertEquals(150, a.getTopScore(SLIDING_TILES).getScoreValue());
        a.updateHighScores("Sliding Tiles",new Score(180));
        a.updateHighScores("Sliding Tiles",new Score(200));
        a.updateHighScores("Sliding Tiles",new Score(250));
        assertEquals(5, a.getHighScores(SLIDING_TILES).size());
        assertTrue(a.getHighScores(SLIDING_TILES).contains(s));
        a.updateHighScores("Sliding Tiles",new Score(100));
        assertEquals(5, a.getHighScores(SLIDING_TILES).size());
        assertFalse(a.getHighScores(SLIDING_TILES).contains(s));
    }

    /**
     * Test if password can be properly set and authenticated.
     */
    @Test
    public void testPasswordCaseSensitive(){
        assertTrue(a.authenticate("pass"));
        assertEquals("user", a.getUsername());
        a.setPassword("pAss");
        assertFalse(a.authenticate("pass"));
        assertTrue(a.authenticate("pAss"));
    }

    /**
     * Test if password can be the same as username (should).
     */
    @Test
    public void testPasswordUsername(){
        a.setPassword("user");
        assertTrue(a.authenticate("user"));
    }
}
