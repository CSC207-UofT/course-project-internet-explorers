package core.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.World;
import core.config.Config;
import core.config.ConfigurableSetting;
import core.input.InputController;
import core.input.InputManager;
import core.worldEntities.Spawner;
import core.worldEntities.SpawnerFactory;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.collisions.CollisionBehaviour;
import core.worldEntities.health.DealsDamage;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.characters.CharacterManager;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Use-Case class for LevelState.
 * Implements methods for advancing the level's World.
 */
public class LevelManager {

    public static final ConfigurableSetting<String> selectedLevel = Config.add(
        String.class,
        "selected-level",
        "Name of the level to load & play when the Play button is clicked.",
        "Level 1",
        s -> s
    );

    public static final float WIN_CONDITION = 70;

    private ActiveLevel level;
    private WorldEntityManager entityManager;
    private final TreeSet<LevelEvent> levelEvents = new TreeSet<>();
    private final TmxMapLoader mapLoader;
    private final SpawnerFactory demoSpawnerFactory;

    public LevelManager(TmxMapLoader mapLoader, SpawnerFactory demoSpawnerFactory) {
        this.mapLoader = mapLoader;
        this.demoSpawnerFactory = demoSpawnerFactory;
    }

    public LevelManager() {
        this(new TmxMapLoader(), new SpawnerFactory());
    }

    /**
     * Converts the given saveLevel into an activeLevel
     *
     * @param savedLevel has all the information for the activeLevel
     */
    public void initializeLevel(SavedLevel savedLevel) {
        this.level =
            new ActiveLevel(
                savedLevel.getCurrentTime(),
                savedLevel.getScore(),
                savedLevel.getSpawnInterval(),
                savedLevel.getNextSpawnTime(),
                mapLoader.load(savedLevel.getMapPath())
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
    public void addGameCharacterRegistrationCallbacks(CharacterManager characterManager, InputManager inputManager) {
        for (Spawner<?> spawner : level.getEnemySpawns()) {
            if (spawner.type.equals(Character.class)) {
                spawner.addSpawnCallback(e -> {
                    if (e instanceof Character character) {
                        if (character.getTeam().equals("player")) {
                            characterManager.addCharacterInputMapping(
                                inputManager,
                                character.getId(),
                                InputController.keyboardInputDevice().characterInputProvider()
                            );
                        } else if (character.getTeam().equals("enemy")) {
                            characterManager.addCharacterInputMapping(
                                inputManager,
                                character.getId(),
                                InputController.aiInputDevice().characterInputProvider()
                            );
                            character.addCollisionBehaviour(
                                new CollisionBehaviour<>(
                                    Character.class,
                                    DealsDamage.class,
                                    (enemy, dealsDamage) ->
                                        this.addLevelEvent(
                                                new LevelEvent(getTime() + 0.01f, levelManager -> entityManager.deleteEntity(enemy.getId()))
                                            )
                                )
                            );
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

        runLevelEvents(this.levelEvents);

        // Stepping physics simulation
        level.world.step(Math.min(dt, 0.5f), 6, 2);
        // Elapsing time in world
        level.setCurrentTime(level.getCurrentTime() + dt);

        // Spawning enemies in world
        updateEnemies();
    }

    /**
     * Runs LevelEvents in the `events` set whose time is less than the current time,
     * and removes those events from the passed set.
     */
    private void runLevelEvents(TreeSet<LevelEvent> events) {
        // Get all level events whose `time` property is less than the current level time
        NavigableSet<LevelEvent> eventsToRun = events.headSet(new LevelEvent(level.getCurrentTime(), o -> {}), true);
        // invoke event callback on each event
        eventsToRun.forEach((levelEvent -> levelEvent.getEventCallback().accept(this)));
        // clear the events we just ran from both sets
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
        List<Spawner<Character>> enemies = level.getEnemySpawns();

        if (enemies.isEmpty() || level.getCurrentTime() > WIN_CONDITION) {
            // If all enemies have been spawned or you survive long enough you've won the game!
            level.finishedLevel();
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
            Spawner<Character> enemySpawner = demoSpawnerFactory.createEnemySpawner();
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

    public ActiveLevel getActiveLevel() {
        return this.level;
    }

    public void dispose() {
        level.getMap().dispose();
        level.getWorld().dispose();
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

    public float getTime() {
        return level.getCurrentTime();
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
