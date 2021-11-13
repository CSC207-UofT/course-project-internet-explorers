package core.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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

    public WorldEntity createEntity(BodyDef bodyDef, FixtureDef... fixtureDefs) {
        WorldEntity entity = new WorldEntity(world.createBody(bodyDef));
        this.entities.put(entity.id, entity);
        createFixtures(entity, fixtureDefs);
        // TODO maybe just return ID
        return entity;
    }

    /**
     * Adds Box2D fixtures to the specified WorldEntity's Box2D Body.
     *
     * @param entityId The target entity's id
     * @param defs     details of fixtures to add
     */
    public void createFixtures(UUID entityId, FixtureDef... defs) {
        createFixtures(getEntity(entityId), defs);
    }

    private void createFixtures(WorldEntity entity, FixtureDef... defs) {
        for (FixtureDef def : defs) {
            entity.body.createFixture(def);
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
            if (updateEntitySprite(e)) {
                e.getSprite().draw(batch);
            }
        });
    }

    /**
     * Invoke `render` on a Box2DDebugRenderer to draw the physics going on in this world.
     * Used for debugging.
     *
     * @param renderer
     * @param camera
     */
    public void drawPhysics(Box2DDebugRenderer renderer, OrthographicCamera camera) {
        renderer.render(world, camera.combined);
    }

    /**
     * Sync a WorldEntity's sprite geometry to its currently stored geometry.
     *
     * @param e
     */
    public static boolean updateEntitySprite(WorldEntity e) {
        Sprite sprite = e.getSprite();
        if (sprite == null) {
            return false;
        }

        // Set sprite rotation origin and angle
        Vector2 origin = e.body.getLocalCenter();
        sprite.setOrigin(origin.x, origin.y);
        sprite.setRotation(e.body.getAngle() * 6.2832f);

        // Set sprite position and dimensions
        Vector2 pos = e.body.getPosition().add(e.getOffset());
        Vector2 size = e.getSize();
        sprite.setBounds(pos.x, pos.y, size.x, size.y);
        return true;
    }
    // TODO check with ben if we should make WorldEntity properties protected
    //      and only have public getters/setters in WorldManager
}
