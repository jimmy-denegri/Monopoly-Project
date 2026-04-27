package monopoly;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Square> squares;

    public Board() {
        squares = new ArrayList<>();
        initializeBoard();
    }

    // Creates all 40 Monopoly board squares in the correct order
    private void initializeBoard() {
        squares.add(new Square("GO", SquareType.GO, 0));
        squares.add(new Square("Mediterranean Avenue", SquareType.PROPERTY, 1));
        squares.add(new Square("Community Chest", SquareType.CHEST, 2));
        squares.add(new Square("Baltic Avenue", SquareType.PROPERTY, 3));
        squares.add(new Square("Income Tax", SquareType.PROPERTY, 4));
        squares.add(new Square("Reading Railroad", SquareType.RAILROAD, 5));
        squares.add(new Square("Oriental Avenue", SquareType.PROPERTY, 6));
        squares.add(new Square("Chance", SquareType.CHANCE, 7));
        squares.add(new Square("Vermont Avenue", SquareType.PROPERTY, 8));
        squares.add(new Square("Connecticut Avenue", SquareType.PROPERTY, 9));
        squares.add(new Square("Jail", SquareType.JAIL, 10));
        squares.add(new Square("St. Charles Place", SquareType.PROPERTY, 11));
        squares.add(new Square("Electric Company", SquareType.UTILITY, 12));
        squares.add(new Square("States Avenue", SquareType.PROPERTY, 13));
        squares.add(new Square("Virginia Avenue", SquareType.PROPERTY, 14));
        squares.add(new Square("Pennsylvania Railroad", SquareType.RAILROAD, 15));
        squares.add(new Square("St. James Place", SquareType.PROPERTY, 16));
        squares.add(new Square("Community Chest", SquareType.CHEST, 17));
        squares.add(new Square("Tennessee Avenue", SquareType.PROPERTY, 18));
        squares.add(new Square("New York Avenue", SquareType.PROPERTY, 19));
        squares.add(new Square("Free Parking", SquareType.PROPERTY, 20));
        squares.add(new Square("Kentucky Avenue", SquareType.PROPERTY, 21));
        squares.add(new Square("Chance", SquareType.CHANCE, 22));
        squares.add(new Square("Indiana Avenue", SquareType.PROPERTY, 23));
        squares.add(new Square("Illinois Avenue", SquareType.PROPERTY, 24));
        squares.add(new Square("B. & O. Railroad", SquareType.RAILROAD, 25));
        squares.add(new Square("Atlantic Avenue", SquareType.PROPERTY, 26));
        squares.add(new Square("Ventnor Avenue", SquareType.PROPERTY, 27));
        squares.add(new Square("Water Works", SquareType.UTILITY, 28));
        squares.add(new Square("Marvin Gardens", SquareType.PROPERTY, 29));
        squares.add(new Square("Go To Jail", SquareType.GO_TO_JAIL, 30));
        squares.add(new Square("Pacific Avenue", SquareType.PROPERTY, 31));
        squares.add(new Square("North Carolina Avenue", SquareType.PROPERTY, 32));
        squares.add(new Square("Community Chest", SquareType.CHEST, 33));
        squares.add(new Square("Pennsylvania Avenue", SquareType.PROPERTY, 34));
        squares.add(new Square("Short Line", SquareType.RAILROAD, 35));
        squares.add(new Square("Chance", SquareType.CHANCE, 36));
        squares.add(new Square("Park Place", SquareType.PROPERTY, 37));
        squares.add(new Square("Luxury Tax", SquareType.PROPERTY, 38));
        squares.add(new Square("Boardwalk", SquareType.PROPERTY, 39));
    }

    // Returns the square at a given board position
    public Square getSquare(int index) {
        return squares.get(index);
    }

    // Finds the next railroad after the given position
    public int nextRailroad(int fromIndex) {
        for (int i = 1; i <= 40; i++) {
            int index = (fromIndex + i) % 40;
            if (squares.get(index).getType() == SquareType.RAILROAD) {
                return index;
            }
        }
        return -1;
    }

    // Finds the next utility after the given position
    public int nextUtility(int fromIndex) {
        for (int i = 1; i <= 40; i++) {
            int index = (fromIndex + i) % 40;
            if (squares.get(index).getType() == SquareType.UTILITY) {
                return index;
            }
        }
        return -1;
    }

    // Returns the position of the Jail square
    public int getJailIndex() {
        return 10;
    }

    // Returns the full list of squares
    public List<Square> getSquares() {
        return squares;
    }

    // Optional helper method: returns total number of squares
    public int size() {
        return squares.size();
    }
}