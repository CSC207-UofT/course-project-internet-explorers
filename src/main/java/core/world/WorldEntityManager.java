package core.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Use-case class for WorldEntities.
 */
public class WorldEntityManager {

    private Map<UUID, WorldEntity> entities;
    private final World world;

    public WorldEntityManager(World world) {
        this.world = world;
        this.entities = new HashMap<>();
    }

    /**
     * Creates a WorldEntity's representation in the World,
     * and adds it to the collection of managed entities.
     *
     * @param entity      The entity to register
     * @param bodyDef     Box2D representation details
     * @param fixtureDefs Box2D representation details
     * @return The WorldEntity's representation as a Box2D Body.
     */
    protected Body register(WorldEntity entity, BodyDef bodyDef, FixtureDef... fixtureDefs) {
        Body body = world.createBody(bodyDef);
        createFixtures(body, fixtureDefs);
        this.entities.put(entity.id, entity);
        return body;
    }

    /**
     * Adds Box2D fixtures to the specified WorldEntity's Box2D Body.
     *
     * @param entityId The target entity's id
     * @param defs     details of fixtures to add
     */
    public void createFixtures(UUID entityId, FixtureDef... defs) {
        createFixtures(getEntity(entityId).getBody(), defs);
    }

    private void createFixtures(Body body, FixtureDef... defs) {
        for (FixtureDef def : defs) {
            body.createFixture(def);
        }
    }

    /**
     * Draw all the entities on the screen.
     *
     * @param batch a SpriteBatch which is already drawing (batch.begin() has been called)
     */
    public void draw(SpriteBatch batch) {
        entities.forEach((id, e) -> {
            if (e instanceof WorldEntityWithSprite entity) {
                Sprite sprite;
                if ((sprite = entity.getSprite()) != null) {
                    sprite.draw(batch);
                }
            }
        });
    }

    public WorldEntity getEntity(UUID id) {
        return entities.get(id);
    }

    public void setLinearVelocity(UUID id, Vector2 velocity) {
        entities.get(id).body.setLinearVelocity(velocity);
    }

    /**
     * Set the entity's velocity such that it reaches the target position in the specified amount of time `dt`.
     */
    public void setTeleportVelocity(UUID id, Vector2 target, float dt) {
        Body body = entities.get(id).body;
        body.setLinearVelocity(target.cpy().sub(body.getPosition().cpy()).scl(1 / dt));
    }

    public void teleport(UUID id, Vector2 target) {
        Body body = entities.get(id).body;
        body.setTransform(target, body.getAngle());
    }
}
