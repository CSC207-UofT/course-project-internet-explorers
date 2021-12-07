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

    public SavedLevel(ActiveLevel level){
        this.currentTime = level.getCurrentTime();
        this.score = level.getScore();
        this.spawnInterval = level.getSpawnInterval();
        this.mapPath = "maps/demo.tmx";
        this.totalSpawns = level.getEnemySpawns().size();
    }

    public String getMapPath() { return mapPath; }

    public Integer getTotalSpawns(){ return totalSpawns; }
}
