import connectfour.ConnectFourBoard;
import general.Board;
import tictactoe.TicTacToeBoard;
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
        TicTacToeBoard board = new TicTacToeBoard(state);
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
        TicTacToeBoard board = new TicTacToeBoard(state);
        board.latestMoveByPlayer = 1;
        board.latestMove = new Point(2,0);
        int result = board.getStatus();
        Assert.assertEquals(1, result);
    }

    @Test
    public void game_won_2() {
        int[] row1 = new int[] {
                1,0,0
        };

        int[] row2 = new int[] {
                1,1,0
        };

        int[] row3 = new int[] {
                2,2,2
        };

        int[][] state = new int[][] {
                row1, row2, row3
        };
        TicTacToeBoard board = new TicTacToeBoard(state);
        board.latestMoveByPlayer = 2;
        board.latestMove = new Point(1,2);
        int result = board.getStatus();
        Assert.assertEquals(2, result);
    }

    @Test
    public void game_getWinningMove() {
        int[] row1 = new int[] {
                2,0,1
        };

        int[] row2 = new int[] {
                2,0,1
        };

        int[] row3 = new int[] {
                0,0,0
        };

        int[][] state = new int[][] {
                row1, row2, row3
        };
        TicTacToeBoard board = new TicTacToeBoard(state);
        board.latestMoveByPlayer = 1;
        board.latestMove = new Point(2,0);
        Board move = board.getWinningMoveOrElseRandom();
        Board expected = board;
        expected.setSquareOnBoard(new Point(0,2), 2);
        Assert.assertEquals(expected.getState(), move.getState());
    }

    @Test
    public void game_getWinningMove_2() {
        int[] row1 = new int[] {
                2,0,1
        };

        int[] row2 = new int[] {
                2,0,1
        };

        int[] row3 = new int[] {
                0,0,0
        };

        int[][] state = new int[][] {
                row1, row2, row3
        };
        TicTacToeBoard board = new TicTacToeBoard(state);
        board.latestMoveByPlayer = 2;
        board.latestMove = new Point(0,0);
        Board move = board.getWinningMoveOrElseRandom();
        Board expected = board;
        expected.setSquareOnBoard(new Point(2,2), 1);
        Assert.assertEquals(expected.getState(), move.getState());
    }

    @Test
    public void connectFour_gameInProgress() {
        int[] col1 = new int[] {
                1,1,0,0,1
        };
        int[] col2 = new int[] {
                0,0,0,0,0
        };
        int[] col3 = new int[] {
                1,0,0,0,0
        };
        int[] col4 = new int[] {
                1,0,0,0,0
        };
        int[] col5 = new int[] {
                0,0,0,0,0
        };
        int[] col6 = new int[] {
                1,0,0,0,0
        };
        int[] col7 = new int[] {
                0,0,0,0,0
        };
        int[][] board = new int[][] {
                col1, col2, col3, col4, col5, col6, col7
        };
        ConnectFourBoard connectFourBoard = new ConnectFourBoard(board);
        connectFourBoard.latestMoveByPlayer = 1;
        connectFourBoard.latestMove = new Point(0,0);
        int status = connectFourBoard.getStatus();
        Assert.assertEquals(status, 0);
    }

    @Test
    public void connectFour_col_player1wins() {
        int[] col1 = new int[] {
                1,1,1,1,1
        };
        int[] col2 = new int[] {
                0,0,0,0,0
        };
        int[] col3 = new int[] {
                1,0,0,0,0
        };
        int[] col4 = new int[] {
                1,0,0,0,0
        };
        int[] col5 = new int[] {
                0,0,0,0,0
        };
        int[] col6 = new int[] {
                1,0,0,0,0
        };
        int[] col7 = new int[] {
                0,0,0,0,0
        };
        int[][] board = new int[][] {
                col1, col2, col3, col4, col5, col6, col7
        };
        ConnectFourBoard connectFourBoard = new ConnectFourBoard(board);
        connectFourBoard.latestMoveByPlayer = 1;
        connectFourBoard.latestMove = new Point(0,0);
        int status = connectFourBoard.getStatus();
        Assert.assertEquals(1, status);
    }

    @Test
    public void connectFour_diag_player1wins() {
        int[] col1 = new int[] {
                1,0,0,0,0
        };
        int[] col2 = new int[] {
                0,1,0,0,0
        };
        int[] col3 = new int[] {
                1,2,1,0,0
        };
        int[] col4 = new int[] {
                1,2,2,1,0
        };
        int[] col5 = new int[] {
                0,0,0,0,0
        };
        int[] col6 = new int[] {
                1,0,0,0,0
        };
        int[] col7 = new int[] {
                0,0,0,0,0
        };
        int[][] board = new int[][] {
                col1, col2, col3, col4, col5, col6, col7
        };
        ConnectFourBoard connectFourBoard = new ConnectFourBoard(board);
        connectFourBoard.latestMoveByPlayer = 1;
        connectFourBoard.latestMove = new Point(0,0);
        int status = connectFourBoard.getStatus();
        Assert.assertEquals(1, status);
    }

    @Test
    public void connectFour_diag2_player1wins() {
        int[] col1 = new int[] {
                1,0,0,0,0
        };
        int[] col2 = new int[] {
                0,1,0,0,0
        };
        int[] col3 = new int[] {
                1,2,1,0,0
        };
        int[] col4 = new int[] {
                1,2,2,1,0
        };
        int[] col5 = new int[] {
                0,0,0,0,0
        };
        int[] col6 = new int[] {
                1,0,0,0,0
        };
        int[] col7 = new int[] {
                0,0,0,0,0
        };
        int[][] board = new int[][] {
                col1, col2, col3, col4, col5, col6, col7
        };
        ConnectFourBoard connectFourBoard = new ConnectFourBoard(board);
        connectFourBoard.latestMoveByPlayer = 1;
        connectFourBoard.latestMove = new Point(1,1);
        int status = connectFourBoard.getStatus();
        Assert.assertEquals(1, status);
    }

    @Test
    public void connectFour_diag3_player1wins() {
        int[] col1 = new int[] {
                0,0,0,0,0
        };
        int[] col2 = new int[] {
                1,0,0,0,0
        };
        int[] col3 = new int[] {
                2,1,1,2,0
        };
        int[] col4 = new int[] {
                1,2,1,1,0
        };
        int[] col5 = new int[] {
                2,2,2,1,0
        };
        int[] col6 = new int[] {
                1,0,0,0,0
        };
        int[] col7 = new int[] {
                0,0,0,0,0
        };
        int[][] board = new int[][] {
                col1, col2, col3, col4, col5, col6, col7
        };
        ConnectFourBoard connectFourBoard = new ConnectFourBoard(board);
        connectFourBoard.latestMoveByPlayer = 1;
        connectFourBoard.latestMove = new Point(4,3);
        int status = connectFourBoard.getStatus();
        Assert.assertEquals(1, status);
    }

    @Test
    public void connectFour_diag4_player1wins() {
        int[] col1 = new int[] {
                0,0,0,0,0
        };
        int[] col2 = new int[] {
                2,0,0,0,0
        };
        int[] col3 = new int[] {
                2,1,1,2,0
        };
        int[] col4 = new int[] {
                1,2,1,1,0
        };
        int[] col5 = new int[] {
                2,2,1,1,0
        };
        int[] col6 = new int[] {
                1,1,0,0,0
        };
        int[] col7 = new int[] {
                1,0,0,0,0
        };
        int[][] board = new int[][] {
                col1, col2, col3, col4, col5, col6, col7
        };
        ConnectFourBoard connectFourBoard = new ConnectFourBoard(board);
        connectFourBoard.latestMoveByPlayer = 1;
        connectFourBoard.latestMove = new Point(6,0);
        int status = connectFourBoard.getStatus();
        Assert.assertEquals(1, status);
    }

    @Test
    public void connectFour_row_player1wins() {
        int[] col1 = new int[] {
                1,1,1,0,1
        };
        int[] col2 = new int[] {
                0,0,0,0,0
        };
        int[] col3 = new int[] {
                1,0,0,0,0
        };
        int[] col4 = new int[] {
                1,0,0,0,0
        };
        int[] col5 = new int[] {
                1,0,0,0,0
        };
        int[] col6 = new int[] {
                1,0,0,0,0
        };
        int[] col7 = new int[] {
                0,0,0,0,0
        };
        int[][] board = new int[][] {
                col1, col2, col3, col4, col5, col6, col7
        };
        ConnectFourBoard connectFourBoard = new ConnectFourBoard(board);
        connectFourBoard.latestMoveByPlayer = 1;
        connectFourBoard.latestMove = new Point(0,0);
        int status = connectFourBoard.getStatus();
        Assert.assertEquals(status, 1);
    }

}