import connectfour.ConnectFourBoard;
import tictactoe.HumanPlayer;
import tictactoe.MctsPlayer;
import tictactoe.TicTacToeBoard;

public class Main {

    /**
     *
     *
     * Game expects input from the human player in the form xy
     * so, 00, sets the mark on the top left field etc.
     *
     * The field is initialized with zeros,
     * if player1 sets a field it becomes marked with 1
     *
     *
     */
    public static void main(String[] args) {
        /*
       Game ticTacToe = new Game(new TicTacToeBoard(3),
               new MctsPlayer(1, 200),
               new HumanPlayer(2));
       ticTacToe.play();
       */


       Game connectFour = new Game(new ConnectFourBoard(7),
               new connectfour.MctsPlayer(1, 4000),
               new connectfour.HumanPlayer(2)
               );
       connectFour.play();
    }
}
