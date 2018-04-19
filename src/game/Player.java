package game;

import game.Board;

public interface Player {

    Board makeNextMove(Board board);

}