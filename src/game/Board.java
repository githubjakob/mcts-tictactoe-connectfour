package game;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Board {

    public static final int GAME_IN_PROGRESS = 0;
    public static final int PLAYER_1_WON = 1;
    public static final int PLAYER_2_WON = 2;
    public static final int DRAW = 3;

    private static final Random RANDOM_GENERATOR = new Random();

    public int[][] state; // be careful that it is state[y][x]

    public Point latestMove; // the coordinates of the piece set the last

    public int latestMoveByPlayer; // the number of the player who set the last piece

    private int size; // the size of the board, eg. 3 for a 3*3 board

    public int pieces = 0; // the number of pieces already set on the board

    private Integer status = null;

    public Board(int[][] state) {
        this.size = state.length;
        this.state = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int n = 0; n < size; n++) {
                this.state[i][n] = state[i][n];
                if (state[i][n] != 0) ++this.pieces;
            }
        }
    }

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

    public Board getWinningMoveOrElseRandom() {
        final List<Board> legalMoves = getAllLegalNextMoves();
        if (legalMoves.isEmpty()) {
            return null;
        }
        for (Board move : legalMoves) {
            int player = move.latestMoveByPlayer;
            if (move.state[0][0] == player) {
                if (move.state[1][0] == player && move.state [2][0] == player) {
                    return move;
                }
                if (move.state[0][1] == player && move.state[0][2] == player) {
                    return move;
                }
            }

            if (move.state[2][2] == player) {
                if (move.state[1][2] == player && move.state [0][2] == player) {
                    return move;
                }
                if (move.state[2][1] == player && move.state[2][0] == player) {
                    return move;
                }
            }
            if (move.state[1][1] == player) {
                if (move.state[0][0] == player && move.state[2][2] == player) {
                    return move;
                }
                if (move.state[1][0] == player && move.state[1][2] == player) {
                    return move;
                }
                if (move.state[0][1] == player && move.state[2][1] == player) {
                    return move;
                }
            }
        }
        final int random = RANDOM_GENERATOR.nextInt(legalMoves.size());
        return legalMoves.get(random);

    }

    // returns null when board is full
    public Board getRandomLegalNextMove() {
        final List<Board> legalMoves = getAllLegalNextMoves();
        if (legalMoves.isEmpty()) {
            return null;
        }
        final int random = RANDOM_GENERATOR.nextInt(legalMoves.size());
        return legalMoves.get(random);
    }

    // returns 0 if game is in progress, returns the number of the player who won, returns 3 for a even
    public int getStatus() {

        if (status != null) {
            return status;
        }

        int row = 0, col = 0, diag = 0, rdiag = 0;

        for (int i = 0; i < size; i++) {
            if (state[latestMove.y][i] == latestMoveByPlayer) row++;
            if (state[i][latestMove.x] == latestMoveByPlayer) col++;
            if (state[i][i] == latestMoveByPlayer) diag++;
            if (state[size - i - 1][i] == latestMoveByPlayer) rdiag++;
        }

        if (row == size || col == size || diag == size || rdiag == size) {
            status = latestMoveByPlayer == 1 ? PLAYER_1_WON : PLAYER_2_WON;
            return status;
        }

        if (isBoardFull()) {
            status = DRAW;
            return status;
        }

        status = GAME_IN_PROGRESS;
        return status;
    }

    public java.util.List<Board> getAllLegalNextMoves() {
        int nextPlayer = latestMoveByPlayer % 2 + 1;
        List<Board> nextMoves = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int n = 0; n < size; n++) {
                if(state[i][n] == 0) {
                    Board legalMove = new Board(this.state);
                    legalMove.setSquareOnBoard(new Point(n, i), nextPlayer);
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