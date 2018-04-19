import game.Board;
import game.Player;
import game.Round;

public class TicTacToe {

    private Board board;

    private Player player1;

    private Player player2;

    private boolean gameOver = false;

    private static final Round ROUND = new Round();

    public TicTacToe(Player player1, Player player2) {
        board = new Board(3);
        this.player1 = player1;
        this.player2 = player2;
    }

    public void play() {
        System.out.println("Game start");
        board.printBoard();

        while (!gameOver) {

            int player = ROUND.nextRound();

            if (player == 1) {
                System.out.println("Player 1 move");
                player1.makeNextMove(board);
            }

            if (player == 2) {
                System.out.println("Player 2 move");
                player2.makeNextMove(board);
            }

            int winningPlayer = board.isLatestMoveAWin();

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
    }
}