package core.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
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
import java.util.*;

/**
 * Use-Case class for LevelState.
 * Implements methods for advancing the level's World.
 */
public class LevelManager {

    private LevelState activeLevel;
    private TiledMap map;
    private WorldEntityManager entityManager;
    private final TreeSet<LevelEvent> levelEvents = new TreeSet<>();
    static final int SPAWN_FREQUENCY = 15;

    // TODO REMOVE (THIS IS FOR TESTING, LOAD TEST LEVELS ONCE POSSIBLE)
    public void initializeEmptyLevel() {
        this.entityManager = new WorldEntityManager(new World(new Vector2(), true));
    }

    // TODO use level loader to load appropriate level once implemented
    public void initializeLevel(String name) {
        this.activeLevel = LevelLoader.getLevel1();
        map = new TmxMapLoader().load(activeLevel.getMapPath());
        this.entityManager = new WorldEntityManager(activeLevel.world);

        // assign all enemies to current entityManager
        List<Spawner<Character>> enemiesUpdated = activeLevel.getEnemySpawns();
        for (Spawner<Character> spawner : enemiesUpdated) {
            spawner.setEntityManager(this.entityManager);
        }
        activeLevel.setEnemySpawns(enemiesUpdated);
    }

    /**
     * Adds callback to assign spawned GameCharacter entities to a CharacterManager,
     * and sets their InputDevice type according to their `team` property
     *
     * @param characterManager the CharacterManager to add the created GameCharacters to
     */
    public void addGameCharacterRegistrationCallbacks(CharacterManager characterManager) {
        for (Spawner<?> spawner : activeLevel.getEnemySpawns()) {
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
        if (activeLevel.getLevelPaused()) {
            return;
        }

        runLevelEvents(this.levelEvents);

        // Step physics simulation with box2d default velocity and position iterations
        activeLevel.world.step(Math.min(dt, 0.5f), 6, 2);

        // Elapsing time in world
        activeLevel.setCurrentTime(activeLevel.getCurrentTime() + dt);

        // Spawning enemies in world
        updateEnemies();
    }

    private void runLevelEvents(TreeSet<LevelEvent> events) {
        // Get level events to run
        NavigableSet<LevelEvent> eventsToRun = events.headSet(new LevelEvent(activeLevel.getCurrentTime(), o -> {}), true);
        // invoke event callback on each event
        eventsToRun.forEach((levelEvent -> levelEvent.getEventCallback().accept(this)));
        // clear original set of events that have been run
        eventsToRun.clear();
    }

    public void addLevelEvent(LevelEvent event) {
        levelEvents.add(event);
    }

    /**
     * Periodically spawn enemies into the world, based on spawnTime given in LevelState
     * If all enemies spawned, check if game has been won
     */
    private void updateEnemies() {
        // Spawning enemies in world
        List<Spawner<Character>> enemies = activeLevel.getEnemySpawns();

        if (enemies.isEmpty()) {
            // If all enemies have been spawned, check if game is won or not
            activeLevel.finishedLevel();
            // if (checkWin()){showWinCondition();} // TODO Roy will implement showWinCondition()
            return;
        }

        // Spawn enemy every spawnTime amount of seconds
        if (activeLevel.getCurrentTime() >= activeLevel.getSpawnTime()) {
            Spawner<Character> enemy = enemies.remove(0);
            enemy.spawn();
            activeLevel.setEnemySpawns(enemies);
            activeLevel.setScore(activeLevel.getScore() + 1);
            activeLevel.setSpawnTime(activeLevel.getSpawnTime() + SPAWN_FREQUENCY);
        }
    }

    /**
     * Checking win condition
     * level is won if 100 seconds have passed
     *
     * @return whether win condition has been met or not
     */
    public boolean checkWin() {
        return activeLevel.isLevelFinished();
    }

    /**
     * Save current time elapsed from level
     * This method will be used in phase 2
     *
     * @throws IOException relating to savedState.txt
     */
    public void saveState() throws IOException {
        // Save needed level information to file
        FileOutputStream fileOutputStream = new FileOutputStream("savedState.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeFloat(activeLevel.getCurrentTime());

        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public float getUnitScale() {
        return activeLevel.getUnitScale();
    }

    public WorldEntityManager getEntityManager() {
        return entityManager;
    }

    public void dispose() {
        map.dispose();
    }

    public void pause() {
        activeLevel.setLevelPaused(true);
    }

    public void resume() {
        activeLevel.setLevelPaused(false);
    }

    public boolean isLevelPaused() {
        return activeLevel.levelPaused;
    }

    public int getTime() {
        return (int) Math.floor(activeLevel.getCurrentTime());
    }

    public TiledMap getMap() {
        return map;
    }

    public float getLevelUnitScale() {
        return activeLevel.getUnitScale();
    }

    public World getWorld() {
        return activeLevel.world;
    }
}
