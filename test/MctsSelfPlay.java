import game.MctsPlayer;
import org.junit.Test;

/**
 * Created by jakob on 23.04.18.
 */
public class MctsSelfPlay {

    @Test
    public void selfPlay() {

        int playouts = 10;

        int[] results = new int[playouts];

        for (int i = 0; i < playouts; i++) {
            TicTacToe ticTacToe = new TicTacToe(new MctsPlayer(1, 2000), new MctsPlayer(2, 1000));


            int winner = ticTacToe.play();
            results[i] = winner;
        }

        for (int i = 0; i <playouts; i++) {
            System.out.println(results[i]);
        }

    }

}
