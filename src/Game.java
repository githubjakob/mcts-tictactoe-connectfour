import general.Board;
import general.Player;
import tictactoe.TicTacToeBoard;
import general.Round;

public class Game {

    private Board board;

    private Player player1;

    private Player player2;

    private boolean gameOver = false;

    private static final Round ROUND = new Round();

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play() {
        System.out.println("Game start");
        board.printBoard();

        int winningPlayer = 0;

        while (!gameOver) {

            int player = ROUND.nextRound();

            if (player == 1) {
                System.out.println("Player 1 move");
                player1.makeNextMove(board);

            } else {
                System.out.println("Player 2 move");
                player2.makeNextMove(board);
            }

            winningPlayer = board.getStatus();

            if (winningPlayer == 1 || winningPlayer == 2) {
                System.out.println("winning player is: " + winningPlayer);
                gameOver = true;
            }

            if (winningPlayer == 3) {
                System.out.println("It's a draw.");
                gameOver = true;
            }

            System.out.print("\n\n");

            board.printBoard();
        }

        return winningPlayer;
    }
}