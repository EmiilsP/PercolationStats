/**
 * A program to estimate the value of the percolation
 * threshold via Monte Carlo simulation.
 *
 * @author Emils Pukins
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] cell;
    private final int maxRow; // the max size for row or col
    private final WeightedQuickUnionUF weighted;
    private final WeightedQuickUnionUF backwash;
    private int openSites;
    private final int top;
    private int bot;

    // create n-by-n grid, with all sites blocked.
    public Percolation(int n) {
        if (n < 1) {
            throw new java.lang.IllegalArgumentException("n needs to be more than 1");
        }
        openSites = 0;
        top=0;
        maxRow = n;
        bot = n * n + 1;
        cell = new boolean[n][n];
        weighted = new WeightedQuickUnionUF(n * n + 2);
        backwash = new WeightedQuickUnionUF(n * n + 2);
    }

    // open site (row, col) if it is not open already. Opened value is 1.
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > maxRow || col > maxRow) {
            throw new java.lang.IllegalArgumentException("Illeagal argument!");
        }
        if (isOpen(row, col)) {
            return; // check if it already opened
        }
        cell[row - 1][col - 1] = true;
        openSites++;
        int current = (maxRow * (row - 1) + col);
        int sideLeft = (maxRow * (row - 1) + col - 1);
        int sideRight = (maxRow * (row - 1) + col + 1);
        int sideUp = (maxRow * (row - 2) + col);
        int sideDown = (maxRow * (row) + col);

        // create virtual top and bottom
        if (row == 1) {
            weighted.union(current, top);
            backwash.union(current, top);


        }
        if (row == maxRow) {
            backwash.union(current, bot);


        }


        if (col > 1 && isOpen(row, (col - 1))) {   // check to the left
            weighted.union(current, sideLeft);
            backwash.union(current, sideLeft);
        }
        if (col < maxRow && isOpen(row, (col + 1))) {   // check to the right
            weighted.union(current, sideRight);
            backwash.union(current, sideRight);
        }
        if (row > 1 && isOpen((row - 1), col)) {   // check up
            weighted.union(current, sideUp);
            backwash.union(current, sideUp);
        }
        if (row < maxRow && isOpen((row + 1), col)) {   // check down
            weighted.union(current, sideDown);
            backwash.union(current, sideDown);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > maxRow || col > maxRow) {
            throw new java.lang.IllegalArgumentException("Illeagal argument!");
        }
        return cell[row - 1][col - 1];
    }

    // is site (row, col) full? (Connected
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        } else {
            return weighted.connected(top, (maxRow * (row - 1) + col));
        }
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return backwash.connected(top, bot);
    }

}

