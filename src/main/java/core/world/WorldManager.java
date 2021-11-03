package core.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorldManager {

    private Map<UUID, WorldEntity> entities;
    private World world;

    public WorldManager(World world) {
        this.world = world;
        this.entities = new HashMap<>();
    }

    public WorldEntity createEntity(BodyDef def) {
        WorldEntity entity = new WorldEntity(world.createBody(def));
        this.entities.put(entity.id, entity);
        return entity;
    }

    public WorldEntity getEntity(UUID id) {
        return entities.get(id);
    }

    /**
     * Steps the physics simulation of the World.
     *
     * @param dt time delta to simulate (seconds) (capped at .5 in case computer is too slow)
     */
    public void step(float dt) {
        world.step(Math.min(dt, 0.5f), 6, 2);
    }

    /**
     * Draw all the entities on the screen.
     *
     * @param batch a SpriteBatch which is already drawing (batch.begin() has been called)
     */
    public void draw(SpriteBatch batch) {
        entities.forEach((id, e) -> {
            updateEntitySprite(e);
            e.getSprite().draw(batch);
        });
    }

    public static void updateEntitySprite(WorldEntity e) {
        Sprite sprite = e.getSprite();
        if (sprite == null) {
            return;
        }

        // Set sprite rotation origin and angle
        Vector2 origin = e.body.getLocalCenter();
        sprite.setOrigin(origin.x, origin.y);
        sprite.setRotation(e.body.getAngle() * 6.2832f);

        // Set sprite position and dimensions
        Vector2 pos = e.body.getPosition().add(e.getOffset());
        Vector2 size = e.getSize();
        sprite.setBounds(pos.x, pos.y, size.x, size.y);
    }
}
