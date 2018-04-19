import java.awt.*;
import java.util.Scanner;

public class HumanPlayer implements Player {

    Scanner scanner = new Scanner(System.in);

    int playerId; // should be 1 for player 1 or 2 for player 2

    HumanPlayer(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public Board makeNextMove(Board board) {
        int move = scanner.nextInt();
        boolean success = board.setSquareOnBoard(new Point(move/10, move % 10), playerId);
        while (!success) {
            System.err.println("Illegal move, try again");
            move = scanner.nextInt();
            success = board.setSquareOnBoard(new Point(move/10, move % 10), playerId);
        }
        return board;
    }
}