package core.presenters.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import core.entities.WorldEntity;
import core.entities.WorldEntityManager;
import java.util.UUID;
import java.util.function.Function;

public class CameraManager {

    protected OrthographicCamera camera;
    protected float unitScale;
    private final WorldEntityManager entityManager;
    private UUID subjectID;
    private boolean debugFreecam;

    public CameraManager(float unitScale, WorldEntityManager entityManager) {
        this.unitScale = unitScale;
        this.entityManager = entityManager;

        this.camera = new OrthographicCamera();
        this.debugFreecam = false;

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
        if (debugFreecam) {
            handleFreecamInput(dt);
        }

        Vector3 dp = getSubjectPosition().sub(camera.position);
        // movement smoothing function – decide how far to move camera based on how far the subject is
        Function<Float, Float> f = x ->
            (float) Math.round((x - Math.tanh(x) + 0.1 * Math.tanh(10 * x)) / unitScale * 1.5f) * unitScale / 1.5f;
        camera.translate(dp.scl(f.apply(dp.len2()) * 60 * dt));
    }

    public void syncCameraViewportToWindow() {
        camera.viewportWidth = Gdx.graphics.getWidth();
        camera.viewportHeight = Gdx.graphics.getHeight();
        camera.update();
    }

    /**
     * Enable freely controlling the camera FOR DEBUG PURPOSES ONLY
     * Detaches camera from current subject by setting `subjectPosition` to a new vector.
     * Enables `debugFreecam` mode on this CameraManager, which tells it to handle user input for moving the camera.
     * <p>
     * TODO replace with a separate class so that user input is handled elsewhere.
     */
    public void enterDebugFreecamMode() {
        subjectID = new WorldEntity(entityManager, new BodyDef()).id;
        debugFreecam = true;
    }

    /**
     * Handles user input to move the camera when `debugFreecam` is enabled on this CameraManager
     *
     * @param dt time in seconds since last frame
     */
    private void handleFreecamInput(float dt) {
        int dx = 0;
        dx += Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? 1 : 0;
        dx -= Gdx.input.isKeyPressed(Input.Keys.LEFT) ? 1 : 0;
        int dy = 0;
        dy += Gdx.input.isKeyPressed(Input.Keys.UP) ? 1 : 0;
        dy -= Gdx.input.isKeyPressed(Input.Keys.DOWN) ? 1 : 0;

        // m/s
        float speed = 10;
        float zoomFactor = unitScale / camera.zoom;
        entityManager.setLinearVelocity(subjectID, new Vector2(dx, dy).nor().scl(speed * dt / zoomFactor));

        if (Gdx.input.isKeyPressed(Input.Keys.O) && camera.zoom > 0.5 * unitScale) {
            camera.zoom *= 0.97f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.L) && camera.zoom < 2 * unitScale) {
            camera.zoom /= 0.97f;
        }
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
