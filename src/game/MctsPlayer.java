package game;

import mcts.Mcts;

public class MctsPlayer extends Player {

    private final int timer;

    private int playerId; // should be 1 for player 1 or 2 for player 2

    public MctsPlayer(int id, int timer) {
        this.timer = timer;
        this.playerId = id;
    }

    public Board makeNextMove(Board currentBoard) {
        System.out.println("Doing MCTS.");
        Mcts mcts = new Mcts(currentBoard, playerId, timer);
        Board winningMove = mcts.doMcts();
        System.out.println("MCTS done.");

        currentBoard.setSquareOnBoard(winningMove.getLatestMoveCoordinates(), playerId);
        return winningMove;
    }


}
