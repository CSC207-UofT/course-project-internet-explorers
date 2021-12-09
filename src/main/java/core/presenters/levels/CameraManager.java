package core.presenters.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import core.worldEntities.WorldEntityManager;
import java.util.UUID;
import java.util.function.Function;

public class CameraManager {

    protected final OrthographicCamera camera;
    protected final float unitScale;
    private final WorldEntityManager entityManager;
    private UUID subjectID;

    //    private boolean debugFreecam;

    public CameraManager(float unitScale, WorldEntityManager entityManager) {
        this.unitScale = unitScale;
        this.entityManager = entityManager;

        this.camera = new OrthographicCamera();
        //        this.debugFreecam = false;

        camera.position.set(getSubjectPosition());
        camera.zoom = unitScale;
    }

    public Vector3 getSubjectPosition() {
        if (subjectID == null) {
            return new Vector3();
        }

        Vector2 pos = entityManager.getEntity(subjectID).getPosition();
        return new Vector3(pos.x, pos.y, 0);
    }

    /**
     * Moves camera as needed to track the subject, then update the camera.
     *
     * @param dt time since last frame in seconds
     */
    public void update(float dt) {
        step(dt);
        camera.update();
    }

    public void step(float dt) {
        Vector3 dp = getSubjectPosition().sub(camera.position);
        // movement smoothing function : decide how far to move camera based on how far the subject is
        Function<Float, Float> f = x ->
            (float) Math.round((x - Math.tanh(x) + 0.1 * Math.tanh(10 * x)) / unitScale * 1.5f) * unitScale / 1.5f;
        camera.translate(dp.scl(f.apply(dp.len2()) * 60 * dt));
    }

    public void syncCameraViewportToWindow() {
        camera.viewportWidth = Gdx.graphics.getWidth();
        camera.viewportHeight = Gdx.graphics.getHeight();
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Tells this CameraManager to track a new WorldEntity, and snaps the camera to its position.
     */
    public void setSubjectID(UUID id) {
        this.subjectID = id;
        this.camera.position.set(getSubjectPosition());
    }
}
