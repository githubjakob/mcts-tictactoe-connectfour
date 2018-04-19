import java.awt.*;
import java.util.*;
import java.util.List;

public class Board {

    int[][] state; // be careful that it is state[y][x]

    Point latestMove;

    int latestMoveByPlayer;

    int size;

    int pieces = 0;

    public static Random randomGenerator = new Random();

    public Board(int size) {
        this.size = size;
        state = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int n = 0; n < state[0].length; n++) {
                state[i][n] = 0;
            }
        }
    }

    void printBoard() {
        for (int i = 0; i < state.length; i++) {
            for (int n = 0; n < state[0].length; n++) {
                System.out.print(state[i][n]);
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }

    // returns false when coordinates already marked
    boolean setSquareOnBoard(Point coordinates, int player) {
        if (coordinates == null) {
            throw new RuntimeException("coordinates are null");
        }
        if (state == null) {
            throw new RuntimeException("Board is null");
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
    Board getRandomLegalNextMove() {
        final List<Board> legalMoves = getLegalNextMoves();
        if (legalMoves.isEmpty()) {
            return null;
        }
        final int random = randomGenerator.nextInt(legalMoves.size());
        return legalMoves.get(random);
    }

    Board cloneBoard() {
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

    // returns 0 if no, returns the number of the player who won, returns 3 for a even
    int isLatestMoveAWin() {
        int row = 0, col = 0, diag = 0, rdiag = 0, count = 0;
        int boardSize = size;

        if (pieces == boardSize * boardSize) {
            return 3;
        }

        for (int i = 0; i < boardSize; i++) {
            if (state[latestMove.y][i] == latestMoveByPlayer) row++;
            if (state[i][latestMove.x] == latestMoveByPlayer) col++;
            if (state[i][i] == latestMoveByPlayer) diag++;
            if (state[boardSize - i - 1][i] == latestMoveByPlayer) rdiag++;
        }
        if (row == boardSize || col == boardSize || diag == boardSize || rdiag == boardSize) {
            return latestMoveByPlayer;
        }

        return 0;
    }

    java.util.List<Board> getLegalNextMoves() {
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
}