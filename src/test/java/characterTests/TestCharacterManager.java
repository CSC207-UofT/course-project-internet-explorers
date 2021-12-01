package characterTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import core.input.AIInputDevice;
import core.inventory.items.Sword;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.GameCharacter;
import core.worldEntities.types.characters.CharacterManager;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;

import java.util.ArrayList;

public class TestCharacterManager {

    /*
     * Tests the CharacterManager use case class
     * */
    World world;
    WorldEntityManager entityManager;
    CharacterManager cm;
    BodyDef def;
    Body body;
    FixtureDef fixture;

    GameCharacter player1;
    GameCharacter player2;
    GameCharacter player3;
    ArrayList<GameCharacter> players;
    AIInputDevice aIInputDevice;
    Sword sword;

    @Before
    public void setup() {
        world = new World(new Vector2(0, 0), true);
        entityManager = new WorldEntityManager(world);


        def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        fixture = new FixtureDef();

        player1 = new GameCharacter();
        player1.setHealth(100);
        sword = new Sword(2);
        player2 = new GameCharacter();
        player3 = new GameCharacter();

        aIInputDevice = new AIInputDevice();

        players = new ArrayList<GameCharacter>(){};
        players.add(player1);
        players.add(player2);
        players.add(player3);

        for (GameCharacter player : players) {
            entityManager.register(player, def, aIInputDevice.getClass(), fixture);
        }

        cm = new CharacterManager(entityManager);
    }

    @AfterEach
    public void teardown() {
        world.dispose();
    }

    @org.junit.Test
    public void testUpdateHealth() {

        cm.updateHealth(player1.getId(), 50);
        assertEquals(player1.getHealth(), 50);
    }

    @org.junit.Test
    public void testUpdateLevel() {

        cm.updateLevel(player1.getId());
        assertEquals(player1.getLevel(), 1);
    }

    @org.junit.Test
    public void testAddInventory() {

        cm.addInventory(player1.getId(), sword);
        assertEquals(player1.getInventory().size(), 1);
    }

    @org.junit.Test
    public void testCanUseItem() {

        assertTrue(cm.canUseItem(player1.getId(), sword));
    }

    @org.junit.Test
    public void testRemoveInventory() {

        assertTrue(cm.removeInventory(player1.getId(), sword));
    }


}
