package core.world;

import com.badlogic.gdx.math.Vector2;
import core.world.WorldEntity;

/**
 * Controller class for spawning entities within the game
 */
public class SpawnController {

    /**
     * The x and y location where a WorldEntity should be spawned to
     */
    private Vector2 spawnLocation;

    // TODO: Figure out SpawnController so it can just take in WorldEntity
    public SpawnController(Vector2 spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public void spawn(WorldEntity worldEntity) {
        worldEntity.setPosition(this.spawnLocation);
    }
}
