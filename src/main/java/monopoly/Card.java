package monopoly;

public class Card {
    private String description;   // Text on the card
    private CardType type;        // What the card does
    private int value;            // Target index or move amount

    public Card(String description, CardType type, int value) {
        this.description = description;
        this.type = type;
        this.value = value;
    }

    // This is the most important method applies the card effect
    public void apply(Player player, MonopolyGame game, Deck sourceDeck) {
        switch (type) {

            case MOVE_TO:
                player.setPosition(value);
                game.resolveSquare();
                break;

            case GO_TO_JAIL:
                player.goToJail();
                break;

            case MOVE_RELATIVE:
                player.move(value);
                game.resolveSquare();
                break;

            case MOVE_TO_NEAREST_RAILROAD:
                int rrIndex = game.getBoard().nextRailroad(player.getPosition());
                player.setPosition(rrIndex);
                game.resolveSquare();
                break;

            case MOVE_TO_NEAREST_UTILITY:
                int utilIndex = game.getBoard().nextUtility(player.getPosition());
                player.setPosition(utilIndex);
                game.resolveSquare();
                break;

            case GET_OUT_OF_JAIL_FREE:
                player.setGetOutOfJailCards(player.getGetOutOfJailCards() + 1);
                player.setJailCardDeck(sourceDeck);
                break;

            case NO_MOVEMENT:
                break;
        }
    }

    // ===== Getters & Setters =====

    public String getDescription() {
        return description;
    }

    public CardType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setValue(int value) {
        this.value = value;
    }
}