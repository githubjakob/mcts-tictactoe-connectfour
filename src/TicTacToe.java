public class TicTacToe {

    Board board;

    Player player1;

    Player player2;

    boolean turn = true;

    boolean gameOver = false;

    public static final Round ROUND = new Round();

    public TicTacToe(Player player1, Player player2) {
        board = new Board(3);
        this.player1 = player1;
        this.player2 = player2;
    }

    void play() {
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
            if (winningPlayer != 0) {
                System.out.println("winning player is: " + winningPlayer);
                gameOver = true;
            }

            turn = !turn;
            System.out.print("\n\n");

            board.printBoard();
        }

    }




}