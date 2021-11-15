package world;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.world.WorldEntity;
import core.world.WorldManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestWorldEntity {

    WorldManager worldManager;
    World world;

    @BeforeEach
    void setup() {
        world = new World(new Vector2(), true);
        worldManager = new WorldManager(world);
    }

    @AfterEach
    void teardown() {
        world.dispose();
    }

    @Test
    void testConstructor() {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;

        WorldEntity entity = new WorldEntity(worldManager, def);

        assertNotNull(entity.id, "Entity UUID was not set.");
        assertNotNull(worldManager.getEntity(entity.id), "WorldManager can't get the entity by UUID.");

        assertAll(
            "Default position, size, and offset were all set to 0.",
            () -> assertEquals(0f, entity.getPosition().x),
            () -> assertEquals(0f, entity.getPosition().y)
        );
    }

    @Test
    void testConstructorWithFixture() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        CircleShape circle = new CircleShape();
        circle.setRadius(1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;

        WorldEntity entity = new WorldEntity(worldManager, bodyDef, fixtureDef);

        circle.dispose();

        assertEquals(1, world.getFixtureCount());
    }

    @Test
    void testConstructorWithFixtures() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        CircleShape circle = new CircleShape();
        circle.setRadius(1);
        PolygonShape square = new PolygonShape();
        square.setAsBox(2, 2);

        FixtureDef fixtureDef1 = new FixtureDef();
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef1.shape = circle;
        fixtureDef2.shape = square;

        WorldEntity entity = new WorldEntity(worldManager, bodyDef, fixtureDef1, fixtureDef2);

        circle.dispose();
        square.dispose();

        assertEquals(2, world.getFixtureCount());
    }
}
