package characterTests;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import core.input.AIInputDevice;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.CharacterManager;
import core.worldEntities.types.characters.GameCharacter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestWorldEntityManager {

    /*
     * Tests the CharacterManager use case class
     * */
    World world;
    WorldEntityManager entityManager;
    BodyDef def;
    Body body;
    FixtureDef fixture;

    GameCharacter player1;
    GameCharacter player2;
    GameCharacter player3;
    ArrayList<GameCharacter> players;
    AIInputDevice aIInputDevice;

    @Before
    public void setup() {
        world = new World(new Vector2(0, 0), true);
        entityManager = new WorldEntityManager(world);


        def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        fixture = new FixtureDef();

        player1 = new GameCharacter();
        player2 = new GameCharacter();
        player3 = new GameCharacter();

        aIInputDevice = new AIInputDevice();

        ArrayList<GameCharacter> players = new ArrayList<GameCharacter>(){};
        players.add(player1);
        players.add(player2);
        players.add(player3);
    }

    @AfterEach
    public void teardown() {
        world.dispose();
    }

    @Test
    public void testRegister() {

        for (GameCharacter player : players) {
            entityManager.register(player, def, aIInputDevice.getClass(), fixture);
        }

        assertEquals(entityManager.getNumEntities(), 3);
    }

    @Test
    public void testFailedRegister() {
        // Should add another form of entity to test this
    }
}


