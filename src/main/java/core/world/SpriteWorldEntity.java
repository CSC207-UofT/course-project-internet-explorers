package core.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteWorldEntity extends SizedWorldEntity implements Drawable {

    protected Sprite sprite;

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setSize(size.x, size.y);
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
