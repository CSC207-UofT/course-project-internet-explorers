package core.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class WorldEntityManager {

    private WorldEntity entity;
    private Body body;
    private Sprite sprite;

    public WorldEntityManager(WorldEntity entity) {
        this.entity = entity;
    }

    public WorldEntity getEntity() {
        return entity;
    }

    protected void setBody(Body body) {
        this.body = body;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void draw(SpriteBatch batch) {
        if (sprite != null) {
            sprite.setPosition(entity.position.x, entity.position.y);
            sprite.setSize(entity.size.x, entity.size.y);
            sprite.draw(batch);
        }
    }
}
