package monopoly;

public class JailStrategy { //This class is going to explain which strategy the player is going to use to leave from the jail.
    private JailStrategyType type;

    public JailStrategy(JailStrategyType type) {
        this.type = type;
    }

    /*
     * It's going to decide what happens in the game when the player is at the jail (it will depends on the type).
     * Returns true if the player's turn is over
     * Returns false if the player should continue with a normal roll
     */
    public boolean handleJailTurn(Player player, MonopolyGame game) {
    	// Always use Get Out of Jail Free card immediately if available
    	if (player.getGetOutOfJailCards() > 0) {
    		
    		player.setGetOutOfJailCards(player.getGetOutOfJailCards() - 1);
            player.leaveJail();
            return false; // continue with normal turn	
    	}
    	
    	switch (type) {
    		case IMMEDIATE_EXIT: //In this case the player would use the card "Get Out of Jail Free".
    			player.leaveJail();
    			return false;
    			
    		case TRY_FOR_DOUBLES: //In this case the player would use the dices to get out of jail.
    			if (player.getJailTurns() >= 3) {
    			player.leaveJail();
    				return false;  				
    	}		
    			int die1 = game.rollDie();
    			int die2 = game.rollDie();
    			
    			if(die1 == die2) { //If both dices are same value, the player will get out of jail.
    				player.leaveJail();
    				player.move(die1 + die2);
                    game.resolveSquare();
                    return true;
    			} else {
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