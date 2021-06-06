import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;

import static org.lwjgl.opengl.GL11.*;

public class Texture {

    // Note, loading textures with 2^n x 2^m dimensions is optimal

    private int id;

    public Texture(String filename){

        this(loadTexture(filename)); // Uses below constructor
    }

    public Texture(int id){

        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void bindTexture(){
        glBindTexture(GL_TEXTURE_2D, this.id);
    }

    private static int loadTexture(String filename){

        String[] splitArray = filename.split("\\.");

        String fileExt = splitArray[splitArray.length - 1];

        try {

            int id = TextureLoader.getTexture(fileExt, new FileInputStream(new File(".\\res\\textures\\" + filename))).getTextureID();

            return id;

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return 0;
    }


}
