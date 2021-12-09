package core.worldEntities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import core.worldEntities.types.characters.Character;
import core.worldEntities.types.damageDealers.DefenderDamage;
import java.util.ArrayList;

public class SpawnerFactory {

    private final TextureAtlas textureAtlas;
    public final String DEMO_TEXTURE_ATLAS_PATH = "characters/sprites.txt";
    public static final String DEMO_PLAYER_SPRITE_NAME = "demo_player";
    public static final String DEMO_SPIKE_SPRITE_NAME = "spike";
    public static final String DEMO_ENEMY_SPRITE_NAME = "demo_enemy";
    public static final String DEMO_DEFENSE_SPRITE_NAME = "demo_defense";

    public SpawnerFactory() {
        this.textureAtlas = new TextureAtlas(DEMO_TEXTURE_ATLAS_PATH);
    }

    public SpawnerFactory(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public Spawner<Character> createPlayerSpawner(ArrayList<Float> playerPosition) {
        Vector2 position = new Vector2(playerPosition.get(0), playerPosition.get(1));
        Sprite sprite = textureAtlas.createSprite(DEMO_PLAYER_SPRITE_NAME);
        sprite.setSize(1, 1);
        sprite.setOriginCenter();

        return Spawner.createSpriteBasedEntitySpawner(Character.class, position, sprite);
    }


    public Spawner<Character> createEnemySpawner() {
        Vector2 position = new Vector2(20, 8);
        Sprite sprite = textureAtlas.createSprite(DEMO_ENEMY_SPRITE_NAME);
        sprite.setSize(1, 1);
        sprite.setOriginCenter();

        return Spawner.createSpriteBasedEntitySpawner(Character.class, position, sprite);
    }

    public Spawner<Character> loadEnemySpawner(ArrayList<Float> savedPosition) {
        Vector2 position = new Vector2(savedPosition.get(0), savedPosition.get(1));
        Sprite sprite = textureAtlas.createSprite(DEMO_ENEMY_SPRITE_NAME);
        sprite.setSize(1, 1);
        sprite.setOriginCenter();

        return Spawner.createSpriteBasedEntitySpawner(Character.class, position, sprite);
    }


    public static Spawner<DefenderDamage> createDefenseSpawner(ArrayList<Float> position) {
        Sprite sprite = new Sprite(new Texture("weapons/defender.png"));
        sprite.setSize(1, 1);
        sprite.setOriginCenter();

        return Spawner.createSpriteBasedEntitySpawner(DefenderDamage.class, new Vector2(position.get(0), position.get(1)), sprite);
    }

    public static Spawner<WorldEntity> createMapBorderSpawner() {
        Spawner<WorldEntity> spawner = new Spawner<>(WorldEntity.class);

        spawner.setBodyDefSupplier(() -> {
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            return def;
        });

        // map vertices
        Vector2[] mapVertices = new Vector2[] { new Vector2(0, 0), new Vector2(0, 10), new Vector2(30, 10), new Vector2(30, 0) };

        int vertices = mapVertices.length;
        FixtureDef[] fixtureDefs = new FixtureDef[vertices];

        for (int i = 0; i < vertices; i++) {
            fixtureDefs[i] = new FixtureDef();

            EdgeShape edge = new EdgeShape();
            edge.setVertex0(mapVertices[i]);
            edge.set(mapVertices[(i + 1) % vertices], mapVertices[(i + 2) % vertices]);
            edge.setVertex3(mapVertices[(i + 3) % vertices]);

            fixtureDefs[i].shape = edge;
            fixtureDefs[i].restitution = 0.2f;
        }

        spawner.setFixtureDefsSupplier(() -> fixtureDefs);

        // dispose of shapes used for fixtures *after* entity has been spawned
        spawner.addSpawnCallback(e -> {
            for (FixtureDef def : fixtureDefs) {
                def.shape.dispose();
            }
        });

        return spawner;
    }
}
