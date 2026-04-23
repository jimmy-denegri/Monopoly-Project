package monopoly;

public class JailStrategy { //This class is going to explain which strategy the player is going to use to leave from the jail.
    private JailStrategyType type;

    public JailStrategy(JailStrategyType type) {
        this.type = type;
    }

    /*
     * It's going to decide what happens in the game when the player is at the jail (it will depends on the type).
     */
    public void handleJailTurn(Player player, MonopolyGame game) {
    	switch (type) {
    		case IMMEDIATE_EXIT: //In this case the player would use the card "Get Out of Jail Free".
    			player.leaveJail();
    			break;
    			
    		case TRY_FOR_DOUBLES: //In this case the player would use the dices to get out of jail.
    			int die1 = game.rollDie();
    			int die2 = game.rollDie();
    			
    			if(die1 == die2) { //If both dices are same value, the player will get out of jail.
    				player.leaveJail();
    			}
    			
    				break;
    	}
    }

    public JailStrategyType getType() {
        return type;
    }

    public void setType(JailStrategyType type) {
        this.type = type;
    }
}