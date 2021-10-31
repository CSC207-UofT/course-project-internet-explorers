package core.levels;

import com.badlogic.gdx.physics.box2d.World;
import java.util.Date;

public class LevelState {

    private String mapPath;
    protected World world;
    protected Date startTime;

    protected LevelState(String mapPath) {
        this.mapPath = mapPath;
    }

    public String getMapPath() {
        return mapPath;
    }
}
