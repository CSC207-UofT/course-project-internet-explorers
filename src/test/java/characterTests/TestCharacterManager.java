package characterTests;

import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.entities.WorldEntityManager;
import core.entities.types.characters.CharacterManager;
import core.entities.types.characters.GameCharacter;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;

public class TestCharacterManager {

    /*
     * Tests the CharacterManager use case class
     * */
    World world;
    CharacterManager cm;

    GameCharacter player1;
    GameCharacter player2;
    GameCharacter player3;

    @Before
    public void setup() {
        world = new World(new Vector2(0, 0), true);
        WorldEntityManager entityManager = new WorldEntityManager(world);
        cm = new CharacterManager(entityManager);

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;

        player1 = new GameCharacter(entityManager, def);
        player2 = new GameCharacter(entityManager, def);
        player3 = new GameCharacter(entityManager, def);
    }

    @AfterEach
    public void teardown() {
        world.dispose();
    }

    @org.junit.Test
    public void testAddCharacter() {
        cm.addCharacter(player1.id);
        cm.addCharacter(player2.id);
        cm.addCharacter(player3.id);

        assertEquals(3, cm.characterEntities.size());
    }
}
