package core.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import java.util.UUID;

/**
 * Represents an entity within a level's World.
 * <p>
 * A WorldEntity has a unique ID and is represented as a Box2D Body within the World.
 *
 * TODO check with ben if we should make WorldEntity properties protected
 *      and only have public getters/setters in WorldManager
 */
public class WorldEntity {

    /**
     * The Box2D Body representing this WorldEntity in the World.
     * The Body's position is also the position of this WorldEntity.
     */
    protected final Body body;
    public final UUID id;

    public WorldEntity(WorldEntityManager entityManager, BodyDef bodyDef, FixtureDef... fixtureDefs) {
        this.id = UUID.randomUUID();

        this.body = entityManager.register(this, bodyDef, fixtureDefs);
    }

    public Vector2 getPosition() {
        return this.body.getPosition().cpy();
    }

    public void setPosition(Vector2 position) {
        this.body.getPosition().set(position);
    }

    public Body getBody() {
        return body;
    }
}
