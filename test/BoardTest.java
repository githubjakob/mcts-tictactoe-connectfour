import game.Board;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;


public class BoardTest {
    
    @Test
    public void game_ongoing() {
        int[] row1 = new int[] {
          0,0,0
        };

        int[] row2 = new int[] {
                0,0,0
        };

        int[] row3 = new int[] {
                0,0,0
        };

        int[][] state = new int[][] {
            row1, row2, row3
        };
        Board board = new Board(state);
        board.latestMoveByPlayer = 1;
        board.latestMove = new Point(1,1);
        int result = board.getStatus();
        Assert.assertEquals(0, result);
    }

    @Test
    public void game_won() {
        int[] row1 = new int[] {
                0,0,1
        };

        int[] row2 = new int[] {
                0,0,1
        };

        int[] row3 = new int[] {
                0,0,1
        };

        int[][] state = new int[][] {
                row1, row2, row3
        };
        Board board = new Board(state);
        board.latestMoveByPlayer = 1;
        board.latestMove = new Point(2,0);
        int result = board.getStatus();
        Assert.assertEquals(1, result);
    }

}