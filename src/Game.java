import org.lwjgl.input.Keyboard;

public class Game {

    private MyMesh mesh;
    private Shader shader;
    private Transform transformer;
    private Camera camera;
    private Material material;


    PointLight p1 = new PointLight(new BaseLight(new Vector3F(1, 0.5f, 0), 1.0f), new Attenuation(0, 0, 1), new Vector3F(-2, 0, 5f), 30);
    PointLight p2 = new PointLight(new BaseLight(new Vector3F(1, 1, 0), 1.0f), new Attenuation(0, 0, 1), new Vector3F(2, 0, 7f), 30);
    PointLight p3 = new PointLight(new BaseLight(new Vector3F(0, 1f, 0), 1.0f), new Attenuation(0, 0, 1), new Vector3F(2, 0, 9f), 30);

    SpotLight s1 = new SpotLight(new PointLight(new BaseLight(new Vector3F(0, 0, 0), 1.5f), new Attenuation(0, 0, 0.1f), new Vector3F(-2, 0, 5f), 30), new Vector3F(1, 1, 1), 0.7f);

    public Game(String model) {

        shader = PhongShader.getInstance();
        camera = new Camera();
        Transform.setProjection(70f, GameWindow.getWidth(), GameWindow.getHeight(), 0.1f, 1000);
        Transform.setCamera(camera);
        transformer = new Transform();
        material = new Material(new Texture("test.png"), new Vector3F(1, 1, 1), 1, 8);

        if (model.equals("T1")){

            // Triangle
            Vertex[] vertices = new Vertex[]{new Vertex(new Vector3F(-1, -1, 0), new Vector2F(0, 0)),
                    new Vertex(new Vector3F(0, 1, 0), new Vector2F(0.5f, 0)),
                    new Vertex(new Vector3F(1, -1, 0), new Vector2F(1.0f, 0)),
                    new Vertex(new Vector3F(0, -1 , 1), new Vector2F(0, 0.5f))


            }; // Starts at bottom left

            int[] indices = new int[]{3, 1, 0,
                    2, 1, 3,
                    0, 1, 2,
                    0, 2, 3}; // order in which we draw vertices

            mesh = new MyMesh(vertices, indices, true);

        } else if (model.equals("S2")){
            //Triangle 2:


            Vertex[] vertices = new Vertex[]{new Vertex(new Vector3F(-1.0f, -1.0f, 0.5773f), new Vector2F(0.0f, 0.0f)),
                    new Vertex(new Vector3F(0.0f, -1.0f, -1.15475f), new Vector2F(0.5f, 0.0f)),
                    new Vertex(new Vector3F(1.0f, -1.0f, 0.5773f), new Vector2F(1.0f, 0.0f)),
                    new Vertex(new Vector3F(0.0f, 1.0f, 0.0f), new Vector2F(0.5f, 1.0f))};

            int[] indices = {0, 3, 1,
                    1, 3, 2,
                    2, 3, 0,
                    1, 2, 0};

            mesh = new MyMesh(vertices, indices, true);
        } else if (model.equals("S3")){

            // Flat Surface / Plane:
            Vertex[] vertices = new Vertex[]{new Vertex(new Vector3F(-10, 0.0f, -10), new Vector2F(0.0f, 0.0f)),
                    new Vertex(new Vector3F(-10, 0.0f, 10 * 3), new Vector2F(0.0f, 1.0f)),
                    new Vertex(new Vector3F(30, 0.0f, -10), new Vector2F(1.0f, 0.0f)),
                    new Vertex(new Vector3F(30, 0.0f, 30), new Vector2F(1.0f, 1.0f))};

            int[] indices = {0, 1, 2,
                    2, 1, 3};
            mesh = new MyMesh(vertices, indices, true);
        } else {
            mesh = new MyMesh(model);
        }

        PhongShader.setAmbientLight(new Vector3F(0.1f, 0.1f, 0.1f));

        PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(new Vector3F(1, 1, 1), 0.8f), new Vector3F(1, 1, 1)));

        PhongShader.setPointLights(new PointLight[]{p1, p2, p3});

        PhongShader.setSpotLights(new SpotLight[]{s1});
    }

    boolean flashOn = true;
    public void input() {

        camera.userMove();

        if (Input.getKeyDown(Keyboard.KEY_F)){
            int myInt = flashOn ? 1 : 0;
            s1.getPointLight().getBaseLight().setColour(new Vector3F(1, 1, 1).mult(myInt));

            flashOn = !flashOn;
        }
    }

    float temp = 0.0f; // Keeps track of time elapsed since starting

    public void update() {

        temp += Time.getDelta();

        // System.out.println(temp);
        float sin_val = (float) Math.sin(temp);

        float cos_val = (float) Math.cos(temp);

        float abs_sin = Math.abs(sin_val);

        float abs_cos = Math.abs(cos_val);

        transformer.setTranslation(0, -1, 5);

        s1.getPointLight().setPosition(camera.getPosition());
        s1.setDirection(camera.getForward());
        // s1.setConeCutOff(abs_sin);


        // s1.getPointLight().setPosition(camera.getPosition().sub(new Vector3F(1, 0, 0)));

        //s1.setPointLight(new PointLight(new BaseLight(new Vector3F(0, 1f, 1f), 0.8f), new Attenuation(0, 0, 0.1f), Transform.getCamera().getPosition(), 30));

        // transformer.setRotation(0,sin_val * 360, 0);

        // PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(new Vector3F(1, 1, 1), 0.8f), camera.getForward().mult(-1)));

        // transformer.setScaling(0.7f * abs_sin, 0.7f * abs_sin, 0.7f * abs_sin);

        p1.setPosition(new Vector3F(3, 0, 8f * (-abs_sin + 0.5f) + 10));
        p1.getBaseLight().setColour(new Vector3F(abs_sin, abs_cos, 0));
//        p1.setAtt(new Attenuation(0, abs_sin, -abs_cos));

        p2.setPosition(new Vector3F(7, 0, 8f * (sin_val + 0.5f) + 10));
        p2.getBaseLight().setColour(new Vector3F(abs_sin, 0, abs_cos));
//        p2.setAtt(new Attenuation(0, -abs_sin, -abs_cos));

        p3.setPosition(new Vector3F(11, 0, 8f * (abs_sin + 0.5f) + 10));
        p3.getBaseLight().setColour(new Vector3F(0, abs_sin, abs_cos));
//        p3.setAtt(new Attenuation(0, abs_sin, -abs_cos));
        
    }

    public void render() {
        // RenderUtil.setClearColour(new Vector3F(0f, 0,0.25f)); change world colour
        shader.bind();
        shader.updateUniforms(transformer.getTransformation(), transformer.getProjectedTransformation(), material);
        mesh.draw();

    }

}
