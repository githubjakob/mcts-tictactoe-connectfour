package general;

import java.util.Random;

/**
 * Created by jakob on 03.05.18.
 */
public class AbstractBoard {

    public int latestMoveByPlayer = 2; // the number of the player who set the last piece

    public int getLatestMovePlayer() {
        return latestMoveByPlayer;
    }
}
