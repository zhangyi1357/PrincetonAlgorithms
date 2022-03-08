/* *****************************************************************************
 *  Name:    Zhang Yi
 *  NetID:   zhangyi
 *  Precept: P00
 *
 *  Description:  Do many trials for percolation simulation and get the
 *                threshold of percolation.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] percolateRate; // rate of percolation
    private final int trials;             // times of trials

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        this.trials = trials;
        int nSquare = n * n;
        percolateRate = new double[trials];
        // perform the trials and recorde numOfSites when percolates
        for (int i = 0; i < trials; ++i) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(row, col))
                    percolation.open(row, col);
            }
            percolateRate[i] = (double) percolation.numberOfOpenSites() / nSquare;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolateRate);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolateRate);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %.6f\n",
                      percolationStats.mean());
        StdOut.printf("stddev                  = %.17f\n",
                      percolationStats.stddev());
        StdOut.printf("95%% confidence interval = [%.17f, %.17f]\n",
                      percolationStats.confidenceLo(),
                      percolationStats.confidenceHi());
    }

}
