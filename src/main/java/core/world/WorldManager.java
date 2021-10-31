package core.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import java.util.Arrays;

/**
 * Use-case class for box2d World.
 * Implements methods for rendering the World and stepping the simulation.
 *
 * The WorldEntity objects living in this World are stored as the UserData of the internal Box2D World.
 */
public class WorldManager {

    private World world;
    private SpriteBatch batch;

    public WorldManager(World world) {
        this.world = world;
    }

    /**
     * Returns the WorldEntities that have been added to the World.
     */
    public WorldEntity[] getWorldEntities() {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);

        return Arrays.stream(bodies.toArray()).map(b -> (WorldEntity) b.getUserData()).toArray(WorldEntity[]::new);
    }

    /**
     * Adds a WorldEntity to the World as a DynamicBody.
     * The WorldEntity's `position` property is reassigned to the resulting Body's position.
     *
     * @param entity The WorldEntity to be added.
     */
    public void addEntityToWorld(WorldEntity entity) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(entity.getPosition());
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        addEntityToWorld(entity, bodyDef);
    }

    /**
     * Adds a WorldEntity to the World, and reassigns its `position` property to the box2d Body's position.
     *
     * @param entity The WorldEntity to be added.
     * @param bodyDef Box2D Body configuration.
     */
    public void addEntityToWorld(WorldEntity entity, BodyDef bodyDef) {
        Body body = world.createBody(bodyDef);
        body.setUserData(entity);
        entity.position = body.getPosition();
    }
}
