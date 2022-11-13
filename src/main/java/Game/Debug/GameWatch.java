package Game.Debug;

import Game.Game;

public class GameWatch implements Runnable{

    private Game game;
    public GameWatch(Game game)
    {
        this.game =game;
    }
    @Override
    public void run() {

    }
}
