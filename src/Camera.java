// Class that represents a Camera object

import org.lwjgl.input.Keyboard;

public class Camera {

    public static final Vector3F yAxis = new Vector3F(0, 1, 0);

    private Vector3F position;

    // Orientation details: (Note that forward and up will always need to be perpendicular to each other so cross
    // product will be very handy here)
    private Vector3F forward;
    private Vector3F up;

    public Camera() { // "Default" Camera, like the initial view.
        this(new Vector3F(0, 0, 0), new Vector3F(0, 0, 1), new Vector3F(0, 1, 0));
    }

    public Camera(Vector3F position, Vector3F forward, Vector3F up) {
        this.position = position;
        this.forward = forward.normalized();
        this.up = up.normalized();


//        up = up.normalized();
//        forward = forward.normalized();
    }

    public void move(Vector3F directionToMoveIn, float amount) {

        position = position.add(directionToMoveIn.mult(amount));

    }

    boolean mouseInViewMode = false;

    public void userMove() {

        Vector2F centerPosition = new Vector2F(GameWindow.getWidth()/2, GameWindow.getHeight()/2);

        if (Input.getKey(Keyboard.KEY_ESCAPE)){
            Input.setCursorVisible(true);
            mouseInViewMode = false;

        }

        if (Input.getMouse(0)){
            Input.setCursorVisible(false);
            Input.setMousePosition(centerPosition);
            mouseInViewMode = true;
        }

        if (mouseInViewMode) {

            // Mouse.getDWheel();

            float translationAmount = (float) (10 * Time.getDelta());



            if (Input.getKey(Keyboard.KEY_W)) {
                move(getForward(), translationAmount);
            }
            if (Input.getKey(Keyboard.KEY_S)) {
                move(getForward(), -translationAmount);
            }
            if (Input.getKey(Keyboard.KEY_A)) {
                move(getLeft(), translationAmount);
            }
            if (Input.getKey(Keyboard.KEY_D)) {
                move(getRight(), translationAmount);
            }

            if (Input.getKey(Keyboard.KEY_R)) {
                this.position = new Vector3F(0, 0, 0);
                this.forward = new Vector3F(0, 0, 1);
                this.up = new Vector3F(0, 1, 0);
            }

                Vector2F deltaPos = Input.getMousePosition().sub(centerPosition);

                boolean rotY = deltaPos.getX() != 0;
                boolean rotX = deltaPos.getY() != 0;

                if (rotY) {
                    rotateY(deltaPos.getX() * 0.1f);
                }

                if (rotX) {
                    rotateX(-deltaPos.getY() * 0.1f);
                }

                if (rotY || rotX) {
                    Input.setMousePosition(centerPosition);
                }

        }

    }

    public Vector3F getLeft() { // Cross product of up and forward gives the left vector by definition.

        return forward.crossProd(up).normalized();

    }


    public Vector3F getRight() { // This is essentially multiplying the result of getLeft() by -1

        return up.crossProd(forward).normalized();

    }


    public void rotateX(float angle) { // Rotating Camera around x-axis
        Vector3F xAxis = yAxis.crossProd(forward).normalized();

        forward = forward.rotate(angle, xAxis).normalized();

        up = forward.crossProd(xAxis).normalized();
    }

    public void rotateY(float angle) {

        Vector3F xAxis = yAxis.crossProd(forward).normalized();


        forward = forward.rotate(angle, yAxis).normalized();

        up = forward.crossProd(xAxis).normalized();

    }


    public Vector3F getPosition() {
        return position;
    }

    public void setPosition(Vector3F position) {
        this.position = position;
    }

    public Vector3F getForward() {
        return forward;
    }

    public void setForward(Vector3F forward) {
        this.forward = forward;
    }

    public Vector3F getUp() {
        return up;
    }

    public void setUp(Vector3F up) {
        this.up = up;
    }
}
