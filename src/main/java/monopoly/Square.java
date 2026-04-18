package monopoly;

public class Square {
    private String name;
    private SquareType type;
    private int index;

    public Square(String name, SquareType type, int index) {
        this.name = name;
        this.type = type;
        this.index = index;
    }

    public void resolve(Player player, MonopolyGame game) {
        // TODO: resolve effect of this square
    }

    public String getName() {
        return name;
    }

    public SquareType getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(SquareType type) {
        this.type = type;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}