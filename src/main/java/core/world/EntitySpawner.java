package core.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Command class used to spawn WorldEntities.
 */
public class EntitySpawner {

    private WorldManager worldManager;

    private Supplier<BodyDef> bodyDefSupplier;
    private Supplier<FixtureDef[]> fixtureDefsSupplier;
    private Supplier<WorldEntityGeometry> geometrySupplier;
    private Supplier<Sprite> spriteSupplier;

    private Consumer<WorldEntity> spawnCallback;

    /**
     * Utility class used to specify position, size, and offset for new WorldEntities.
     */
    public static class WorldEntityGeometry {

        public Vector2 position;
        public Vector2 size;
        public Vector2 offset;

        public WorldEntityGeometry() {
            this.position = new Vector2();
            this.size = new Vector2();
            this.offset = new Vector2();
        }

        public WorldEntityGeometry(Vector2 position, Vector2 size, Vector2 offset) {
            this.position = position;
            this.size = size;
            this.offset = offset;
        }
    }

    /**
     * Creates a rectangular box shaped fixture using the passed in size as width/height.
     */
    public static Supplier<FixtureDef[]> createRectangularFixtureDefSupplier(Vector2 size) {
        return () -> {
            FixtureDef def = new FixtureDef();
            PolygonShape rectangle = new PolygonShape();
            rectangle.setAsBox(size.x / 2, size.y / 2);

            def.shape = rectangle;

            return new FixtureDef[] { def };
        };
    }

    public EntitySpawner() {
        bodyDefSupplier = BodyDef::new;
        fixtureDefsSupplier = () -> new FixtureDef[] {};
        geometrySupplier = WorldEntityGeometry::new;
        spriteSupplier = () -> null;
    }

    public WorldEntity spawn() {
        BodyDef bodyDef = bodyDefSupplier.get();
        WorldEntityGeometry geometry = geometrySupplier.get();

        bodyDef.position.set(geometry.position);

        WorldEntity entity = worldManager.createEntity(bodyDef, fixtureDefsSupplier.get());

        entity.setSize(geometry.size);
        entity.setOffset(geometry.offset);
        entity.setSprite(spriteSupplier.get());

        spawnCallback.accept(entity);

        return entity;
    }


    public void setWorldManager(WorldManager worldManager) {
        this.worldManager = worldManager;
    }

    public void setBodyDefSupplier(Supplier<BodyDef> bodyDefSupplier) {
        this.bodyDefSupplier = bodyDefSupplier;
    }

    public void setFixtureDefsSupplier(Supplier<FixtureDef[]> supplier) {
        this.fixtureDefsSupplier = supplier;
    }

    public void geometrySupplier(Supplier<WorldEntityGeometry> supplier) {
        this.geometrySupplier = supplier;
    }

    public void setSpriteSupplier(Supplier<Sprite> supplier) {
        this.spriteSupplier = supplier;
    }

    public void setSpawnCallback(Consumer<WorldEntity> callback) {
        this.spawnCallback = callback;
    }
}
