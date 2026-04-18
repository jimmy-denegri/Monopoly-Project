package monopoly;

public class JailStrategy {
    private JailStrategyType type;

    public JailStrategy(JailStrategyType type) {
        this.type = type;
    }

    public void handleJailTurn(Player player, MonopolyGame game) {
        // TODO: implement jail strategy behavior
    }

    public JailStrategyType getType() {
        return type;
    }

    public void setType(JailStrategyType type) {
        this.type = type;
    }
}