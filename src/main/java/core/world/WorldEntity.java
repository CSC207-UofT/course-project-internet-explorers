package core.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import java.util.UUID;

/**
 * Represents an entity within a level's World.
 * <p>
 * A WorldEntity has a position and a rectangular bounding box,
 * expressed as its dimensions and that box's offset relative
 * to the position.
 *
 * TODO explore whether this should extend Body
 */
public class WorldEntity {

    /**
     * The Box2D Body representing this WorldEntity in the World.
     * The Body's position is also the position of this WorldEntity.
     */
    protected final Body body;

    /**
     * The x and y dimensions of this WorldEntity.
     */
    private Vector2 size;

    /**
     * The offset between this WorldEntity's origin and one of its corners.
     * <p>
     * Two opposing corners of this WorldEntity are
     * position + offset
     * position + offset + size
     */
    private Vector2 offset;

    private Sprite sprite;

    public final UUID id;

    protected WorldEntity(Body body) {
        this.body = body;
        body.setUserData(this);

        this.size = new Vector2();
        this.offset = new Vector2();

        this.id = UUID.randomUUID();
    }


    // TODO copy returned vector to keep our vector private
    public Vector2 getPosition() {
        return this.body.getPosition();
    }

    public void setPosition(Vector2 position) {
        this.body.getPosition().set(position);
    }

    // TODO copy returned vector to keep our vector private
    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size.set(size);
    }

    // TODO copy returned vector to keep our vector private
    public Vector2 getOffset() {
        return offset;
    }

    public void setOffset(Vector2 offset) {
        this.offset.set(offset);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Body getBody() {
        return body;
    }

    public UUID getId() {
        return id;
    }
}
