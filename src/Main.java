import java.awt.*;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class Main {

    public static void main(String[] args) {
       //TicTacToe ticTacToe = new TicTacToe(new HumanPlayer(1), new MctsPlayer(2));
       TicTacToe ticTacToe = new TicTacToe(new MctsPlayer(1), new HumanPlayer(2));
       ticTacToe.play();
    }
}
