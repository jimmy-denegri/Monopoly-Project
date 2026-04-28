package monopoly;

public class Statistics {
    private int[] landCounts;   // how many times each square was landed on
    private int totalTurns;     // total recorded landings

    public Statistics(int boardSize) {
        landCounts = new int[boardSize];
        totalTurns = 0;
    }

    // Records one landing on a square
    public void recordLanding(int index) {
        landCounts[index]++;
        totalTurns++;
    }

    // Converts landing counts into percentages
    public double[] getPercentages() {
        double[] percentages = new double[landCounts.length];

        if (totalTurns == 0) {
            return percentages;
        }

        for (int i = 0; i < landCounts.length; i++) {
            percentages[i] = (landCounts[i] * 100.0) / totalTurns;
        }

        return percentages;
    }

    public int[] getLandCounts() {
        return landCounts;
    }

    public int getTotalTurns() {
        return totalTurns;
    }

    // Prints all square statistics
    public void printStatistics(Board board) {
        double[] percentages = getPercentages();

        for (int i = 0; i < landCounts.length; i++) {
            Square square = board.getSquare(i);

            System.out.printf(
                "%2d %-25s Count: %-8d Percentage: %.2f%%\n",
                i,
                square.getName(),
                landCounts[i],
                percentages[i]
            );
        }
    }
}