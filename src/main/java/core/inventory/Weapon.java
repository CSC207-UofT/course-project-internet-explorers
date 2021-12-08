package core.inventory;

import static core.worldEntities.DemoSpawners.createDefenseSpawner;

//TODO MAKE CLEAN - remove non-clean imports, create inventory manager, move use method
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import core.inventory.items.Defender;
import core.levels.LevelEvent;
import core.levels.LevelManager;
import core.worldEntities.Spawner;
import core.worldEntities.WorldEntityManager;
import core.worldEntities.health.Damage;
import core.worldEntities.types.damageDealers.CircularDamageRegion;
import java.util.UUID;

public abstract class Weapon implements Item {

    public static int DEFAULT_LEVEL = 1;

    private String texturePath;
    private int level;
    private Damage damage;
    private int range;
    public UUID id;

    public abstract Damage getDamage();

    public abstract int getLevel();

    public abstract void setLevel(int new_level);

    public abstract int getRange();

    public abstract UUID getID();
}
