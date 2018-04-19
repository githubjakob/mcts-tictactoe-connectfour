package mcts;

import game.Board;

import java.time.Instant;

public class Mcts {

    private static final int MCTS_MAX_BRANCHING_FACTOR = 9;

    private static final long MCTS_TIMER = 2; //in seconds

    public static Board doMcts(Board currentBoard, int playerId) {
        System.out.println("MCTS working.");

        Instant deadline = Instant.now().plusSeconds(MCTS_TIMER);

        Node tree = new Node(currentBoard);

        while (Instant.now().isBefore(deadline)) {

            //SELECT
            Node promisingNode = selectPromisingNode(tree);

            //EXPAND
            Node selected = expandNodeAndReturnRandom(promisingNode);

            //SIMULATE
            Node leaf = simulateLightPlayout(selected, playerId);

            //PROPAGATE
            backPropagation(leaf);
        }

        return tree.getChildWithMaxScore().board;

    }

    // if node is already a leaf, return the leaf
    private static Node expandNodeAndReturnRandom(Node node) {

        Node result = node;

        if (node.board.isBoardFull()) {
            return node;
        }

        game.Board board = node.board;

        for (int i = 0; i < MCTS_MAX_BRANCHING_FACTOR; i++) {

            game.Board move = board.getRandomLegalNextMove();
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

    private static void backPropagation(Node leaf) {
        ++leaf.visits;
        Node node = leaf.parent;
        int reward = leaf.score;

        while (true) { // look for the root
            ++node.visits;
            node.score += reward;
            if (node.parent == null) break;
            node = node.parent;
        }
    }

    private static Node simulateLightPlayout(Node promisingNode, int playerId) {
        Node node = promisingNode;

        if (promisingNode.board.pieces >= 9) {
            return promisingNode;
        }

        boolean gameOver = false;
        while (!gameOver) {
            game.Board nextMove = node.board.getRandomLegalNextMove();

            if (nextMove == null) {
                throw new RuntimeException("board is full");
            }

            Node child = new Node(nextMove);
            child.parent = node;
            node.addChild(child);

            gameOver = checkGameCondition(child, playerId);
            node = child;
        }
        return node;
    }

    private static boolean checkGameCondition(Node node, int playerId) {
        boolean gameOver = false;
        int winner = node.board.isLatestMoveAWin();
        if (winner > 0) {
            if (winner == playerId) {
                node.score++;
            } else if (winner == playerId % 2 + 1) {
                node.score--;
            }

            gameOver = true;
        }
        return gameOver;
    }

    private static Node selectPromisingNode(Node tree) {
        Node node = tree;
        while (node.children.size() != 0) {
            node = UCT.findBestNodeWithUCT(node);
        }
        return node;
    }
}
