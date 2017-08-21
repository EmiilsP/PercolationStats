
/**
 * To test Percolation.java
 *
 * @author Emils Pukins
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final double[] openVsClosed;
    private final int perCount;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("n and trials needs to be more than 0");
        }
        perCount = trials;
        int arraySize = n * n;
        int[] randArray = new int[arraySize];
        openVsClosed = new double[trials];
        for (int count = 0; count < arraySize; count++) {
            randArray[count] = count;
        }
        while (trials > 0) {
            Percolation percolation = new Percolation(n);
            StdRandom.shuffle(randArray);
            for (int id : randArray) {
                percolation.open((id / n) + 1, (id % n) + 1);
                if (percolation.percolates()) {
                    break;
                }
            }
            openVsClosed[trials - 1] = ((double) percolation.numberOfOpenSites() / arraySize);
            trials -= 1;
        }
    }

    public double mean() {
        return StdStats.mean(openVsClosed);

    }                        // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(openVsClosed);

    }                        // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean() - ((1.96 * stddev() / Math.sqrt(perCount)));

    }                  // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + ((1.96 * stddev() / Math.sqrt(perCount)));
    }                 // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);

        String confidence = percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi();
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + confidence + "]");
    }

}