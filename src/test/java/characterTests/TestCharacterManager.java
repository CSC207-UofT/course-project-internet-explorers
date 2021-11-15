package characterTests;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import core.characters.CharacterManager;
import core.characters.GameCharacter;
import org.junit.Before;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestCharacterManager {

    /*
    * Tests the CharacterManager use case class
    * */

    CharacterManager cm;
    GameCharacter player1;
    GameCharacter player2;
    GameCharacter player3;

    @Before
    public void setUp() {
        cm = new CharacterManager();

        World world = new World(new Vector2(0, 0), true);
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;

        Body body = world.createBody(def);
        player1 = new GameCharacter(body);
        player2 = new GameCharacter(body);
        player3 = new GameCharacter(body);
    }

    @org.junit.Test
    public void testAddCharacter() {
        cm.addCharacter(player1);
        cm.addCharacter(player2);
        cm.addCharacter(player3);

        assertEquals(3, cm.characterEntities.size());
    }
}
