package core.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class LevelLoader {

    public static LevelState getLevel1() {
        LevelState lvl = new LevelState("maps/demo.tmx");
        lvl.world = new World(new Vector2(0, 0), true);

        return lvl;
    }
}
