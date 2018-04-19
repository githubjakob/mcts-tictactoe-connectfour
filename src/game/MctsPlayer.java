package game;

import mcts.Mcts;

/**
 * Created by jakob on 18.04.18.
 */
public class MctsPlayer implements Player {

    private int playerId; // should be 1 for player 1 or 2 for player 2

    public MctsPlayer(int id) {
        this.playerId = id;
    }

    @Override
    public Board makeNextMove(Board currentBoard) {
        System.out.println("Doing MCTS.");
        Board winningMove = Mcts.doMcts(currentBoard, playerId);
        System.out.println("MCTS done.");

        currentBoard.setSquareOnBoard(winningMove.getLatestMoveCoordinates(), playerId);
        return winningMove;
    }


}
