package monopoly;

public class Player {
    private int position;
    private boolean inJail;
    private int jailTurns;
    private int doublesCount;
    private int getOutOfJailCards;

    public Player() {
        this.position = 0;
        this.inJail = false;
        this.jailTurns = 0;
        this.doublesCount = 0;
        this.getOutOfJailCards = 0;
    }

    public void move(int spaces) {
        // TODO: move player around board
    }

    public void goToJail() {
        // TODO: send player to jail
    }

    public void leaveJail() {
        // TODO: remove player from jail
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public int getJailTurns() {
        return jailTurns;
    }

    public void setJailTurns(int jailTurns) {
        this.jailTurns = jailTurns;
    }

    public int getDoublesCount() {
        return doublesCount;
    }

    public void setDoublesCount(int doublesCount) {
        this.doublesCount = doublesCount;
    }

    public int getGetOutOfJailCards() {
        return getOutOfJailCards;
    }

    public void setGetOutOfJailCards(int getOutOfJailCards) {
        this.getOutOfJailCards = getOutOfJailCards;
    }
}