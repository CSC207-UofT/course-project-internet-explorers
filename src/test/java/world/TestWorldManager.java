package world;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.world.WorldEntity;
import core.world.WorldManager;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestWorldManager {

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

    static Stream<Vector2> positionProvider() {
        return Stream.of(
            new Vector2(0, 0),
            new Vector2(1, 1),
            new Vector2(-1, -1),
            new Vector2(0.5f, 0.3f),
            new Vector2(1.5f, 1.3f),
            new Vector2(2, 1),
            new Vector2(2, 2),
            new Vector2(-2, -2),
            new Vector2(-2.3f, -0.13f),
            new Vector2(-20.3f, 10.33f),
            new Vector2(131.41f, -80.313f),
            new Vector2(-231.621f, -380.313f),
            new Vector2(-431.442f, 680.313f),
            new Vector2(831.14f, 983.73f)
        );
    }

    @Test
    void testCreateEntity() {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;

        WorldEntity entity = worldManager.createEntity(def);

        assertNotNull(entity.id, "Entity UUID was not set.");
        assertNotNull(worldManager.getEntity(entity.id), "WorldManager can't get the entity by UUID.");
        assertEquals(entity.id, entity.getBody().getUserData(), "Entity body UserData object not set to its UUID.");

        assertAll(
            "Default position, size, and offset were all set to 0.",
            () -> assertEquals(0f, entity.getPosition().x),
            () -> assertEquals(0f, entity.getPosition().y),
            () -> assertEquals(0f, entity.getSize().x),
            () -> assertEquals(0f, entity.getSize().y),
            () -> assertEquals(0f, entity.getOffset().x),
            () -> assertEquals(0f, entity.getOffset().y)
        );
    }

    @Test
    void testCreateEntityWithFixture() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        CircleShape circle = new CircleShape();
        circle.setRadius(1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;

        WorldEntity entity = worldManager.createEntity(bodyDef, fixtureDef);

        circle.dispose();

        assertEquals(1, world.getFixtureCount());
        assertEquals(1, entity.getBody().getFixtureList().size);
    }

    @Test
    void testCreateEntityWithFixtures() {
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

        WorldEntity entity = worldManager.createEntity(bodyDef, fixtureDef1, fixtureDef2);

        circle.dispose();
        square.dispose();

        assertEquals(2, world.getFixtureCount());
        assertEquals(2, entity.getBody().getFixtureList().size);
    }
}
