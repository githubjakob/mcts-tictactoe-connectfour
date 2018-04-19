package game;

import mcts.Mcts;

public class MctsPlayer implements Player {

    private int playerId; // should be 1 for player 1 or 2 for player 2

    public MctsPlayer(int id) {
        this.playerId = id;
    }

    @Override
    public Board makeNextMove(Board currentBoard) {
        System.out.println("Doing MCTS.");
        Mcts mcts = new Mcts(currentBoard, playerId);
        Board winningMove = mcts.doMcts();
        System.out.println("MCTS done.");

        currentBoard.setSquareOnBoard(winningMove.getLatestMoveCoordinates(), playerId);
        return winningMove;
    }


}
