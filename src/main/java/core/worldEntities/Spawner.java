package core.worldEntities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Command class used to spawn WorldEntities.
 */
public class Spawner<T extends WorldEntity> implements Serializable {

    /**
     * The EntityManager with which to register the spawned WorldEntity.
     */
    private WorldEntityManager entityManager;

    // Must pass in the type T, can't use the class type annotation directly
    public final Class<T> type;

    /**
     * Supplier functions for the WorldEntity's Box2D Body and Fixture definitions.
     */
    private Supplier<BodyDef> bodyDefSupplier;
    private Supplier<FixtureDef[]> fixtureDefsSupplier;

    /**
     * Callbacks which take the spawned WorldEntity to perform additional configuration.
     * Callbacks are invoked in ArrayList order.
     */
    private final ArrayList<Consumer<T>> spawnCallbacks;

    public Spawner(Class<T> type) {
        this.type = type;
        bodyDefSupplier = BodyDef::new;
        fixtureDefsSupplier = () -> new FixtureDef[] {};

        spawnCallbacks = new ArrayList<>();
    }

    /**
     * Create and register the WorldEntity specified by this Spawner.
     *
     * @return the created WorldEntity
     */
    public WorldEntity spawn() {
        T entity = entityManager.createEntity(type, bodyDefSupplier.get(), fixtureDefsSupplier.get());

        spawnCallbacks.forEach(c -> c.accept(entity));

        return entity;
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

    public void addSpawnCallback(Consumer<T> callback) {
        this.spawnCallbacks.add(callback);
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

        spawner.addSpawnCallback(e -> {
            e.setSprite(sprite);
            rectangle.dispose();
        });

        return spawner;
    }
}
