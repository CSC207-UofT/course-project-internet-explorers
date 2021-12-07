package core.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
import java.util.HashMap;
import java.util.List;

import static core.worldEntities.DemoSpawners.createEnemySpawner;

/**
 * Use-Case class for LevelState.
 * Implements methods for rendering level and advancing the level's World.
 */
public class LevelManager {

    private ActiveLevel level;
    // private final TiledMap map;
//    private final OrthogonalTiledMapRenderer mapRenderer;
    private WorldEntityManager entityManager;
//    private final SpriteBatch batch;
//    protected HashMap<String, Integer> levelDifficultyToEnemies;
//    protected List<Spawner<Character>> enemySpawns;


    public void initializeLevel(SavedLevel savedLevel) throws IOException {
        this.level = new ActiveLevel(savedLevel); // create active level
        this.level.setEnemySpawns(createEnemyList(savedLevel.getTotalSpawns()));
        this.entityManager = new WorldEntityManager(level.world); // activelevel
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

        // Spawn enemy every spawnTime amount of seconds
        if (level.getCurrentTime() >= level.getSpawnInterval()) {
            Spawner<Character> enemy = enemies.remove(0);
            enemy.spawn();
            level.setEnemySpawns(enemies);
            level.setScore(level.getScore() + 1);
            level.setSpawnInterval(level.getSpawnInterval() + 15);
        }
    }

    /**
     * Create HashMap holding levelDifficulty as key, and number of enemies that correspond to
     * that levelDifficulty, as value
     *
     * @return HashMap containing levelDifficulty mapped to number of enemies
     */
    private static HashMap<String, Integer> createLevelDifficultyToEnemies() {
        HashMap<String, Integer> my_dict = new HashMap<>();
        for (int i=0; i<10; i++){
            my_dict.put("L" + (i+5), i+5);
        }
        return my_dict;
    }

    /**
     * Creates list of enemies to be spawned in level
     * @param numOfEnemies wanted to be spawned in this level
     * @return enemies list
     */
    private List<Spawner<Character>> createEnemyList(int numOfEnemies) {
        List<Spawner<Character>> enemies = new ArrayList<>();
        // if time = 0, then populate enemySpawns with all enemies set for the levelDifficulty
//        if (level.getCurrentTime() == 0) {
            for (int i = 0; i < numOfEnemies; i++) {
                Spawner<Character> enemySpawner = createEnemySpawner();
                enemySpawner.addSpawnCallback(character -> character.setTeam("enemy"));
                enemies.add(enemySpawner);
            }
//        }
        // if time != 0, then adjust enemySpawns to remove enemies already defeated from last saved state
//        else{
//            double numEnemies = Math.floor(level.getCurrentTime() / level.getSpawnInterval());
//            for (int i = 0; i < numEnemies; i++) {
//                enemies = level.getEnemySpawns();
//                enemies.remove(0);
//                level.setEnemySpawns(enemies);
//            }
//        }
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

    /**
     * Save current time elapsed from level
     * This method will be used in phase 2
     *
     * @throws IOException            relating to savedState.txt
     */
    public void saveState(String fileName) throws IOException {
        // Save needed level information to file
        FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        // when loading ActiveLevel from SavedLevel, ActiveLevel makes a spawn list of length Savedlevel.totalSpawns
        // when an enemy is spawned in LevelManager, it removes a Spawner from ActiveLevel.enemySpawns
        // when saving the level, aka making a SavedLevel out of ActiveLevel:
        //    * assign SavedLevel.totalSpawns to length(ActiveLevel.enemySpawns)
        //    * update SavedLevel.currentTime to ActiveLevel.currentTime
        //    * assign SavedLevel.spawnInterval to ActiveLevel.spawnInterval
        //    * assign SavedLevel.mapPath to same'ol mapPath
        //    * score doesn't matter....?

        SavedLevel level = new SavedLevel(this.level);

        objectOutputStream.writeObject(level);


        objectOutputStream.flush();
        objectOutputStream.close();
    }

//    public void renderMap(OrthographicCamera camera) {
//        mapRenderer.setView(camera);
//        mapRenderer.render();
//    }
//
//    public void renderWorld(OrthographicCamera camera) {
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//        entityManager.draw(batch);
//        batch.end();
//    }

    /**
     * Invoke `render` on a Box2DDebugRenderer to draw the physics going on in this world.
     * Used for debugging.
     */
    public void renderPhysics(Box2DDebugRenderer renderer, OrthographicCamera camera) {
        renderer.render(level.world, camera.combined);
    }

    public float getUnitScale() {
        return level.getUnitScale();
    }

    public WorldEntityManager getEntityManager() {
        return entityManager;
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

    public World getWorld(){
        return this.level.getWorld();
    }

    public TiledMap getMap(){
        return this.level.getMap();
    }

    public float getLevelUnitScale(){
        return this.level.getUnitScale();
    }
}
