import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Node {

    String status = "GAME_IN_PROGRESS";

    Board board;

    int player;

    int visits;

    int score;

    List<Node> children = new ArrayList<>();

    Node parent = null;

    public Node(Board initBoard) {
        this.board = initBoard;
        this.player = player;
    }

    void setBoard(Board board) {
        this.board = board;
    }

    Node getChildWithMaxScore() {
        Node result = children.get(0);
        for (int i = 1; i < children.size(); i++) {
            if (children.get(i).score > result.score) {
                result = children.get(i);
            }
        }
        return result;
    }

    void addChild(Node node) {
        children.add(node);
    }
}