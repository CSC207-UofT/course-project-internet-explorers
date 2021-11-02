package core.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.util.function.Function;

public class CameraManager {

    protected OrthographicCamera camera;
    protected float unitScale;
    private Vector2 subjectPosition;
    private boolean debugFreecam;

    public CameraManager(float unitScale) {
        this.unitScale = unitScale;
        this.subjectPosition = new Vector2();
        this.camera = new OrthographicCamera();
        this.debugFreecam = false;

        camera.position.set(subjectPosition.x, subjectPosition.y, 0);
        camera.zoom = unitScale;
    }

    /**
     * Moves camera as needed to track the subject, then update the camera.
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

        Vector3 dp = new Vector3(subjectPosition.x, subjectPosition.y, 0).sub(camera.position);
        Function<Float, Float> f = x -> (float) Math.round((x - Math.tanh(x) + 0.1 * Math.tanh(10 * x)) / unitScale * 2) * unitScale / 2;
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
     *
     * TODO replace with a separate class so that user input is handled elsewhere.
     */
    public void enterDebugFreecamMode() {
        subjectPosition = new Vector2(camera.position.x, camera.position.y);
        debugFreecam = true;
    }

    /**
     * Handles user input to move the camera when `debugFreecam` is enabled on this CameraManager
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
        subjectPosition.add(new Vector2(dx, dy).nor().scl(speed * dt / zoomFactor));

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
     * Tells this CameraManager to track a new position vector, and snaps the camera to that position.
     * @param position the new position Vector2 to track.
     */
    public void setSubjectPosition(Vector2 position) {
        this.subjectPosition = position;
        this.camera.position.set(subjectPosition.x, subjectPosition.y, 0);
    }

    public Vector2 getSubjectPosition() {
        return subjectPosition;
    }
}
