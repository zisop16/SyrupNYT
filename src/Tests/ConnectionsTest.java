package Tests;

import Games.Connections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionsTest {
    private Connections game;
    @BeforeEach
    public void setUp() {
        game = new Connections();
    }
    @Test
    public void testInitialization() {
        game.initialize();
        String display = game.boardDisplay();

        String target = display.substring(0, 31);
        assertEquals("Solved Words:\nRemaining Words:\n", target);
        ArrayList<String> words = new ArrayList<>(Arrays.asList("Mighty", "Pretty", "Really",
                "Very", "Daisy", "Goofy", "Happy", "Lady",
                "Baby", "Navy", "Sky", "Tiffany",
                "Army", "Colony", "Livery", "Shiny"));
        for (String word : words) {
            assert(display.contains(word));
        }
        assertEquals(game.remainingLives(), 4);
    }
    @Test
    public void testGoodGuess() {
        game.initialize();
        HashSet<String> guess = new HashSet<>(Arrays.asList("mighty", "pretty", "really", "very"));

        game.guess(guess);
        String display = game.boardDisplay();
        int solvedIndex = display.indexOf('\n');
        String target = "Especially (Easy): Mighty, Pretty, Really, Very\n";
        assertEquals(solvedIndex + 1, display.indexOf(target));
        assertEquals(game.remainingLives(), 4);
    }
    @Test
    public void testBadGuess() {
        game.initialize();
        HashSet<String> guess = new HashSet<>(Arrays.asList("goofy", "pretty", "really", "very"));
        game.guess(guess);
        String display = game.boardDisplay();
        int solvedIndex = display.indexOf('\n');
        String target = "Remaining Words:\n";
        assertEquals(solvedIndex + 1, display.indexOf(target));
        assertEquals(game.remainingLives(), 3);
    }
}