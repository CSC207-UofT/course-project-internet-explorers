package core.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import java.util.function.Function;

public class CameraManager {

    protected OrthographicCamera camera;
    protected float unitScale;
    private Vector3 subjectPosition;
    private boolean debugFreecam;

    public CameraManager(float unitScale) {
        this.unitScale = unitScale;
        this.subjectPosition = new Vector3().setZero();
        this.camera = new OrthographicCamera();
        this.debugFreecam = false;

        camera.position.set(subjectPosition);
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

        camera.position.z = subjectPosition.z;
        Vector3 dp = subjectPosition.cpy().sub(camera.position);
        Function<Float, Float> f = x -> (float) Math.round((x - Math.tanh(x) + 0.1 * Math.tanh(10 * x)) / unitScale * 2) * unitScale / 2;
        camera.translate(dp.scl(f.apply(dp.len2()) * 60 * dt));
    }

    public void syncCameraViewportToWindow() {
        camera.viewportWidth = Gdx.graphics.getWidth();
        camera.viewportHeight = Gdx.graphics.getHeight();
        camera.update();
    }

    public void enterDebugFreecamMode() {
        subjectPosition = new Vector3().set(camera.position);
        debugFreecam = true;
    }

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
        subjectPosition.add(new Vector3(dx, dy, 0).nor().scl(speed * dt / zoomFactor));

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

    public void setSubjectPosition(Vector3 position) {
        this.subjectPosition = position;
    }

    public Vector3 getSubjectPosition() {
        return subjectPosition;
    }
}
