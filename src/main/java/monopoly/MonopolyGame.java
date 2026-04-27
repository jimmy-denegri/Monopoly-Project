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
        boolean turnOver = false;
        int doublesThisTurn = 0;

        // If player starts turn in jail, jail strategy decides what happens
        if (player.isInJail()) {
            turnOver = strategy.handleJailTurn(player, this);

            // If jail strategy ended the turn, record landing and stop
            if (turnOver) {
                stats.recordLanding(player.getPosition());
                totalMoves++;
                return;
            }
        }

        // Keep rolling while player rolls doubles
        while (!turnOver) {
            int die1 = rollDie();
            int die2 = rollDie();
            int steps = die1 + die2;

            boolean rolledDoubles = die1 == die2;

            if (rolledDoubles) {
                doublesThisTurn++;
            } else {
                doublesThisTurn = 0;
                turnOver = true;
            }

            // Third consecutive doubles sends player to jail immediately
            if (doublesThisTurn == 3) {
                player.goToJail();
                turnOver = true;
                break;
            }

            // Move player
            player.move(steps);

            // Resolve square effect
            resolveSquare();

            // If square/card sent player to jail, turn ends
            if (player.isInJail()) {
                turnOver = true;
                break;
            }

            // If player rolled doubles, they get another roll
            if (!rolledDoubles) {
                turnOver = true;
            }
        }

        // Record only the final square after all movement effects
        stats.recordLanding(player.getPosition());
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