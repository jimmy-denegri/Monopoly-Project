package monopoly;

public class Statistics {
    private int[] landCounts;
    private int totalTurns;

    public Statistics(int boardSize) {
        this.landCounts = new int[boardSize];
        this.totalTurns = 0;
    }

    public void recordLanding(int index) {
        // TODO: increase landing count for square
    }

    public double[] getPercentages() {
        // TODO: calculate landing percentages
        return new double[landCounts.length];
    }

    public int[] getLandCounts() {
        return landCounts;
    }

    public int getTotalTurns() {
        return totalTurns;
    }
}