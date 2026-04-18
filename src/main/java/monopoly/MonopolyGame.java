package monopoly;

public class MonopolyGame {
    private Board board;
    private Player player;
    private Deck chanceDeck;
    private Deck ccDeck;
    private JailStrategy strategy;
    private int totalMoves;

    public MonopolyGame() {
        this.board = new Board();
        this.player = new Player();
        this.chanceDeck = new Deck();
        this.ccDeck = new Deck();
        this.strategy = new JailStrategy(JailStrategyType.IMMEDIATE_EXIT);
        this.totalMoves = 0;
    }

    public void runSimulation(int n) {
        // TODO: run simulation for n turns
    }

    public void playTurn() {
        // TODO: play one turn
    }

    public void resolveSquare() {
        // TODO: resolve current square effect
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public Deck getChanceDeck() {
        return chanceDeck;
    }

    public Deck getCcDeck() {
        return ccDeck;
    }

    public JailStrategy getStrategy() {
        return strategy;
    }

    public int getTotalMoves() {
        return totalMoves;
    }

    public void setStrategy(JailStrategy strategy) {
        this.strategy = strategy;
    }
}