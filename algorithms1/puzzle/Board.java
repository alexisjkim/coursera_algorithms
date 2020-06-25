package algorithms1.puzzle;

import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[][] board;
    private int n; //n = dimension of board

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != (i * n) + j + 1 && board[i][j] != 0) {
                    distance++;
                }
            }
        }
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != (i * n) + j + 1 && board[i][j] != 0) {
                    int x = board[i][j];
                    int wanted_i = ((x-1) / n);
                    int wanted_j = (x % n) - 1;
                    if (wanted_j == -1) wanted_j = n - 1;
                    distance = distance + Math.abs(i - wanted_i);
                    distance = distance + Math.abs(j - wanted_j);
                }
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != i * n + j + 1 && board[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (y == null) return false;

        return this.toString().equals(y.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        Stack<Board> stack1 = new Stack<Board>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    if (i > 0) {
                        int[][] top = board_clone(board);
                        top[i][j] = top[i-1][j];
                        top[i-1][j] = 0;
                        Board neighbor = new Board(top);
                        stack1.push(neighbor);

                    }
                    if (i < n-1) {
                        int[][] bottom = board_clone(board);
                        bottom[i][j] = bottom[i+1][j];
                        bottom[i+1][j] = 0;
                        Board neighbor = new Board(bottom);
                        stack1.push(neighbor);

                    }
                    if (j > 0) {
                        int[][] left = board_clone(board);
                        left[i][j] = left[i][j-1];
                        left[i][j-1] = 0;
                        Board neighbor = new Board(left);
                        stack1.push(neighbor);

                    }
                    if (j < n-1) {
                        int[][] right = board_clone(board);
                        right[i][j] = right[i][j+1];
                        right[i][j+1] = 0;
                        Board neighbor = new Board(right);
                        stack1.push(neighbor);
                    }
                }
            }

        }

        return stack1;
    }

    private int[][] board_clone(int[][] current) {
        int[][] new_board = new int[n][n];
        for (int i = 0; i < n; i++) {
            new_board[i] = current[i].clone();
        }
        return new_board;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin_board = new Board(board_clone(board));

        if (twin_board.board[0][0] == 0) {
            int swap = twin_board.board[1][0];
            twin_board.board[1][0] = twin_board.board[1][1];
            twin_board.board[1][1] = swap;
        } else {
            if (twin_board.board[0][1] == 0) {
                int swap = twin_board.board[1][0];
                twin_board.board[1][0] = twin_board.board[0][0];
                twin_board.board[0][0] = swap;
            }
            else {
                int swap = twin_board.board[0][1];
                twin_board.board[0][1] = twin_board.board[0][0];
                twin_board.board[0][0] = swap;
            }
        }
        return twin_board;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] lst = new int[3][3];
        lst[0][0] = 5;
        lst[0][1] = 8;
        lst[0][2] = 7;
        lst[1][0] = 0;
        lst[1][1] = 4;
        lst[1][2] = 6;
        lst[2][0] = 3;
        lst[2][1] = 1;
        lst[2][2] = 2;
        Board board = new Board(lst);
        System.out.println(board.twin());


    }

}