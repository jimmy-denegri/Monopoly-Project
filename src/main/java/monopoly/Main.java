package monopoly;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {

        int[] turnCounts = {1000, 10000, 100000, 1000000};

        JailStrategyType[] strategies = {
            JailStrategyType.IMMEDIATE_EXIT,
            JailStrategyType.TRY_FOR_DOUBLES
        };

        try {
            PrintWriter output = new PrintWriter(new FileWriter("monopoly_results.txt"));

            for (JailStrategyType strategyType : strategies) {

                
                output.println("Strategy: " + strategyType);
                

                for (int sim = 1; sim <= 10; sim++) {

                    output.println("\nSimulation #" + sim);

                    for (int turns : turnCounts) {

                        MonopolyGame game = new MonopolyGame();
                        game.setStrategy(new JailStrategy(strategyType));

                        initializeChanceDeck(game);
                        initializeChestDeck(game);

                        game.runSimulation(turns);

                        output.println("\nTurns: " + turns);
                        printStatisticsToFile(game, output);
                    }
                }
            }

            output.close();
            System.out.println("Results saved to monopoly_results.txt");

        } catch (IOException e) {
            System.out.println("Error writing file.");
            e.printStackTrace();
        }
    }

    private static void printStatisticsToFile(MonopolyGame game, PrintWriter output) {
        int[] counts = game.getStats().getLandCounts();
        double[] percentages = game.getStats().getPercentages();
        Board board = game.getBoard();

        for (int i = 0; i < counts.length; i++) {
            output.printf(
                "%2d %-25s Count: %-8d Percentage: %.2f%%\n",
                i,
                board.getSquare(i).getName(),
                counts[i],
                percentages[i]
            );
        }
    }

    private static void initializeChanceDeck(MonopolyGame game) {
        Deck chance = game.getChanceDeck();

        chance.addCard(new Card("Advance to GO", CardType.MOVE_TO, 0));
        chance.addCard(new Card("Go to Jail", CardType.GO_TO_JAIL, 10));
        chance.addCard(new Card("Go back 3 spaces", CardType.MOVE_RELATIVE, -3));
        chance.addCard(new Card("Go to nearest Railroad", CardType.MOVE_TO_NEAREST_RAILROAD, 0));
        chance.addCard(new Card("Go to nearest Railroad", CardType.MOVE_TO_NEAREST_RAILROAD, 0));
        chance.addCard(new Card("Go to nearest Utility", CardType.MOVE_TO_NEAREST_UTILITY, 0));
        chance.addCard(new Card("Advance to Illinois Avenue", CardType.MOVE_TO, 24));
        chance.addCard(new Card("Advance to St. Charles Place", CardType.MOVE_TO, 11));
        chance.addCard(new Card("Advance to Boardwalk", CardType.MOVE_TO, 39));
        chance.addCard(new Card("Get Out of Jail Free", CardType.GET_OUT_OF_JAIL_FREE, 0));

        for (int i = 0; i < 6; i++) {
            chance.addCard(new Card("No movement", CardType.NO_MOVEMENT, 0));
        }
    }

    private static void initializeChestDeck(MonopolyGame game) {
        Deck chest = game.getCcDeck();

        chest.addCard(new Card("Advance to GO", CardType.MOVE_TO, 0));
        chest.addCard(new Card("Go to Jail", CardType.GO_TO_JAIL, 10));
        chest.addCard(new Card("Get Out of Jail Free", CardType.GET_OUT_OF_JAIL_FREE, 0));

        for (int i = 0; i < 13; i++) {
            chest.addCard(new Card("No movement", CardType.NO_MOVEMENT, 0));
        }
    }
}