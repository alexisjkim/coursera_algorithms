package algorithms1.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private WeightedQuickUnionUF id;
    private int n1;
    private boolean[] opened;
    private int counter;
    public Percolation(int n){
        if (n < 0 || n == 0) {
            throw new IllegalArgumentException("n too small");
        }
        id = new WeightedQuickUnionUF((n * n) + 2);
        opened = new boolean[n * n];

        for (int i = 0; i < (n * n); i++) { opened[i] = false; }

        for (int i = 0; i < n; i++) {
            id.union((n*n), i);
            id.union((n*n) + 1, (n*n)-i-1);
        }
        n1 = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException("too small");
        }
        if (row > n1 || col > n1) {
            throw new IllegalArgumentException("too big");
        }
        opened[xyToID(row, col)] = true;

        if (validateRowCol(row-1, col)){
            if (isOpen((row-1), col)) { id.union(xyToID(row, col), ((row-2) * n1) + col - 1); }
        } if (validateRowCol(row+1, col)) {
            if (isOpen((row+1), col)) { id.union(xyToID(row, col), (row * n1) + col - 1); }
        } if (validateRowCol(row, col-1)) {
            if (isOpen((row), col - 1)) {
                id.union(xyToID(row, col) - 1, xyToID(row, col));
            }
        } if (validateRowCol(row, col+1)) {
            if (isOpen((row), col + 1)) {
                id.union(xyToID(row, col) + 1, xyToID(row, col));
            }
        }
    }
    private boolean validateRowCol (int row, int col) {
        if (row < 1 || row > n1) { return false;}
        if (col < 1 || col > n1) { return false; }
        return true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException("too small");
        }
        if (row > n1 || col > n1) {
            throw new IllegalArgumentException("too big");
        }
        return opened[xyToID(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException("too small");
        }
        if (row > n1 || col > n1) {
            throw new IllegalArgumentException("too big");
        }
        if (!isOpen(row, col)) { return false; }

        return id.connected(xyToID(row,col), n1*n1);

    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        counter = 0;
        for (int i = 0; i < (n1 * n1); i ++) {
            if (opened[i]) { counter ++; }
        }
        return counter;
    }

    // does the system percolate?
    public boolean percolates(){
        if (n1 == 1) {return opened[0];}
        return id.connected(n1*n1, (n1*n1) +1);
    }

    private int xyToID(int row, int col) {
        return ((row-1) * n1) + col - 1;
    }

    // test client (optional)
    public static void main(String[] args){
        Percolation a = new Percolation(3);
        a.open(1,1);
        a.open(2,1);
        a.open(3,1);
        a.open(3,3);
        System.out.println(a.isFull(3,3));

    }
}
