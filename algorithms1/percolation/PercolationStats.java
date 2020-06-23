package algorithms1.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private Percolation a;
    private int ans;
    private int row;
    private int col;
    private double[] percolate_times;
    private double counter;
    private int n1;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("too small");
        }
        percolate_times = new double[trials];
        n1 = n;

        for (int i = 0; i < trials; i++) {
            a = new Percolation(n);
            counter = 0.0;

            while (!a.percolates()) {
                row = StdRandom.uniform(1, n+1);
                col = StdRandom.uniform(1, n+1);

                if (!a.isOpen(row, col)){ counter += 1; }

                a.open(row, col);
            }

            percolate_times[i] = (double) a.numberOfOpenSites() / (n * n);

        }
    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(percolate_times);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(percolate_times);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - (1.96 * (mean() / n1));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + (1.96 * (mean() / n1));
    }

    // test client (see below)
    public static void main(String[] args){
        PercolationStats test1 = new PercolationStats(10, 10);
        System.out.println(test1.mean());
    }
}
