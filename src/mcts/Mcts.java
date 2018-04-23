package mcts;

import game.Board;

import java.time.Instant;

public class Mcts {

    private static final int MCTS_MAX_BRANCHING_FACTOR = 9;

    //private static final long MCTS_TIMER = 100; //in milis

    private final Board board;

    private final int playerId;

    private final int opponentId;

    private final int timer;

    public Mcts(Board board, int playerId, int timer) {
        this.board = board;
        this.playerId = playerId;
        this.opponentId = playerId % 2 + 1;
        this.timer = timer;
    }

    public Board doMcts() {
        System.out.println("MCTS working.");

        long counter = 0l;

        Instant deadline = Instant.now().plusMillis(timer);

        Node tree = new Node(board);

        while (Instant.now().isBefore(deadline)) {
            counter++;

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

        Node best = tree.getChildWithMaxScore();

        System.out.println("Did " + counter + " expansions/simulations withing " + timer + " milis");
        System.out.println("Best move scored " + best.score + " and was visited " + best.visits + " times");

        return best.board;
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

        while (node.board.getStatus() == Board.GAME_IN_PROGRESS) {
            game.Board nextMove = node.board.getWinningMoveOrElseRandom();

            Node child = new Node(nextMove);
            child.parent = node;
            node.addChild(child);

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
