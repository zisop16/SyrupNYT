package Games;

public class OrderedPair implements Comparable<OrderedPair>{
    private int x;
    private int y;
    public OrderedPair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int X() {
        return x;
    }
    public int Y() {
        return y;
    }
    public int compareTo(OrderedPair other) {
        int xDiff = x - other.x;
        if (xDiff != 0) {
            return xDiff;
        }
        return y - other.y;
    }
    public boolean equals(Object other) {
        if (!(other instanceof OrderedPair asPair)) {
            return false;
        }
        return asPair.x == x && asPair.y == y;
    }
    public int hashCode() {
        return x + y;
    }
}
