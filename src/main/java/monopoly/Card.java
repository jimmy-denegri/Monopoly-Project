package monopoly;

public class Card {
    private String description;
    private CardType type;
    private int targetIndex;

    public Card(String description, CardType type, int targetIndex) {
        this.description = description;
        this.type = type;
        this.targetIndex = targetIndex;
    }

    public void apply(Player player, MonopolyGame game) {
        // TODO: apply card effect
    }

    public String getDescription() {
        return description;
    }

    public CardType getType() {
        return type;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setTargetIndex(int targetIndex) {
        this.targetIndex = targetIndex;
    }
}