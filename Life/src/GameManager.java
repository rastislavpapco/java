import java.util.ArrayList;
import java.util.Scanner;

/**
 * Game manager that takes care of the whole running of the game.
 * SIZE is the length of the edges of a square board (SIZE x SIZE).
 * board represents the game board containing all the cells (and empty space - NONE).
 *
 * author Rastislav Papčo
 */
public class GameManager {
    private final int SIZE = 10;
    private ArrayList<ArrayList<CellType>> board = Generator.generateBoard(SIZE);

    /**
     * @param i Row of the cell.
     * @param j Column of the cell.
     * @return Number of living neighbour cells of the cell.
     */
    private int countLivingNeighbours(int i, int j) {
        int alive = 0;

        for (int x = i - 1; x < i + 2; x++) {
            for (int y = j - 1; y < j + 2; y++) {
                if ((x != i || y != j) && 0 <= x && x < SIZE && 0 <= y && y < SIZE) {
                    if (board.get(x).get(y) == CellType.ALIVE) {
                        alive++;
                    }
                }
            }
        }

        return alive;
    }


    /**
     * @param cell Cell that the rules will be applied to.
     * @param alive Number of living neighbour cells.
     * @return New cell based on the rules.
     */
    private CellType applyRulesToCell(CellType cell, int alive) {

        if ((cell == CellType.ALIVE && (alive == 2 || alive == 3)) ||
                (cell == CellType.DEAD && alive == 3)) return CellType.ALIVE;

        return CellType.DEAD;
    }

    /**
     * Generate next state of the board from the current one, based on rules.
     */
    private void nextGeneration() {
        ArrayList<ArrayList<CellType>> newBoard = new ArrayList<>();
        CellType cell;
        CellType newCell;
        int alive;

        for (int i = 0; i < SIZE; i++) {
            ArrayList<CellType> newRow = new ArrayList<>();

            for (int j = 0; j < SIZE; j++) {
                cell = board.get(i).get(j);
                newCell = CellType.NONE;

                if (cell != CellType.NONE) {
                    alive = countLivingNeighbours(i, j);
                    newCell = applyRulesToCell(cell, alive);
                }

                newRow.add(newCell);
            }

            newBoard.add(newRow);
        }

        board = newBoard;
    }

    /**
     * Print the current state of board.
     */
    private void printBoard() {
        char symbol;

        for (ArrayList<CellType> row : board) {
            for (CellType cell : row) {
                if (cell == CellType.ALIVE) symbol = 'A';
                else if (cell == CellType.DEAD) symbol = 'D';
                else symbol = '_';
                System.out.print(symbol);
            }
            System.out.println();
        }
    }

    /**
     * Main loop that simulates the game.
     */
    public void play() {
        boolean next = true;
        String input;
        Scanner reader = new Scanner(System.in);

        while (next) {
            printBoard();
            nextGeneration();
            System.out.println();

            System.out.println("Press enter to continue; type anything to quit.");
            input = reader.nextLine();
            if (!input.isEmpty()) next = false;
        }

        reader.close();
    }
}
