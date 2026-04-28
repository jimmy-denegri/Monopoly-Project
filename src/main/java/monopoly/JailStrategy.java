package monopoly;

public class JailStrategy {
    private JailStrategyType type;

    public JailStrategy(JailStrategyType type) {
        this.type = type;
    }

    // Returns true if the player's turn is over
    // Returns false if the player should continue with a normal roll
    public boolean handleJailTurn(Player player, MonopolyGame game) {
        // Always use Get Out of Jail Free card immediately if available
        if (player.getGetOutOfJailCards() > 0) {
            player.setGetOutOfJailCards(player.getGetOutOfJailCards() - 1);
            player.leaveJail();
            return false; // continue with normal turn
        }

        switch (type) {
            case IMMEDIATE_EXIT:
                // Strategy A: pay immediately and leave jail
                player.leaveJail();
                return false; // continue with normal turn

            case TRY_FOR_DOUBLES:
                // If player already tried 3 times, pay on next turn and leave
                if (player.getJailTurns() >= 3) {
                    player.leaveJail();
                    return false; // continue with normal turn
                }

                int die1 = game.rollDie();
                int die2 = game.rollDie();

                if (die1 == die2) {
                    // Rolled doubles: leave jail and move, but turn ends after this move
                    player.leaveJail();
                    player.move(die1 + die2);
                    game.resolveSquare();
                    return true;
                } else {
                    // Failed attempt, stay in jail
                    player.setJailTurns(player.getJailTurns() + 1);
                    return true;
                }
        }

        return true;
    }

    public JailStrategyType getType() {
        return type;
    }

    public void setType(JailStrategyType type) {
        this.type = type;
    }
}