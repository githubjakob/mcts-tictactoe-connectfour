import java.awt.*;
import java.time.Instant;
import java.util.*;

/**
 * Created by jakob on 18.04.18.
 */
public class MctsPlayer implements Player {

    int playerId;

    MctsPlayer(int id) {
        this.playerId = id;
    }

    @Override
    public Board makeNextMove(Board currentBoard) {
        Node winningNode = doMcts(currentBoard);
        Board move = winningNode.board;
        if (move == null) {
            throw new RuntimeException("hier ist was kaputt");
        }
        System.out.println("MCTS done.");
        boolean success = currentBoard.setSquareOnBoard(move.latestMove, playerId);
        return move;
    }

    private Node doMcts(Board currentBoard) {
        System.out.println("MCTS working.");

        int maxSeconds = 3;
        Instant deadline = Instant.now().plusSeconds(maxSeconds);

        Node tree = new Node(currentBoard);

        while (Instant.now().isBefore(deadline)) {

            //SELECT
            Node promisingNode = selectPromisingNode(tree);

            //EXPAND
            Node selected = expandNodeAndReturnRandom(promisingNode);

            //SIMULATE
            Node leaf = simulateLightPlayout(selected);

            //PROPAGATE
            backPropagation(leaf);
        }

        return tree.getChildWithMaxScore();

    }

    // if node is already a leaf, return the leaf
    private Node expandNodeAndReturnRandom(Node node) {
        int MAX_BRANCHING_FACTOR = 9;

        Node result = node;

        if (node.board.pieces >= 9) {
            node.status = "GAME_OVER";
            return node;
        }

        Board board = node.board;

        for (int i = 0; i < MAX_BRANCHING_FACTOR; i++) {

            Board move = board.getRandomLegalNextMove();
            if (move == null) {
                break;
            }
            Node child = new Node(move);
            child.parent = node;
            node.addChild(child);

            result = child;
        }
        return result;
    }

    private void backPropagation(Node leaf) {
        ++leaf.visits;
        Node node = leaf.parent;
        int reward = leaf.score;

        while (true) { // look for the root
           //System.out.println("propagating back");

           ++node.visits;
           node.score += reward;

           if (node.parent == null) break;

           node = node.parent;
        }

    }


    private Node simulateLightPlayout(Node promisingNode) {
        //System.out.println("simulating playout");
        Node node = promisingNode;
        int result = 0; // 1 if one, -1 if lost

        if (promisingNode.board.pieces >= 9) {
            promisingNode.status = "GAME_OVER";
            return promisingNode;
        }

        boolean gameOver = false;
        while (!gameOver) {
            //System.out.println("simulating moves");

            Board newBoard = node.board.getRandomLegalNextMove();

            if (newBoard == null) {
                throw new RuntimeException("board is full");

            }

            Node child = new Node(newBoard);
            node.addChild(child);
            child.parent = node;

            gameOver = checkWinCondition(child);

            node = child;
        }
        //System.out.println("result is " + result);
        return node;
    }

    boolean checkWinCondition(Node node) {
        // check win condition
        boolean gameOver = false;
        int winner = node.board.isLatestMoveAWin();
        if (winner > 0) {
            if (winner == playerId) {
                node.score++;
            } else if (winner == playerId % 2 + 1) {
                node.score--;
            }

            node.status = "GAME_OVER";
            gameOver = true;
        }
        return gameOver;
    }

    private Node selectPromisingNode(Node tree) {
        //System.out.println("selecting promising node");
        Node node = tree;
        while (node.children.size() != 0) {
            node = UCT.findBestNodeWithUCT(node);
        }
        return node;
    }
}
