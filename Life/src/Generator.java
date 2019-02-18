import java.util.ArrayList;
import java.util.Random;

/**
 * author Rastislav Papčo
 */
public class Generator {

    public static ArrayList<ArrayList<CellType>> generateBoard(int size) {
        ArrayList<ArrayList<CellType>> board = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            ArrayList<CellType> row = new ArrayList<>();

            for (int j = 0; j < size; j++) {
                row.add(CellType.values()[rand.nextInt(3)]);
            }

            board.add(row);
        }

        return board;
    }

}
