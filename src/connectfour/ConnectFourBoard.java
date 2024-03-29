package connectfour;

import general.AbstractBoard;
import general.Board;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ConnectFourBoard extends AbstractBoard implements Board {

    public int[][] state;

    public Point latestMove; // the coordinates of the piece set the last

    private int width; // the width of the board, eg. 3 for a 3*3 board

    private int height = 6;

    public int pieces = 0; // the number of pieces already set on the board

    private Integer status = null;

    public ConnectFourBoard(int[][] state) {
        this.width = state.length;
        this.height = state[0].length;
        this.state = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int n = 0; n < height; n++) {
                this.state[i][n] = state[i][n];
                if (state[i][n] != 0) ++this.pieces;
            }
        }
    }

    public ConnectFourBoard(int size) {
        this.width = size;
        state = new int[width][height];  // spaltenweise

        for (int i = 0; i < height; i++) {
            for (int n = 0; n < width; n++) {
                state[n][i] = 0;
            }
        }
    }

    public Point getLatestMoveCoordinates() {
        return latestMove;
    }

    @Override
    public int[][] getState() {
        return this.state;
    }

    public void printBoard() {
        for (int i = height-1; i >= 0; i--) {
            System.out.print("|");
            for (int n = 0; n < width; n++) {
                int field = state[n][i];
                if (field == 0) {
                    System.out.print(" ");
                } else if (field == 1) {
                    System.out.print("x");
                } else {
                    System.out.print("o");
                }
                System.out.print("|");
            }
            System.out.print("\n");
        }
        System.out.print("|0|1|2|3|4|5|6|");
        System.out.print("\n\n");
    }

    // returns false when coordinates already marked
    public boolean setSquareOnBoard(Point coordinates, int player) {
        if (coordinates == null) {
            throw new RuntimeException("coordinates are null");
        }
        if (state == null) {
            throw new RuntimeException("game.ConnectFourBoard is null");
        }

        int current = state[coordinates.x][coordinates.y];
        if (current != 0) {
            return false;
        } else {
            ++pieces;
            latestMove = coordinates;
            latestMoveByPlayer = player;
            state[coordinates.x][coordinates.y] = player;
        }
        return true;
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

        if (isBoardFull()) {
            status = DRAW;
            return status;
        }

        if (pieces == 0) {
            status = GAME_IN_PROGRESS;
            return status;
        }

        int row = 0, col = 0, diag = 0, rdiag = 0;

        for (int i = 0; i < width; i++) {
                if (i < height && state[latestMove.x][i] == latestMoveByPlayer) {
                    col++;
                    if (col == 4) {
                        status = latestMoveByPlayer == 1 ? PLAYER_1_WON : PLAYER_2_WON;
                        return status;
                    }
                } else col = 0;

                if (state[i][latestMove.y] == latestMoveByPlayer) {
                    row++;
                    if (row  == 4) {
                        status = latestMoveByPlayer == 1 ? PLAYER_1_WON : PLAYER_2_WON;
                        return status;
                    }
                } else row = 0;

                int left = latestMove.x-latestMove.y;
                if (left >= 0 && left+i < width && i < height && state[left+i][i] == latestMoveByPlayer) {
                    diag++;
                    if (diag == 4) {
                        status = latestMoveByPlayer == 1 ? PLAYER_1_WON : PLAYER_2_WON;
                        return status;
                    }
                } else diag = 0;

                int right = latestMove.x+latestMove.y;
                if (right < width && right-i >= 0 && i < height && state[right-i][i] == latestMoveByPlayer) {
                    rdiag++;
                    if (rdiag == 4) {
                        status = latestMoveByPlayer == 1 ? PLAYER_1_WON : PLAYER_2_WON;
                        return status;
                    }
                } else rdiag = 0;
/*
                if (state[latestMove.x][i] == latestMoveByPlayer) {
                    col++;
                    if (col == 4) {
                        status = latestMoveByPlayer == 1 ? PLAYER_1_WON : PLAYER_2_WON;
                        return status;
                    }
                } else col = 0;


                if (state[latestMove.x][latestMove.y-i] == latestMoveByPlayer) row++;
                if (state[i][i] == latestMoveByPlayer) diag++;
                if (state[width - i - 1][i] == latestMoveByPlayer) rdiag++;*/

        }

        status = GAME_IN_PROGRESS;
        return status;
    }

    @Override
    public List<Board> getAllLegalNextMoves() {
        int nextPlayer = latestMoveByPlayer % 2 + 1;

        List<Board> nextMoves = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int n = 0; n < height; n++) {
                if(state[i][n] == 0) {
                    ConnectFourBoard legalMove = new ConnectFourBoard(this.state);
                    legalMove.setSquareOnBoard(new Point(i, n), nextPlayer);
                    nextMoves.add(legalMove);
                    break;
                }
            }
        }
        return nextMoves;
    }

    public boolean isBoardFull() {
        return pieces == height * width;
    }
}