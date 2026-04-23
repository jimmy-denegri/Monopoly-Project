package monopoly;

import java.util.Random;

public class MonopolyGame {
    // Core components of the game
    private Board board;              // Holds all 40 squares
    private Player player;            // The single player in simulation
    private Deck chanceDeck;          // Chance cards
    private Deck ccDeck;              // Community Chest cards
    private JailStrategy strategy;    // How player behaves in jail
    private Statistics stats;         // Tracks landing frequencies

    private int totalMoves;           // Total number of turns taken
    private Random random;            // Used to simulate dice rolls

    // Constructor: initializes everything needed for the game
    public MonopolyGame() {
        board = new Board();
        player = new Player();
        chanceDeck = new Deck();
        ccDeck = new Deck();
        strategy = new JailStrategy(JailStrategyType.IMMEDIATE_EXIT);
        stats = new Statistics(40);   // 40 squares on Monopoly board
        totalMoves = 0;
        random = new Random();
    }

    // Runs the simulation for n turns
    public void runSimulation(int n) {
        for (int i = 0; i < n; i++) {
            playTurn();  // simulate one full turn
        }
    }

    // Simulates ONE turn of the game
    public void playTurn() {

        // Step 1: Check if player is currently in jail
        if (player.isInJail()) {

            // Let the strategy decide what to do (try doubles, pay, etc.)
            strategy.handleJailTurn(player, this);

            // If still in jail after strategy → turn ends immediately
            if (player.isInJail()) {
                stats.recordLanding(player.getPosition()); // still on jail square
                totalMoves++;
                return;
            }
        }

        // Step 2: Roll two dice
        int die1 = rollDie();
        int die2 = rollDie();
        int steps = die1 + die2;

        // Step 3: Handle doubles logic
        if (die1 == die2) {
            // Increase consecutive doubles count
            player.setDoublesCount(player.getDoublesCount() + 1);
        } else {
            // Reset if not doubles
            player.setDoublesCount(0);
        }

        // If player rolls 3 doubles in a row → go to jail
        if (player.getDoublesCount() == 3) {
            player.goToJail();
            stats.recordLanding(player.getPosition());
            totalMoves++;
            return;
        }

        // Step 4: Move the player forward
        player.move(steps);

        // Step 5: Resolve the square landed on
        resolveSquare();

        // Step 6: Record where the player ended up
        stats.recordLanding(player.getPosition());

        // Step 7: Increment turn counter
        totalMoves++;
    }

    // Handles what happens when player lands on a square
    public void resolveSquare() {
        Square currentSquare = board.getSquare(player.getPosition());

        // Let the square decide what happens (card, jail, etc.)
        currentSquare.resolve(player, this);
    }

    // Simulates rolling one die (1–6)
    public int rollDie() {
        return random.nextInt(6) + 1;
    }

    // Getters (used by other classes) 

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

    public Statistics getStats() {
        return stats;
    }

    public int getTotalMoves() {
        return totalMoves;
    }

    public Random getRandom() {
        return random;
    }

    public void setStrategy(JailStrategy strategy) {
        this.strategy = strategy;
    }
}