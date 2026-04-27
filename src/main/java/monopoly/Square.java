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
        switch (type) {
            case GO:
            case PROPERTY:
            case RAILROAD:
            case UTILITY:
            case JAIL:
                // No special action for these in the simulator
                break;

            case GO_TO_JAIL:
                player.goToJail();
                break;

            case CHANCE:
                Card chanceCard = game.getChanceDeck().draw();
                if (chanceCard != null) {
                	chanceCard.apply(player, game, game.getChanceDeck());
                }
                break;

            case CHEST:
                Card chestCard = game.getCcDeck().draw();
                if (chestCard != null) {
                	chestCard.apply(player, game, game.getCcDeck());
                }
                break;
        }
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