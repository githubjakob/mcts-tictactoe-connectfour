import connectfour.ConnectFourBoard;
import tictactoe.MctsPlayer;
import org.junit.Test;
import tictactoe.TicTacToeBoard;

/**
 * Created by jakob on 23.04.18.
 */
public class MctsSelfPlay {

    @Test
    public void selfPlay_ticTacToe() {

        int playouts = 100;

        int[] results = new int[3];

        for (int i = 0; i < playouts; i++) {
            Game ticTacToe = new Game(new TicTacToeBoard(3),
                    new MctsPlayer(1, 1000),
                    new MctsPlayer(2, 1000));


            int winner = ticTacToe.play();
            results[winner-1]++;
        }

        System.out.println("====================");
        System.out.println("Player 1 won:" + results[0]);
        System.out.println("Player 2 won:" + results[1]);
        System.out.println("Draw:" + results[2]);

    }

    @Test
    public void selfPlay_connectFour() {

        int playouts = 1;

        int[] results = new int[3];

        for (int i = 0; i < playouts; i++) {
            Game connectFour = new Game(new ConnectFourBoard(7),
                    new MctsPlayer(1, 10),
                    new MctsPlayer(2, 1000));


            int winner = connectFour.play();
            results[winner-1]++;
        }

        System.out.println("====================");
        System.out.println("Player 1 won:" + results[0]);
        System.out.println("Player 2 won:" + results[1]);
        System.out.println("Draw:" + results[2]);

    }

}
