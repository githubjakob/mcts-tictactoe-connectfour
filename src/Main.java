import game.HumanPlayer;
import game.MctsPlayer;

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
       TicTacToe ticTacToe = new TicTacToe(new HumanPlayer(1), new MctsPlayer(2, 200));
       ticTacToe.play();
    }
}
