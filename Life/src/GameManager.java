import com.sun.xml.internal.bind.v2.TODO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * author Rastislav Papčo
 */
public class GameManager {
    private final int SIZE = 10;
    private ArrayList<ArrayList<CellType>> board;

    public GameManager() {
        board = Generator.generateBoard(SIZE);
    }

    private int countLivingNeighbours(int i, int j) {
        int alive = 0;

        for (int x = i - 1; x < i + 2; x++) {
            for (int y = j - 1; y < j + 2; y++) {
                if (x != i && y != j && 0 <= x && x < SIZE && 0 <= y && y < SIZE) {
                    if (board.get(x).get(y) == CellType.ALIVE) {
                        alive++;
                    }
                }
            }
        }

        return alive;
    }

    private CellType applyRulesToCell(CellType cell, int alive) {

        if ((cell == CellType.ALIVE && (alive == 2 || alive == 3)) ||
                (cell == CellType.DEAD && alive == 3)) return CellType.ALIVE;

        return CellType.DEAD;
    }

    private void nextGeneration() {
        ArrayList<ArrayList<CellType>> newBoard = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            ArrayList<CellType> newRow = new ArrayList<>();

            for (int j = 0; j < SIZE; j++) {
                CellType cell = board.get(i).get(j);
                CellType newCell = CellType.NONE;

                if (cell != CellType.NONE) {
                    int alive = countLivingNeighbours(i, j);
                    newCell = applyRulesToCell(cell, alive);
                }

                newRow.add(newCell);
            }

            newBoard.add(newRow);
        }

        board = newBoard;
    }

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

    public void play() {
        boolean next = true;
        String input;
        Scanner reader = new Scanner(System.in);

        while (next) {
            printBoard();
            nextGeneration();
            System.out.println();

            System.out.println("User input reading:");
            input = reader.nextLine();
            System.out.println("User input read:");
            if (input.isEmpty()) next = false;
        }

        reader.close();
    }
}
