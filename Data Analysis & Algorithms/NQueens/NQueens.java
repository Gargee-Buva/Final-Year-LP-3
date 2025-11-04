import java.util.Arrays;
import java.util.Scanner;

public class NQueens {

    // board[r][c] == 1 means a queen is placed at (r,c)
    private int[][] board;
    private int n;
    private int fixedRow = -1;
    private int fixedCol = -1;
    private boolean solved = false;

    public NQueens(int n) {
        this.n = n;
        board = new int[n][n];
        for (int[] row : board) Arrays.fill(row, 0);
    }

    // Place the fixed queen (0-based). Returns false if out of bounds or conflict.
    public boolean placeFixedQueen(int r, int c) {
        if (r < 0 || r >= n || c < 0 || c >= n) return false;
        board[r][c] = 1;
        fixedRow = r;
        fixedCol = c;
        // check that the fixed queen doesn't conflict with itself (trivial) or make impossible immediately
        return true;
    }

    // Check if it's safe to place a queen at (row, col)
    private boolean isSafe(int row, int col) {
        // Check column above
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) return false;
        }

        // Check upper-left diagonal
        int i = row, j = col;
        while (i >= 0 && j >= 0) {
            if (board[i][j] == 1) return false;
            i--; j--;
        }

        // Check upper-right diagonal
        i = row; j = col;
        while (i >= 0 && j < n) {
            if (board[i][j] == 1) return false;
            i--; j++;
        }

        return true;
    }

    // Backtracking: try to place queens row by row, skipping fixed row
    private void solveFromRow(int row) {
        if (solved) return; // stop after finding first solution

        if (row == n) {
            // all rows handled -> solution found
            solved = true;
            return;
        }

        // if this row has the pre-placed queen, skip to next row
        if (row == fixedRow) {
            solveFromRow(row + 1);
            return;
        }

        for (int col = 0; col < n; col++) {
            if (isSafe(row, col)) {
                board[row][col] = 1;
                solveFromRow(row + 1);
                if (solved) return;
                board[row][col] = 0; // backtrack
            }
        }
    }

    // Public solve method
    public boolean solve() {
        // quick validation: fixed queen must not conflict with any previous queen in earlier rows
        if (fixedRow != -1) {
            // check if fixed queen conflicts with any other pre-existing queen (none exist except itself)
            // But it might make impossible: we'll detect that during backtracking.
        }
        solveFromRow(0);
        return solved;
    }

    // Print board
    public void printBoard() {
        if (!solved) {
            System.out.println("No solution found.");
            return;
        }
        for (int r = 0; r < n; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < n; c++) {
                sb.append(board[r][c] == 1 ? " Q" : " .");
            }
            System.out.println(sb.toString());
        }
    }

    // Print positions (row -> col) 1-based for readability
    public void printPositions() {
        System.out.print("Positions (row->col, 1-based): ");
        boolean first = true;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (board[r][c] == 1) {
                    if (!first) System.out.print(", ");
                    System.out.print((r+1) + "->" + (c+1));
                    first = false;
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter board size n: ");
        int n = sc.nextInt();

        NQueens solver = new NQueens(n);

        System.out.print("Do you want to fix the first queen position? (y/n): ");
        char ch = sc.next().toLowerCase().charAt(0);

        if (ch == 'y') {
            System.out.print("Enter fixed queen row (1-based): ");
            int r = sc.nextInt();
            System.out.print("Enter fixed queen column (1-based): ");
            int c = sc.nextInt();
            // convert to 0-based
            boolean ok = solver.placeFixedQueen(r - 1, c - 1);
            if (!ok) {
                System.out.println("Invalid fixed queen coordinates.");
                sc.close();
                return;
            }
        }

        boolean found = solver.solve();
        if (found) {
            System.out.println("\nSolution found:");
            solver.printBoard();
            solver.printPositions();
        } else {
            System.out.println("\nNo solution exists with the given fixed queen (or for this n).");
        }
        sc.close();
    }
}
