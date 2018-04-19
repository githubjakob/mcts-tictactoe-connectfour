package game;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Board {

    private int[][] state; // be careful that it is state[y][x]

    private Point latestMove; // the coordinates of the piece set the last

    private int latestMoveByPlayer; // the number of the player who set the last piece

    private int size; // the size of the board, eg. 3 for a 3*3 board

    public int pieces = 0; // the number of pieces already set on the board

    private static final Random RANDOM_GENERATOR = new Random();

    public Board(int size) {
        this.size = size;
        state = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int n = 0; n < state[0].length; n++) {
                state[i][n] = 0;
            }
        }
    }

    public Point getLatestMoveCoordinates() {
        return latestMove;
    }

    public void printBoard() {
        for (int i = 0; i < state.length; i++) {
            for (int n = 0; n < state[0].length; n++) {
                System.out.print(state[i][n]);
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }

    // returns false when coordinates already marked
    public boolean setSquareOnBoard(Point coordinates, int player) {
        if (coordinates == null) {
            throw new RuntimeException("coordinates are null");
        }
        if (state == null) {
            throw new RuntimeException("game.Board is null");
        }

        int current = state[coordinates.y][coordinates.x];
        if (current != 0) {
            return false;
        } else {
            ++pieces;
            latestMove = coordinates;
            latestMoveByPlayer = player;
            state[coordinates.y][coordinates.x] = player;
        }
        return true;
    }

    // returns null when board is full
    public Board getRandomLegalNextMove() {
        final List<Board> legalMoves = getLegalNextMoves();
        if (legalMoves.isEmpty()) {
            return null;
        }
        final int random = RANDOM_GENERATOR.nextInt(legalMoves.size());
        return legalMoves.get(random);
    }

    private Board cloneBoard() {
        int[][] newState = new int[size][size];
        Board newBoard = new Board(size);
        newBoard.state = newState;
        for (int i = 0; i < size; i++) {
            for (int n = 0; n < size; n++) {
                newState[i][n] = state[i][n];
                if (state[i][n] != 0) ++newBoard.pieces;
            }
        }
        return newBoard;
    }

    // returns 0 if game is in progress, returns the number of the player who won, returns 3 for a even
    public int isLatestMoveAWin() {
        int row = 0, col = 0, diag = 0, rdiag = 0;

        if (isBoardFull()) {
            return 3;
        }

        for (int i = 0; i < size; i++) {
            if (state[latestMove.y][i] == latestMoveByPlayer) row++;
            if (state[i][latestMove.x] == latestMoveByPlayer) col++;
            if (state[i][i] == latestMoveByPlayer) diag++;
            if (state[size - i - 1][i] == latestMoveByPlayer) rdiag++;
        }
        if (row == size || col == size || diag == size || rdiag == size) {
            return latestMoveByPlayer;
        }

        return 0;
    }

    private java.util.List<Board> getLegalNextMoves() {
        int nextPlayer = latestMoveByPlayer % 2 + 1;
        List<Board> nextMoves = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int n = 0; n < size; n++) {
                if(state[i][n] == 0) {
                    Board legalMove = this.cloneBoard();
                    legalMove.setSquareOnBoard(new Point(n, i), nextPlayer);
                    legalMove.latestMove = new Point(n, i);
                    legalMove.latestMoveByPlayer = nextPlayer;
                    nextMoves.add(legalMove);
                }
            }
        }
        return nextMoves;
    }

    public boolean isBoardFull() {
        return pieces == size * size;
    }
}