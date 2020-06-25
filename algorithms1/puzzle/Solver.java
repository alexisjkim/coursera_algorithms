package algorithms1.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class Solver {
    private SearchNode answer = null;


    private class SearchNode {
        private Board board;
        private SearchNode previous;
        private int moves;
        private int manhattan;

        public SearchNode (Board board1, SearchNode previous1, int manhattan1) {
            board = board1;
            previous = previous1;
            manhattan = manhattan1;
            if (previous != null) moves = previous1.moves + 1;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        SearchNode initial_searchnode = new SearchNode(initial, null, initial.manhattan());
        Board twin_board = initial.twin();
        SearchNode initial_searchnode_twin = new SearchNode(twin_board, null, twin_board.manhattan());

        MinPQ<SearchNode> queue = new MinPQ<SearchNode>(new comparator1());
        MinPQ<SearchNode> queue_twin = new MinPQ<SearchNode>(new comparator1());

        initial_searchnode.moves = 0;
        initial_searchnode_twin.moves = 0;
        queue.insert(initial_searchnode);
        queue_twin.insert(initial_searchnode_twin);


        while (!queue.isEmpty()) {

            if (!queue_twin.isEmpty()) {
                SearchNode min_twin = queue_twin.delMin();
                if (min_twin.board.isGoal()) {
                    break;
                }
                for (Board i : min_twin.board.neighbors()) {
                    if (min_twin.previous == null) {
                        queue_twin.insert(new SearchNode(i, min_twin, i.manhattan()));
                    }
                    else {
                        if (!i.equals(min_twin.previous.board))
                            queue_twin.insert(new SearchNode(i, min_twin, i.manhattan()));
                    }
                }

            }

            SearchNode min = queue.delMin();

            if (min.board.isGoal()) {
                answer = min;
                break;
            }


            for (Board i : min.board.neighbors()) {
                if (min.previous == null) {
                    queue.insert(new SearchNode(i, min, i.manhattan()));
                }
                else {
                    if (!i.equals(min.previous.board))
                        queue.insert(new SearchNode(i, min, i.manhattan()));
                }
            }
        }
    }

    private class comparator1 implements Comparator<SearchNode>  {

        public int compare(SearchNode o1, SearchNode o2) {
            if (o1.manhattan + o1.moves > o2.manhattan + o2.moves) return 1;
            else return -1;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return (answer != null);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return answer.moves;
        }
        else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (answer == null) return null;
        SearchNode current = answer;
        Stack<Board> boardStack = new Stack<Board>();
        while (current.previous != null) {
            boardStack.push(current.board);
            current = current.previous;
        }
        boardStack.push(current.board);
        return boardStack;
    }


    // test client (see below) 
    public static void main(String[] args) {
        int[][] lst = new int[2][2];
        lst[0][0] = 0;
        lst[0][1] = 3;
        lst[1][0] = 2;
        lst[1][1] = 1;

        Board board = new Board(lst);
        Solver a = new Solver(board);


        System.out.println(a.solution());


    }

}