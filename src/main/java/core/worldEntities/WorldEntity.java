package core.worldEntities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import java.util.UUID;

/**
 * Represents an entity within a level's World.
 * <p>
 * A WorldEntity has a unique ID and is represented as a Box2D Body within the World.
 */
public class WorldEntity {

    /**
     * The Box2D Body representing this WorldEntity in the World.
     * The Body's position is also the position of this WorldEntity.
     */
    private final Body body;
    private final UUID id;

    public WorldEntity(Body body) {
        this.id = UUID.randomUUID();

        this.body = body;
        this.body.setUserData(this);
    }

    public Vector2 getPosition() {
        return this.body.getPosition().cpy();
    }

    public Body getBody() {
        return body;
    }

    public void setLinearVelocity(Vector2 velocity) {
        this.body.setLinearVelocity(velocity);
    }

    public UUID getId() {
        return id;
    }
}
