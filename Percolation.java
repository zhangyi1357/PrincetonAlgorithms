/* *****************************************************************************
 *  Name:    Zhang Yi
 *  NetID:   Me
 *  Precept: P00
 *
 *  Description:  Do simulation for percolation problem using weighted quick
 *                union-find data structure
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;                   // grid size
    private int nOpen;                     // sites opened
    private final WeightedQuickUnionUF uf; // union-find
    private byte[] siteStatus;             // site status

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        nOpen = 0;
        // set all sites unopen
        int nSquare = n * n;
        siteStatus = new byte[nSquare + 1];
        siteStatus[0] = 1;
        for (int i = 1; i < nSquare + 1; ++i)
            siteStatus[i] = 0;
        uf = new WeightedQuickUnionUF(nSquare + 1);
        // connect virtual site to the top row
        for (int i = 1; i <= n; ++i) {
            uf.union(0, i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) // is open already
            return;
        int index = (row - 1) * n + col;
        if (row == n)
            siteStatus[index] = 2;
        else
            siteStatus[index] = 1;
        ++nOpen;
        if (row - 1 >= 1 && isOpen(row - 1, col)) {
            int p = uf.find(index - n);
            int q = uf.find(index);
            uf.union(index - n, index);
            if (siteStatus[p] == 2 || siteStatus[q] == 2) {
                int root = uf.find(index);
                siteStatus[root] = 2;
            }
        }
        if (row + 1 <= n && isOpen(row + 1, col)) {
            int p = uf.find(index + n);
            int q = uf.find(index);
            uf.union(index + n, index);
            if (siteStatus[p] == 2 || siteStatus[q] == 2) {
                int root = uf.find(index);
                siteStatus[root] = 2;
            }
        }
        if (col - 1 >= 1 && isOpen(row, col - 1)) {
            int p = uf.find(index - 1);
            int q = uf.find(index);
            uf.union(index, index - 1);
            if (siteStatus[p] == 2 || siteStatus[q] == 2) {
                int root = uf.find(index);
                siteStatus[root] = 2;
            }
        }
        if (col + 1 <= n && isOpen(row, col + 1)) {
            int p = uf.find(index + 1);
            int q = uf.find(index);
            uf.union(index, index + 1);
            if (siteStatus[p] == 2 || siteStatus[q] == 2) {
                int root = uf.find(index);
                siteStatus[root] = 2;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return siteStatus[(row - 1) * n + col] > 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col) && uf.find(0) == uf.find((row - 1) * n + col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1)
            return isOpen(1, 1);
        int root = uf.find(0);
        return siteStatus[root] == 2;
    }

    // check for the validation of row and col
    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();
    }

    // test client (optional)
    public static void main(String[] args) {
        In in = new In(args[0]); // input file
        int n = in.readInt(); // N-by-N percolation system
        Percolation perc = new Percolation(n);
        boolean percolates = false;
        int count = 0;
        while (!in.isEmpty()) {
            int i = in.readInt() + 1;
            int j = in.readInt() + 1;
            if (!perc.isOpen(i, j))
                ++count;
            perc.open(i, j);
            percolates = perc.percolates();
            if (percolates)
                break;
        }
        StdOut.println(count + " open sites");
        if (percolates)
            StdOut.println("percolates");
        else
            StdOut.println("does not percolate");
    }
}
