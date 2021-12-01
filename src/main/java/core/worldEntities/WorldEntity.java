package core.worldEntities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
    private Body body;
    public final UUID id;

    public WorldEntity() {
        this.id = UUID.randomUUID();
    }

    public Vector2 getPosition() {
        return this.body.getPosition().cpy();
    }

    public void setPosition(Vector2 position) {
        this.body.getPosition().set(position);
    }

    public void setBody(Body body) {this.body = body;}

    public Body getBody() {
        return body;
    }

    public void setLinearVelocity(Vector2 velocity) {
        this.body.setLinearVelocity(velocity);
    }

    public UUID getId() { return this.id; }
}
