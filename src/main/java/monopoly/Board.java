package monopoly;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Square> squares;

    public Board() {
        this.squares = new ArrayList<>();
        // TODO: initialize all 40 Monopoly squares
    }

    public Square getSquare(int index) {
        return squares.get(index);
    }

    public int nextRailroad(int fromIndex) {
        // TODO: return next railroad index
        return -1;
    }

    public int nextUtility(int fromIndex) {
        // TODO: return next utility index
        return -1;
    }

    public int getJailIndex() {
        // TODO: return jail square index
        return -1;
    }

    public List<Square> getSquares() {
        return squares;
    }
}