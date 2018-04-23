package mcts;

import game.Board;

import java.time.Instant;

public class Mcts {

    private static final int MCTS_MAX_BRANCHING_FACTOR = 9;

    private static final long MCTS_TIMER = 200; //in milis

    private final Board board;

    private final int playerId;

    private final int opponentId;

    public Mcts(Board board, int playerId) {
        this.board = board;
        this.playerId = playerId;
        this.opponentId = playerId % 2 + 1;
    }

    public Board doMcts() {
        System.out.println("MCTS working.");

        Instant deadline = Instant.now().plusMillis(MCTS_TIMER);

        Node tree = new Node(board);

        while (Instant.now().isBefore(deadline)) {

            //SELECT
            Node promisingNode = selectPromisingNode(tree);

            //EXPAND
            Node selected = promisingNode;
            if (selected.board.getStatus() == Board.GAME_IN_PROGRESS) {
                selected = expandNodeAndReturnRandom(promisingNode);
            }

            //SIMULATE
            Node leaf = simulateLightPlayout(selected);

            //PROPAGATE
            backPropagation(leaf);
        }

        return tree.getChildWithMaxScore().board;
    }

    // if node is already a leaf, return the leaf
    private Node expandNodeAndReturnRandom(Node node) {

        Node result = node;

        game.Board board = node.board;

        int count = 0;

        for (Board move : board.getAllLegalNextMoves()) {
            Node child = new Node(move);
            child.parent = node;
            node.addChild(child);

            result = child;
            count++;
            if (count >= MCTS_MAX_BRANCHING_FACTOR) break;
        }
        return result;
    }

    private void backPropagation(Node leaf) {
        ++leaf.visits;
        Node node = leaf.parent;
        int reward = 0;
        if (leaf.board.getStatus() == playerId) {
            reward = 10;
        }
        leaf.score += reward;

        while (true) { // look for the root
            ++node.visits;
            if (node.board.latestMoveByPlayer == playerId) {
                node.score += reward;
            } else {
                node.score -= reward;
            }

            if (node.parent == null) break;
            node = node.parent;
        }
    }

    private Node simulateLightPlayout(Node promisingNode) {
        Node node = promisingNode;
        int boardStatus = node.board.getStatus();

        if (boardStatus == opponentId) {
            node.parent.score = Integer.MIN_VALUE;
            return node;
        }

        while (boardStatus == Board.GAME_IN_PROGRESS) {
            game.Board nextMove = node.board.getWinningMoveOrElseRandom();

            Node child = new Node(nextMove);
            child.parent = node;
            node.addChild(child);

            boardStatus = nextMove.getStatus();

            node = child;
        }
        return node;
    }

    private Node selectPromisingNode(Node tree) {
        Node node = tree;
        while (node.children.size() != 0) {
            node = UCT.findBestNodeWithUCT(node);
        }
        return node;
    }
}
