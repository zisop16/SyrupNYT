package Games;

public class Word implements Comparable<Word> {
    private OrderedPair location;
    private String word;
    private String hint;
    private boolean solved;
    public Word(int x, int y, String word, String hint) {
        location = new OrderedPair(x, y);
        this.word = word;
        this.hint = hint;
        solved = false;
    }
    public int compareTo(Word other) {
        int locationDiff = location.compareTo(other.location);
        if (locationDiff != 0) {
            return locationDiff;
        }
        return this.word.compareTo(other.word);
    }

    public boolean Solved() {
        return solved;
    }
    public void solve() {
        solved = true;
    }
    public String getHint() {
        return hint;
    }
    public OrderedPair getLocation() {
        return location;
    }
    public int getX() {
        return location.X();
    }
    public int getY() {
        return location.Y();
    }
    public String getWord() {
        return word;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Word)) {
            return false;
        }
        Word asWord = (Word) other;
        return location.equals(asWord.location) && word.equals(asWord.word);
    }
    public int hashCode() {
        return location.hashCode() + word.hashCode();
    }
}
