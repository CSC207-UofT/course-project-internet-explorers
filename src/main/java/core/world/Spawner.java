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

    // Utility Methods

    /**
     * Creates a rectangular box shape based on a given Sprite.
     */
    public static PolygonShape createRectangleFromSprite(Sprite sprite) {
        PolygonShape rectangle = new PolygonShape();
        float hx = sprite.getWidth() / 2;
        float hy = sprite.getHeight() / 2;

        Vector2 origin = new Vector2(hx - sprite.getOriginX(), hy - sprite.getOriginY());

        rectangle.setAsBox(hx, hy, origin, sprite.getRotation() / 57.29578f);

        return rectangle;
    }

    //    public static <K> Spawner<K extends WorldEntityWithSprite> createSpriteBasedEntitySpawner(Class<? extends WorldEntityWithSprite>, Vector2 position, Sprite sprite) {
    //
    //    }

    public static <K extends WorldEntityWithSprite> Spawner<K> createSpriteBasedEntitySpawner(
        Class<K> type,
        Vector2 position,
        Sprite sprite
    ) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        return createSpriteBasedEntitySpawner(type, bodyDef, sprite);
    }

    public static <K extends WorldEntityWithSprite> Spawner<K> createSpriteBasedEntitySpawner(
        Class<K> type,
        BodyDef bodyDef,
        Sprite sprite
    ) {
        Spawner<K> spawner = new Spawner<>(type);
        spawner.setBodyDefSupplier(() -> bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape rectangle = createRectangleFromSprite(sprite);
        fixtureDef.shape = rectangle;
        spawner.setFixtureDefsSupplier(() -> new FixtureDef[] { fixtureDef });

        spawner.setSpawnCallback(e -> {
            e.setSprite(sprite);
            rectangle.dispose();
        });

        return spawner;
    }
}