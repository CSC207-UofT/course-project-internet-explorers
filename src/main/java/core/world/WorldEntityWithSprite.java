package core.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class WorldEntityWithSprite extends WorldEntity {

    private Sprite sprite;
    private Vector2 offset;

    public WorldEntityWithSprite(WorldManager worldManager, BodyDef bodyDef, FixtureDef... fixtureDefs) {
        super(worldManager, bodyDef, fixtureDefs);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.offset = new Vector2(-sprite.getOriginX(), -sprite.getOriginY());
    }

    protected Sprite getSprite() {
        sprite.setRotation(body.getAngle() * 57.29578f);

        Vector2 pos = this.getPosition().add(offset);
        sprite.setPosition(pos.x, pos.y);

        return sprite;
    }
}
