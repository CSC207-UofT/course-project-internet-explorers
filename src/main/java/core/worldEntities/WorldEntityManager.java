package core.worldEntities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import core.worldEntities.collisions.CollisionManager;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Use-case class for WorldEntities.
 */
public class WorldEntityManager {

    private final Map<UUID, WorldEntity> entities;
    private final World world;

    public WorldEntityManager(World world) {
        this.world = world;
        this.entities = new HashMap<>();

        world.setContactListener(new CollisionManager());
    }

    /**
     * Creates a WorldEntity's representation in the World,
     * and adds it to the collection of managed entities.
     *
     * @param <T> the type of WorldEntity to create
     * @param bodyDef     Box2D representation details
     * @param fixtureDefs Box2D representation details
     * @return The WorldEntity.
     */
    public <T extends WorldEntity> T createEntity(Class<T> type, BodyDef bodyDef, FixtureDef... fixtureDefs) {
        Body body = this.world.createBody(bodyDef);
        createFixtures(body, fixtureDefs);
        try {
            T entity = type.getConstructor(Body.class).newInstance(body);
            this.entities.put(entity.getId(), entity);
            return entity;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to spawn a WorldEntity of type " + type + ".");
        }
    }

    /**
     * Removes an entity from this World.
     * @param id the id of the entity to delete
     */
    public void deleteEntity(UUID id) {
        world.destroyBody(this.entities.remove(id).getBody());
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

    public Map<UUID, WorldEntity> getEntities() {
        return this.entities;
    }
}
