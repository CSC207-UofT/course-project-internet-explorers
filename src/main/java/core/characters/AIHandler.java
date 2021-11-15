package core.characters;

import com.badlogic.gdx.physics.box2d.World;
import java.util.Objects;
import java.util.UUID;

public class AIHandler extends InputHandler {

    /*
     * Handles the movement of the defenders and enemies in the game
     * @param manager: the CharacterManager allowing AIHandler to pass inputs to entities
     * */

    public AIHandler(CharacterManager manager) {
        super(manager);
    }

    /*
     * TODO: Talk to level manager to know when a new level starts to initialize movement
     * */

    @Override
    public void checkInputMovement(UUID id) {
        if (Objects.equals(manager.characterEntities.get(id).getTeam(), "Enemy")) {
            int dx = 0;
            int dy = 0;

            // Enemy will only move left
            dx -= 1;

            manager.updateCharacterPosition(id, dx, dy);
        }
    }

    /*
     * Increases the level of the AI following the completion of a wave
     * */
    public void increaseLevel(UUID id, boolean cleared) {
        if (cleared) {
            manager.updateLevel(id);
        }
    }

    /*
     * Decreases health of enemy when hit
     * TODO: Talk to collisions (?)
     * Check if the current position coincides with the position of another world entity
     * Then check if its damaging, then update damage - violate clean architecture?
     * */
    public void depleteHealth(UUID id, int damage) {
        if (true) {
            manager.updateHealth(id, damage);
        }
    }
}
