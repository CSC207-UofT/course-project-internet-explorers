package core.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
import java.util.List;

/**
 * Use-Case class for LevelState.
 * Implements methods for rendering level and advancing the level's World.
 */
public class LevelManager {

    private final LevelState level;
    private final TiledMap map;
    private final WorldEntityManager entityManager;

    public LevelManager(LevelState level) {
        this.level = level;
        map = new TmxMapLoader().load(level.getMapPath());
        this.entityManager = new WorldEntityManager(level.world);



        // assign all enemies to current entityManager
        List<Spawner<Character>> enemiesUpdated = level.getEnemySpawns();
        for (Spawner<Character> spawner : enemiesUpdated) {
            spawner.setEntityManager(this.entityManager);
        }
        level.setEnemySpawns(enemiesUpdated);
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
                            characterManager.addCharacter(character.id, KeyboardInputDevice.class);
                        } else if (character.getTeam().equals("enemy")) {
                            characterManager.addCharacter(character.id, AIInputDevice.class);
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

        // Spawn enemy every spawnTime amount of seconds
        if (level.getCurrentTime() >= level.getSpawnTime()) {
            Spawner<Character> enemy = enemies.remove(0);
            enemy.spawn();
            level.setEnemySpawns(enemies);
            level.setScore(level.getScore() + 1);
            level.setSpawnTime(level.getSpawnTime() + 15);
        }
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

    /**
     * Save current time elapsed from level
     * This method will be used in phase 2
     *
     * @throws IOException            relating to savedState.txt
     */
    public void saveState() throws IOException {
        // Save needed level information to file
        FileOutputStream fileOutputStream = new FileOutputStream("savedState.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeFloat(level.getCurrentTime());

        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public float getUnitScale() {
        return level.getUnitScale();
    }

    public WorldEntityManager getEntityManager() {
        return entityManager;
    }

    public void dispose() {
        map.dispose();
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

    public TiledMap getMap() {
        return map;
    }

    public float getLevelUnitScale(){
        return level.getUnitScale();
    }

    public World getWorld(){
        return level.world;
    }
}
