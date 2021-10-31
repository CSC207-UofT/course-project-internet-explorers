package core.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import java.util.Arrays;

/**
 * Use-case class for box2d World.
 * Implements methods for rendering the World and stepping the simulation.
 * <p>
 * The WorldEntity objects living in this World are stored as the UserData of the internal Box2D World.
 */
public class WorldManager {

    private final World world;
    private SpriteBatch batch;

    public WorldManager(World world) {
        this.world = world;
        batch = new SpriteBatch();
    }

    public void draw(OrthographicCamera camera) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (WorldEntityManager manager : getWorldEntityManagers()) {
            manager.draw(batch);
        }
        batch.end();
    }

    /**
     * Returns the WorldEntities that have been added to the World.
     */
    public WorldEntityManager[] getWorldEntityManagers() {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);

        return Arrays.stream(bodies.toArray(Body.class)).map(b -> (WorldEntityManager) b.getUserData()).toArray(WorldEntityManager[]::new);
    }

    /**
     * Adds a WorldEntity to the World as a DynamicBody.
     * The WorldEntity's `position` property is reassigned to the resulting Body's position.
     *
     * @param entityManager The WorldEntityManager of the WorldEntity to be added.
     */
    public void addEntityToWorld(WorldEntityManager entityManager) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(entityManager.getEntity().getPosition());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        addEntityToWorld(entityManager, bodyDef);
    }

    /**
     * Adds a WorldEntity to the World, and reassigns its `position` property to the box2d Body's position.
     *
     * @param entityManager The WorldEntityManager of the WorldEntity to be added.
     * @param bodyDef       Box2D Body configuration.
     */
    public void addEntityToWorld(WorldEntityManager entityManager, BodyDef bodyDef) {
        Body body = world.createBody(bodyDef);
        body.setUserData(entityManager);
        entityManager.getEntity().position = body.getPosition();
        entityManager.setBody(body);
    }
}
