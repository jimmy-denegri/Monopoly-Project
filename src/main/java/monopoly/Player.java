package monopoly;

public class Player {
    private int position;
    private boolean inJail;
    private int jailTurns;
    private int doublesCount;
    private int getOutOfJailCards;
    private Deck jailCardDeck;

    public Player() {
        this.position = 0; //The player begginings in the position 0 = "Go!";
        this.inJail = false; //The player doesn't start the game in jail, so that's false so far.
        this.jailTurns = 0; 
        this.doublesCount = 0; //It counts how many doubles numbers you got from the dice.
        this.getOutOfJailCards = 0; //The player doesn't have any card so far.
    }
    
    // To move the player around
    public void move(int spaces) {
        position = (position + spaces) % 40; //We use the remainder of 40 because the board has 40 spaces. 
    }

    //This function is gonna be used when the player is going to the jail.	
    public void goToJail() {
        position = 10; //The position 10 in the board send the player to the jail.
        inJail = true;
        jailTurns = 0;
        doublesCount = 0;
    }
    
    //This function is when the player is leaving from the jail.
    public void leaveJail() {
        inJail = false;
        jailTurns = 0;
    }

    // Return player's position.
    public int getPosition() { //get: returns value
        return position;
    }

    public void setPosition(int position) {
        this.position = position; //set: updates value, example: "Go to the jail!" card.
    }

    //Return if the player is in the jail or not.
    public boolean isInJail() {
        return inJail;
    }
    
    public Deck getJailCardDeck() {
        return jailCardDeck;
    }

    public void setJailCardDeck(Deck jailCardDeck) {
        this.jailCardDeck = jailCardDeck;
    }

    // Updating sending the player to the jail.
    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    // Check how many turns the player has been in the jail.	
    public int getJailTurns() {
        return jailTurns;
    }

    //Updating the turns the player has been in jail.
    public void setJailTurns(int jailTurns) {
        this.jailTurns = jailTurns;
    }

    //Returns how many times you got double numbers in the dice.
    public int getDoublesCount() {
        return doublesCount;
    }
    
    //Updating the times the double numbers.
    public void setDoublesCount(int doublesCount) {
        this.doublesCount = doublesCount;
    }

    //Returns current number of get out of jail cards.
    public int getGetOutOfJailCards() {
        return getOutOfJailCards;
    }

    //Update the number of cards to get out of jail the player has.
    public void setGetOutOfJailCards(int getOutOfJailCards) {
        this.getOutOfJailCards = getOutOfJailCards;
    }
}