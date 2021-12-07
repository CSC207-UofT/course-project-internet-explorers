package core.levels;

public class SavedLevel extends LevelState {

    private final String mapPath;
    private int totalSpawns;


    public SavedLevel(String mapPath, int totalSpawns, float spawnInterval) {
        this.currentTime = 0;
        this.score = 0;
        this.spawnInterval = spawnInterval;
        this.mapPath = mapPath;
        this.totalSpawns = totalSpawns;
    }

    public String getMapPath() { return mapPath; }

    public Integer getTotalSpawns(){ return totalSpawns; }

}
