package Games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MiniCrossword implements NYTGame {
    private HashMap<Integer, Word> down;
    private HashMap<Integer, Word> across;
    private static final int GRID_SIZE = 5;
    private int[][] grid;
    public MiniCrossword() {
        initialize();
    }
    public void initialize() {
        grid = new int[GRID_SIZE][];
        grid[0] = new int[]{0, 1, 2, 3, 4};
        grid[1] = new int[]{5, -1, -1, -1, -1};
        grid[2] = new int[]{6, -1, -1, -1, -1};
        grid[3] = new int[]{7, -1, -1, -1, -1};
        grid[4] = new int[]{0, 8, -1, -1, -1};
        down = new HashMap<>();
        across = new HashMap<>();
        down.put(1, new Word(1, 0, "email", "Online memo"));
        down.put(2, new Word(2, 0, "vitro", "The \"V\" of IVF"));
        down.put(3, new Word(3, 0, "ethan", "Allen of the Green Mountain Boys"));
        down.put(4, new Word(4, 0, "rhyme", "What birds and nerds do"));
        down.put(5, new Word(0, 1, "sch", "Learning ctr."));
        across.put(1, new Word(1, 0, "ever", "Always"));
        across.put(5, new Word(0, 1, "smith", "Women's college in Northampton, Massachusetts"));
        across.put(6, new Word(0, 2, "cathy", "Heathcliff's love in \"Wuthering Heights\""));
        across.put(7, new Word(0, 3, "hiram", "Distiller Walker"));
        across.put(8, new Word(1, 4, "lone", "One and only"));
    }

    public void start() {
        boolean won = false;
        while (true) {
            System.out.print(boardDisplay());
            int option = Main.grabOption("What would you like to do?", new ArrayList<>(
                    Arrays.asList("Show across hints", "Show down hints", "Show all hints", "Solve word", "Give up"
                    ))
            );
            switch(option) {
                case 0: {
                    String hints = acrossHints();
                    System.out.print(hints);
                    break;
                }
                case 1: {
                    String hints = downHints();
                    System.out.print(hints);
                    break;
                }
                case 2: {
                    String hints = acrossHints() + downHints();
                    System.out.print(hints);
                    break;
                }
                case 3: {
                    boolean across = Main.grabOption("Down or across?", new ArrayList<>(Arrays.asList(
                            "Down", "Across"
                    ))) == 1;
                    int number = Main.grabIntOption("Which number will you solve?");
                    while (true) {
                        if (across) {
                            if (!this.across.containsKey(number)) {
                                number = Main.grabIntOption("Which number will you solve?");
                                continue;
                            }
                        } else {
                            if (!this.down.containsKey(number)) {
                                number = Main.grabIntOption("Which number will you solve?");
                                continue;
                            }
                        }
                        break;
                    }
                    System.out.print("What is your guess? ");
                    String guess = Main.scanner.nextLine();
                    solve(across, number, guess);
                    break;
                }
                case 4: {
                    System.out.println("See you later quitter.");
                    break;
                }
            }
            if (option == 4) {
                break;
            }
            if (done()) {
                won = true;
                break;
            }
        }
        if (won) {
            System.out.println("Wow, you solved everything. Good job.");
            System.out.print(boardDisplay());
        }
    }
    private boolean done() {
        for (int number : across.keySet()) {
            Word curr = across.get(number);
            if (!curr.Solved()) {
                return false;
            }
        }
        for (int number : down.keySet()) {
            Word curr = down.get(number);
            if (!curr.Solved()) {
                return false;
            }
        }
        return true;
    }
    private void solve(boolean across, int number, String guess) {
        guess = guess.toLowerCase();
        Word target;
        if (across) {
            target = this.across.get(number);
        } else {
            target = down.get(number);
        }
        String targetWord = target.getWord().toLowerCase();
        if (guess.equals(targetWord)) {
            System.out.println("That's correct, " + number + (across ? " across" : " down") + " is " + guess);
            target.solve();
        } else {
            System.out.println("Wrong, you are a loser.");
        }
    }
    private static String strMult(String s, int m) {
        StringBuilder toReturn = new StringBuilder();
        for (int i = 0; i < m; i++) {
            toReturn.append(s);
        }
        return toReturn.toString();
    }
    public String acrossHints() {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("Across:\n");
        for (Integer key : across.keySet()) {
            Word curr = across.get(key);
            toReturn.append(key + ". " + curr.getHint() + '\n');
        }
        return toReturn.toString();
    }
    public String downHints() {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("Down:\n");
        for (Integer key : down.keySet()) {
            Word curr = down.get(key);
            toReturn.append(key + ". " + curr.getHint() + '\n');
        }
        return toReturn.toString();
    }
    public String boardDisplay() {
        StringBuilder toReturn = new StringBuilder();
        final int SQUARE_SIZE = 2;
        toReturn.append("  ");
        toReturn.append(strMult("_", (SQUARE_SIZE + 1) * GRID_SIZE));
        toReturn.append('\n');
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int k = 0; k < SQUARE_SIZE; k++) {
                toReturn.append("| ");
                for (int j = 0; j < GRID_SIZE; j++) {
                    if (grid[i][j] == 0) {
                        toReturn.append(strMult("X", SQUARE_SIZE));
                    }
                    else if (k == 0) {
                        // Print the number if we are in the top left corner
                        int number = grid[i][j];
                        if (number > 0) {
                            toReturn.append(number);
                        } else {
                            toReturn.append(' ');
                        }
                        toReturn.append(strMult(" ", SQUARE_SIZE - 1));
                    } else if (k == SQUARE_SIZE - 1) {
                        //Print a character if it is solved and we are in the bottom right corner
                        toReturn.append(strMult(" ", SQUARE_SIZE - 1));
                        toReturn.append(getCharacter(i, j));
                    } else {
                        toReturn.append(strMult(" ", SQUARE_SIZE));
                    }
                    toReturn.append(' ');
                }
                toReturn.append("|\n");
            }
        }
        toReturn.append("  ");
        toReturn.append(strMult("_", (SQUARE_SIZE + 1) * GRID_SIZE));
        toReturn.append("\n");
        return toReturn.toString();
    }

    private char getCharacter(int row, int col) {
        int offset = 0;
        while (true) {
            // Move vertically upward until we find the down word which connects to this position
            int curr = grid[row - offset][col];
            if (curr > 0) {
                Word target = down.get(curr);
                if (target != null) {
                    if (target.Solved()) {
                        return target.getWord().charAt(offset);
                    } else {
                        break;
                    }
                }
            }
            offset++;
        }
        offset = 0;
        while (true) {
            int curr = grid[row][col - offset];
            if (curr > 0) {
                Word target = across.get(curr);
                if (target != null) {
                    if (target.Solved()) {
                        return target.getWord().charAt(offset);
                    } else {
                        return ' ';
                    }
                }
            }
            offset++;
        }
    }
}
