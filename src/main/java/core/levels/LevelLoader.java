package core.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import core.world.EntitySpawner;
import static core.world.DemoSpawners.*;

import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    /**
     * Load level with spawners and map
     *
     */
    public static LevelState getLevel1() {
        // Create enemy spawner list
        List<EntitySpawner> enemies = new ArrayList<EntitySpawner>();
        for (int i = 0; i < 5; i++){
            EntitySpawner enemySpawner = createEnemySpawner();
            enemies.add(enemySpawner);
        }
        LevelState lvl = new LevelState("maps/demo.tmx", enemies);
        lvl.setUnitScale(1 / 64f);
        lvl.world = new World(new Vector2(0, 0), true);

        return lvl;
    }
}
