package core.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Command class used to spawn WorldEntities.
 */
public class Spawner<T extends WorldEntity> {

    private WorldEntityManager entityManager;
    private final Class<T> type;

    private Supplier<BodyDef> bodyDefSupplier;
    private Supplier<FixtureDef[]> fixtureDefsSupplier;

    private Consumer<T> spawnCallback;

    // TODO add more utility methods for basic shapes and bodies

    /**
     * Creates a rectangular box shaped fixture using the passed in size as width/height.
     */
    public static Supplier<FixtureDef[]> createRectangularFixtureDefSupplier(Sprite sprite) {
        return () -> {
            FixtureDef def = new FixtureDef();
            PolygonShape rectangle = new PolygonShape();
            float hx = sprite.getWidth() / 2;
            float hy = sprite.getHeight() / 2;

            Vector2 origin = new Vector2(hx - sprite.getOriginX(), hy - sprite.getOriginY());

            rectangle.setAsBox(hx, hy, origin, 0f);

            def.shape = rectangle;
            //            rectangle.setAsBox(size.x/2, size.y/2, new Vector2(-size.x, -size.y), 0f);

            return new FixtureDef[] { def };
        };
    }

    public Spawner(Class<T> type) {
        this.type = type;
        bodyDefSupplier = BodyDef::new;
        fixtureDefsSupplier = () -> new FixtureDef[] {};

        spawnCallback = e -> {};
    }

    public WorldEntity spawn() {
        BodyDef bodyDef = bodyDefSupplier.get();

        try {
            T entity = type
                .getConstructor(WorldEntityManager.class, BodyDef.class, FixtureDef[].class)
                .newInstance(entityManager, bodyDef, fixtureDefsSupplier.get());

            spawnCallback.accept(entity);

            return entity;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to spawn a WorldEntity of type " + type + ".");
        }
    }

    public void setEntityManager(WorldEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setBodyDefSupplier(Supplier<BodyDef> bodyDefSupplier) {
        this.bodyDefSupplier = bodyDefSupplier;
    }

    public void setFixtureDefsSupplier(Supplier<FixtureDef[]> supplier) {
        this.fixtureDefsSupplier = supplier;
    }

    public void setSpawnCallback(Consumer<T> callback) {
        this.spawnCallback = callback;
    }
}
