package mcts;

import game.Board;

import java.util.ArrayList;
import java.util.List;

public class Node {

    public Board board;

    int visits;

    int score;

    List<Node> children = new ArrayList<>();

    Node parent = null;

    public Node(Board initBoard) {
        this.board = initBoard;
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