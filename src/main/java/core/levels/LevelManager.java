package core.levels;

import static core.worldEntities.DemoSpawners.createEnemySpawner;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import core.input.AIInputDevice;
import core.input.KeyboardInputDevice;
import core.worldEntities.Spawner;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Use-Case class for LevelState.
 * Implements methods for advancing the level's World.
 *
 */
public class LevelManager {

    private ActiveLevel level;
    private WorldEntityManager entityManager;

    /**
     * Converts the given saveLevel into an activeLevel
     * @param savedLevel has all the information for the activeLevel
     */
    public void initializeLevel(SavedLevel savedLevel) {
        this.level =
            new ActiveLevel(
                savedLevel.getCurrentTime(),
                savedLevel.getScore(),
                savedLevel.getSpawnInterval(),
                savedLevel.getNextSpawnTime(),
                savedLevel.getMapPath()
            );
        this.level.setEnemySpawns(createEnemyList(savedLevel.getTotalSpawns()));
        this.entityManager = new WorldEntityManager(level.world);
        List<Spawner<Character>> enemiesUpdated = level.getEnemySpawns();

        // assign all enemies to an entityManager
        for (Spawner<Character> spawner : enemiesUpdated) {
            spawner.setEntityManager(this.entityManager);
        }
        this.level.setEnemySpawns(enemiesUpdated);
    }

    /**
     * Adds callback to assign spawned GameCharacter entities to a CharacterManager,
     * and sets their InputDevice type according to their `team` property
     *
     * @param characterManager the CharacterManager to add the created GameCharacters to
     */
    public void addGameCharacterRegistrationCallbacks(CharacterManager characterManager) {
        for (Spawner<?> spawner : level.getEnemySpawns()) {
            if (spawner.type.equals(Character.class)) {
                spawner.addSpawnCallback(e -> {
                    if (e instanceof Character character) {
                        if (character.getTeam().equals("player")) {
                            characterManager.setInputDeviceType(character.getId(), KeyboardInputDevice.class);
                        } else if (character.getTeam().equals("enemy")) {
                            characterManager.setInputDeviceType(character.getId(), AIInputDevice.class);
                        }
                    }
                });
            }
        }
    }

    /**
     * Steps the physics simulation of the level's World.
     * Elapses time in the level, and spawns enemies as necessary.
     * <p>
     * Above actions only performed if level isn't in a paused state
     *
     * @param dt time delta to simulate (seconds) (capped at .5 in case computer is too slow)
     */
    public void step(float dt) {
        // If level isn't paused, perform step, time elapsing, spawning
        if (level.getLevelPaused()) {
            return;
        }

        // Stepping physics simulation
        level.world.step(Math.min(dt, 0.5f), 6, 2);

        // Elapsing time in world
        level.setCurrentTime(level.getCurrentTime() + dt);

        // Spawning enemies in world
        updateEnemies();
    }

    /**
     * Periodically spawn enemies into the world, based on spawnTime given in LevelState
     * If all enemies spawned, check if game has been won
     */
    private void updateEnemies() {
        // Spawning enemies in world
        List<Spawner<Character>> enemies = level.getEnemySpawns();

        if (enemies.isEmpty()) {
            // If all enemies have been spawned, check if game is won or not
            level.finishedLevel();
            // if (checkWin()){showWinCondition();} // TODO Roy will implement showWinCondition()
            return;
        }

        // Spawn enemy every spawnTime amount of seconds, removing Spawner from enemySpawner
        if (level.getCurrentTime() >= level.getNextSpawnTime()) {
            Spawner<Character> enemy = enemies.remove(0);
            enemy.spawn();
            level.setEnemySpawns(enemies);
            level.setScore(level.getScore() + 1);
            level.setNextSpawnTime(level.getNextSpawnTime() + level.getSpawnInterval());
        }
    }

    /**
     * Creates list of enemies to be spawned in level
     *
     * @param numOfEnemies wanted to be spawned in this level
     * @return enemies list
     */
    private List<Spawner<Character>> createEnemyList(int numOfEnemies) {
        List<Spawner<Character>> enemies = new ArrayList<>();
        for (int i = 0; i < numOfEnemies; i++) {
            Spawner<Character> enemySpawner = createEnemySpawner();
            enemySpawner.addSpawnCallback(character -> character.setTeam("enemy"));
            enemies.add(enemySpawner);
        }
        return enemies;
    }

    /**
     * Checking win condition
     * level is won if 100 seconds have passed
     *
     * @return whether win condition has been met or not
     */
    public boolean checkWin() {
        return level.isLevelFinished();
    }

    public float getUnitScale() {
        return level.getUnitScale();
    }

    public WorldEntityManager getEntityManager() {
        return entityManager;
    }

    protected ActiveLevel getActiveLevel() {
        return this.level;
    }

    public void dispose() {
        level.getMap().dispose();
    }

    public void pause() {
        level.setLevelPaused(true);
    }

    public void resume() {
        level.setLevelPaused(false);
    }

    public boolean isLevelPaused() {
        return level.levelPaused;
    }

    public int getTime() {
        return (int) Math.floor(level.getCurrentTime());
    }

    public World getWorld() {
        return this.level.getWorld();
    }

    public TiledMap getMap() {
        return this.level.getMap();
    }

    public float getLevelUnitScale() {
        return this.level.getUnitScale();
    }
}
