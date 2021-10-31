package core.levels;

import com.badlogic.gdx.physics.box2d.World;
import java.util.Date;

public class LevelState {

    private String mapPath;
    protected World world;
    protected Date startTime;

    // unitScale measured in m/px
    // represents in-game size of map tiles
    // current conventions
    //  * 1 map tile is 1m by 1m
    //  * tiles are 32px by 32px
    private float unitScale;

    protected LevelState(String mapPath) {
        this.mapPath = mapPath;
    }

    protected void setUnitScale(float unitScale) {
        this.unitScale = unitScale;
    }

    public float getUnitScale() {
        return this.unitScale;
    }

    public String getMapPath() {
        return mapPath;
    }
}
