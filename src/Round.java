/**
 * Created by jakob on 19.04.18.
 */
public class Round {

    boolean turn;

    Round() {
        this.turn = false;
    }

    int nextRound() {
        this.turn = !this.turn;
        return turn ? 1 : 2;
    }
}
