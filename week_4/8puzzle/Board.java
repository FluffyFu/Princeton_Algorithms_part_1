/* *****************************************************************************
 *  Name: Xinlin Song
 *  Date: 1/8/2019
 *  Description: Board object used to store the borad cofig and other info
 *      such as board dimension, distance to the goal etc.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private final int[][] currentBlocks;
    private final int dim;
    private int zeroRowIndex;
    private int zeroColIndex;

    public Board(int[][] blocks) {
        dim = blocks.length;
        currentBlocks = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                currentBlocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    zeroRowIndex = i;
                    zeroColIndex = j;
                }
            }
        }
    }


    public int dimension() {
        return dim;
    }

    public int hamming() {
        int counter = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if ((currentBlocks[i][j] != 0) && (currentBlocks[i][j] != (i * dim + j + 1)))
                    counter++;
            }
        }
        return counter;
    }

    public int manhattan() {
        int counter = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int element = currentBlocks[i][j];
                if (element == 0) continue;
                int correctRowIndex = (element - 1) / dim;
                int correctColumnIndex = (element - 1) % dim;
                counter = counter + Math.abs(i - correctRowIndex) +
                        Math.abs(j - correctColumnIndex);
            }
        }
        return counter;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        int[][] copyBlocks = copyBlocks(currentBlocks);
        int firstRowIndex;
        int firstColIndex;
        int secondRowIndex;
        int secondColIndex;
        if ((zeroRowIndex == 0 && zeroColIndex == 0) || (zeroRowIndex == 0 && zeroColIndex == 1)) {
            firstRowIndex = 2 / dim;
            firstColIndex = 2 % dim;
            secondRowIndex = 3 / dim;
            secondColIndex = 3 % dim;
        }
        else {
            firstRowIndex = 0;
            firstColIndex = 0;
            secondRowIndex = 0;
            secondColIndex = 1;
        }
        int temp = copyBlocks[firstRowIndex][firstColIndex];
        copyBlocks[firstRowIndex][firstColIndex] = copyBlocks[secondRowIndex][secondColIndex];
        copyBlocks[secondRowIndex][secondColIndex] = temp;

        return new Board(copyBlocks);
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (!Arrays.deepEquals(this.currentBlocks, that.currentBlocks)) return false;
        return true;

    }

    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();

        int leftRow = zeroRowIndex;
        int leftCol = zeroColIndex - 1;
        int rightRow = zeroRowIndex;
        int rightCol = zeroColIndex + 1;
        int upRow = zeroRowIndex - 1;
        int upCol = zeroColIndex;
        int downRow = zeroRowIndex + 1;
        int downCol = zeroColIndex;

        int[][] blocks;
        if (leftCol >= 0) {
            blocks = copyBlocks(currentBlocks);
            swapBlankWith(blocks, leftRow, leftCol);
            // StdOut.println("currentBlocks");
            // printArray(currentBlocks);
            stack.push(new Board(blocks));
        }

        if (rightCol < dim) {
            blocks = copyBlocks(currentBlocks);
            // StdOut.println("right");
            swapBlankWith(blocks, rightRow, rightCol);
            stack.push(new Board(blocks));
        }

        if (upRow >= 0) {
            blocks = copyBlocks(currentBlocks);
            // StdOut.println("up");
            // StdOut.println("currentBlocks");
            // printArray(currentBlocks);
            // printArray(blocks);
            swapBlankWith(blocks, upRow, upCol);
            stack.push(new Board(blocks));
        }

        if (downRow < dim) {
            blocks = copyBlocks(currentBlocks);
            // StdOut.println("down");
            // printArray(blocks);
            swapBlankWith(blocks, downRow, downCol);
            stack.push(new Board(blocks));
        }

        return stack;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(String.format("%2d ", currentBlocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // private Board createTwin() {
    //     int firstNum = StdRandom.uniform(dim * dim);
    //     int[] firstIndex = generateIndexFromInt(firstNum);
    //     while (currentBlocks[firstIndex[0]][firstIndex[1]] == 0) {
    //         firstNum = StdRandom.uniform(dim * dim);
    //         firstIndex = generateIndexFromInt(firstNum);
    //     }
    //     int secondNum = StdRandom.uniform(dim * dim);
    //     int[] secondIndex = generateIndexFromInt(secondNum);
    //     while ((secondNum == firstNum) ||
    //             (currentBlocks[secondIndex[0]][secondIndex[1]] == 0)) {
    //         secondNum = StdRandom.uniform(dim * dim);
    //         secondIndex = generateIndexFromInt(secondNum);
    //     }
    //
    //     int[][] copyBlocks = copyBlocks(currentBlocks);
    //     int temp = copyBlocks[firstIndex[0]][firstIndex[1]];
    //     copyBlocks[firstIndex[0]][firstIndex[1]] = copyBlocks[secondIndex[0]][secondIndex[1]];
    //     copyBlocks[secondIndex[0]][secondIndex[1]] = temp;
    //
    //     return new Board(copyBlocks);
    // }

    private int[][] copyBlocks(int[][] blocks) {
        int[][] copyBlocks = new int[dim][dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                copyBlocks[i][j] = blocks[i][j];
            }
        }
        return copyBlocks;
    }

    private int[] generateIndexFromInt(int n) {
        int[] index = new int[2];
        index[0] = n / dim; // row index;
        index[1] = n % dim; // column index;
        return index;
    }

    /*
    swap blank with the input block element.
     */
    private void swapBlankWith(int[][] blocks, int rowIndex, int colIndex) {
        int temp = blocks[rowIndex][colIndex];
        blocks[zeroRowIndex][zeroColIndex] = temp;
        blocks[rowIndex][colIndex] = 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board board = new Board(blocks);

        StdOut.print(board);

        StdOut.println("Current Node Before iterating neighbors");
        StdOut.println(board);

        for (Board neighbor : board.neighbors()) {
            StdOut.print(neighbor);
        }
        StdOut.println("Current Node After iterating neighbors");
        StdOut.println(board);

        // StdOut.printf("hamming distance %d\n", board.hamming());
        // StdOut.printf("Manhattan distance %d\n", board.manhattan());

        // StdOut.println("Twin");
        // for (int i = 0; i < 10; i++) {
        //     StdOut.println(i);
        //     StdOut.println(board.twin());
        // }

        // int[][] anotherBlocks = { { 1, 2, 3 }, { 4, 5, 6 }, { 8, 7, 0 } };
        // Board anotherBoard = new Board(anotherBlocks);
        // StdOut.println(board.equals(anotherBoard));
        // StdOut.printf("anotherBoard Hamming %d\n", anotherBoard.hamming());
        // StdOut.printf("anotherBoard Manhattan %d\n", anotherBoard.manhattan());
    }
}
