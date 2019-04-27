/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 01/13/2019
 *  Description: Solver to 8-Puzzle.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private int moves = 0;
    private boolean mainSolvable = false;
    private boolean twinSolvable = false;
    private Stack<Board> solutions = new Stack<Board>();


    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode predecessor;
        private int hammingPriority;
        private int manhattanPriority;

        public SearchNode(Board board, int moves, SearchNode predecessor) {
            this.board = board;
            this.moves = moves;
            this.predecessor = predecessor;
            this.hammingPriority = this.moves + this.board.hamming();
            this.manhattanPriority = this.moves + this.board.manhattan();


        }

        public int hammingPriority() {
            return this.hammingPriority;
        }

        public int manhattanPriority() {
            return this.manhattanPriority;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.manhattanPriority() - that.manhattanPriority();
        }
    }

    /*
    Code to solve the puzzle.
     */
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> mainPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>();
        SearchNode mainCurrentNode = new SearchNode(initial, 0, null);
        SearchNode twinCurrentNode = new SearchNode(initial.twin(), 0, null);


        mainPQ.insert(mainCurrentNode);
        twinPQ.insert(twinCurrentNode);

        /*
        Keep searching until either mainPQ or twinPQ is empty or goal is reached.
         */
        while (!mainSolvable && !twinSolvable) {
            // main puzzle
            mainCurrentNode = mainPQ.delMin();

            if (mainCurrentNode.board.isGoal()) {
                this.mainSolvable = true;
                this.moves = mainCurrentNode.moves;
            }
            else {

                for (Board neighborBoard : mainCurrentNode.board.neighbors()) {

                    if (mainCurrentNode.predecessor == null) {

                        mainPQ.insert(new SearchNode(neighborBoard, mainCurrentNode.moves + 1,
                                                     mainCurrentNode));
                    }
                    else if (!neighborBoard.equals(mainCurrentNode.predecessor.board)) {
                        mainPQ.insert(new SearchNode(neighborBoard, mainCurrentNode.moves + 1,
                                                     mainCurrentNode));
                    }
                }

            }

            // twin puzzle
            twinCurrentNode = twinPQ.delMin();
            if (twinCurrentNode.board.isGoal()) {
                this.twinSolvable = true;
                this.moves = twinCurrentNode.moves;
            }
            else {
                for (Board twinNeighborBoard : twinCurrentNode.board.neighbors()) {
                    if (twinCurrentNode.predecessor == null) {
                        twinPQ.insert(
                                new SearchNode(twinNeighborBoard, twinCurrentNode.moves + 1,
                                               twinCurrentNode));
                    }

                    else if (!twinNeighborBoard.equals(twinCurrentNode.predecessor.board)) {
                        twinPQ.insert(
                                new SearchNode(twinNeighborBoard, twinCurrentNode.moves + 1,
                                               twinCurrentNode));
                    }
                }
            }

        }

        if (this.mainSolvable) {
            while (mainCurrentNode.predecessor != null) {
                solutions.push(mainCurrentNode.board);
                mainCurrentNode = mainCurrentNode.predecessor;
            }
            solutions.push(mainCurrentNode.board);
        }
    }

    /*
    Return if the puzzle is solvable.
     */
    public boolean isSolvable() {
        return mainSolvable;
    }

    /*
    Return the number of steps to solve the puzzle if it is solvable.
     */
    public int moves() {
        if (isSolvable()) {
            return moves;
        }
        else return -1;

    }

    /*
    Return the states from initial to goal if it is solvable.
     */
    public Iterable<Board> solution() {
        if (isSolvable()) return solutions;
        else return null;

    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
