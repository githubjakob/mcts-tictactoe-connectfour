package game;

import mcts.Mcts;

public class MctsPlayer extends Player {

    private final int computations;

    private int playerId; // should be 1 for player 1 or 2 for player 2

    public MctsPlayer(int id, int computations) {
        this.computations = computations;
        this.playerId = id;
    }

    public Board makeNextMove(Board currentBoard) {
        System.out.println("Doing MCTS.");
        Mcts mcts = new Mcts(currentBoard, playerId, computations);
        Board winningMove = mcts.doMcts();
        System.out.println("MCTS done.");

        currentBoard.setSquareOnBoard(winningMove.getLatestMoveCoordinates(), playerId);
        return winningMove;
    }


}
