import game.MctsPlayer;
import org.junit.Test;

/**
 * Created by jakob on 23.04.18.
 */
public class MctsSelfPlay {

    @Test
    public void selfPlay() {

        int playouts = 100;

        int[] results = new int[3];

        for (int i = 0; i < playouts; i++) {
            TicTacToe ticTacToe = new TicTacToe(new MctsPlayer(1, 1000), new MctsPlayer(2, 1000));


            int winner = ticTacToe.play();
            results[winner-1]++;
        }

        System.out.println("====================");
        System.out.println("Player 1 won:" + results[0]);
        System.out.println("Player 2 won:" + results[1]);
        System.out.println("Draw:" + results[2]);

    }

}
