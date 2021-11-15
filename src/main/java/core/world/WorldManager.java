package core.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import java.util.*;

/**
 * Use-case class managing WorldEntities and their Box2D representation.
 * TODO split into WorldManager and separate WorldEntityManager (?)
 *      could make WorldManager methods part of LevelManager
 */
public class WorldManager {

    private Map<UUID, WorldEntity> entities;
    private World world;

    public WorldManager(World world) {
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
    protected Body addEntityToWorld(WorldEntity entity, BodyDef bodyDef, FixtureDef... fixtureDefs) {
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
            if (e instanceof WorldEntityWithSprite entity) {
                Sprite sprite;
                if ((sprite = entity.getSprite()) != null) {
                    sprite.draw(batch);
                }
            }
        });
    }

    /**
     * Invoke `render` on a Box2DDebugRenderer to draw the physics going on in this world.
     * Used for debugging.
     */
    public void drawPhysics(Box2DDebugRenderer renderer, OrthographicCamera camera) {
        renderer.render(world, camera.combined);
    }
    // TODO check with ben if we should make WorldEntity properties protected
    //      and only have public getters/setters in WorldManager
}
