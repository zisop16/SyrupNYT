package Games;

import java.util.*;

public class Connections implements NYTGame {
    private static final int GROUP_SIZE = 4;
    private static final int NUM_GROUPS = 4;
    private ArrayList<String> unsolvedWords;
    private ArrayList<String> solvedGroups;
    private HashMap<String, ArrayList<String>> words;
    private int lives;
    public Connections() {
        initialize();
    }
    public void initialize() {
        lives = 4;
        words = new HashMap<>();
        solvedGroups = new ArrayList<>();
        unsolvedWords = new ArrayList<>();
        ArrayList<String> especially = new ArrayList<>(Arrays.asList("Mighty", "Pretty", "Really", "Very"));
        unsolvedWords.addAll(especially);
        words.put("Especially (Easy)", especially);
        ArrayList<String> disneyCharacters = new ArrayList<>(Arrays.asList("Daisy", "Goofy", "Happy", "Lady"));
        unsolvedWords.addAll(disneyCharacters);
        words.put("Disney Characters (Medium)", disneyCharacters);
        ArrayList<String> kindsOfBlue = new ArrayList<>(Arrays.asList("Baby", "Navy", "Sky", "Tiffany"));
        unsolvedWords.addAll(kindsOfBlue);
        words.put("Kinds of Blue (Hard)", kindsOfBlue);
        ArrayList<String> bodyParts = new ArrayList<>(Arrays.asList("Army", "Colony", "Livery", "Shiny"));
        unsolvedWords.addAll(bodyParts);
        words.put("Body Parts Plus 'Y' (Hardest)", bodyParts);
        shuffle();
    }
    public void start() {
        ArrayList<String> options = new ArrayList<String>(Arrays.asList("Shuffle", "Make guess", "Give up"));
        while (true) {
            System.out.print(boardDisplay());
            int option = Main.grabOption("What would you like to do?", options);
            switch(option) {
                case 0: {
                    shuffle();
                    break;
                }
                case 1: {
                    HashSet<String> answer = getGuess();
                    guess(answer);
                    break;
                }
                case 2: {
                    giveUp();
                    break;
                }
            }
            if (lives == 0) {
                System.out.println("You died. Better luck next time.");
                break;
            }
            if (unsolvedWords.isEmpty()) {
                System.out.println("You solved all the connections!");
                System.out.print(boardDisplay());
                break;
            }
        }
    }
    public String boardDisplay() {
        StringBuilder toReturn = new StringBuilder("Solved Words:\n");
        for (String group : solvedGroups) {
            toReturn.append(group).append(": ");
            ArrayList<String> curr = words.get(group);
            for (int i = 0; i < GROUP_SIZE; i++) {
                String member = curr.get(i);
                toReturn.append(member);
                if (i != GROUP_SIZE - 1) {
                    toReturn.append(", ");
                }
            }
            toReturn.append('\n');
        }
        toReturn.append("Remaining Words:\n");
        for (int i = 0; i < unsolvedWords.size(); i++) {
            toReturn.append(unsolvedWords.get(i));
            if ((i % GROUP_SIZE) == (GROUP_SIZE - 1)) {
                toReturn.append('\n');
            } else {
                toReturn.append(", ");
            }
        }
        return toReturn.toString();
    }
    public int remainingLives() {
        return lives;
    }
    public void shuffle() {
        Collections.shuffle(unsolvedWords);
    }
    public HashSet<String> getGuess() {
        System.out.print("Give each word in the group separated by a single space: ");
        String response = Main.scanner.nextLine();
        String[] group = response.split(" ");
        if (group.length != GROUP_SIZE) {
            return getGuess();
        }
        for (int i = 0; i < group.length; i++) {
            group[i] = group[i].toLowerCase();
        }
        HashSet<String> asSet = new HashSet<String>(List.of(group));
        if (asSet.size() != GROUP_SIZE) {
            return getGuess();
        }
        ArrayList<String> remaining = (ArrayList<String>) unsolvedWords.clone();
        remaining.replaceAll(String::toLowerCase);
        boolean match = true;
        for (String curr : asSet) {
            if (!remaining.contains(curr)) {
                match = false;
                break;
            }
        }
        return match ? asSet : getGuess();
    }
    public void guess(HashSet<String> given) {
        String matchCategory = null;
        for (String category : words.keySet()) {
            ArrayList<String> target = words.get(category);
            boolean match = true;
            for (String curr : target) {
                curr = curr.toLowerCase();
                if (!given.contains(curr)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                matchCategory = category;
                break;
            }
        }
        if (matchCategory != null) {
            System.out.println("Correct, you solved the category: " + matchCategory);
            solvedGroups.add(matchCategory);
            for (String word : words.get(matchCategory)) {
                unsolvedWords.remove(word);
            }
        } else {
            lives--;
            System.out.println("Sorry, you are a failure. Remaining lives: " + lives);
        }
    }
    public void giveUp() {
        lives = 0;
    }
}
