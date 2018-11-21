package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

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
        a.updateHighScores(new Score(200));
        a.updateHighScores(new Score(50));
        a2.updateHighScores(new Score(50));
        a2.updateHighScores(new Score(100));
        assertEquals(0, a.compareTo(a2));
        a.updateHighScores(new Score(49));
        assertEquals(-1, a.compareTo(a2));
    }

    /**
     * Test correctness of how scores update.
     */
    @Test
    public void testScoreUpdate(){
        assertEquals(0, a.getTopScore().getScoreValue());
        Score s = new Score(300);
        a.updateHighScores(s);
        a.updateHighScores(new Score(150));
        assertEquals(150, a.getTopScore().getScoreValue());
        a.updateHighScores(new Score(180));
        a.updateHighScores(new Score(200));
        a.updateHighScores(new Score(250));
        assertEquals(5, a.getHighScores().size());
        assertTrue(a.getHighScores().contains(s));
        a.updateHighScores(new Score(100));
        assertEquals(5, a.getHighScores().size());
        assertFalse(a.getHighScores().contains(s));
    }

    /**
     * Test if password can be properly set and authenticated.
     */
    @Test
    public void testPassword(){
        assertTrue(a.authenticate("pass"));
        assertEquals("user", a.getUsername());
        a.setPassword("pAss");
        assertFalse(a.authenticate("pass"));
        assertTrue(a.authenticate("pAss"));
    }
}