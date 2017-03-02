package client.utility.drawables;

import java.awt.Point;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class DrawableEntity implements Drawable {
    private int    id;
    private String name;
    private String type;
    Point          frameSize;
    Point          currentFrame;
    Point          sheetSize;
    int            millisecondsPerFrame;
    int            timeSinceLastFrame;
    private Vector2f position;
    private float rotation;
    private SpriteSheet sprite;
    Image img;

    public DrawableEntity(int id, String name, String type, Vector2f pos, int rotation, SpriteSheet texture, Point frameSize, Point sheetSize,
            int millisecondsPerFrame) {
        this.id = id;
        this.name = name;
        this.type = type;
        setPosition(pos);
        setRotation((float) rotation);
        this.frameSize = frameSize;
        this.sheetSize = sheetSize;
        currentFrame = new Point(0, 0);
        sprite = texture;
        this.millisecondsPerFrame = millisecondsPerFrame;
    }

    public void update(int millSinceLastUpdate) {
        /*
         * Handles a sprites animation
         * moving to the next frame
         */
        timeSinceLastFrame+= millSinceLastUpdate;
        if (timeSinceLastFrame > millisecondsPerFrame) {
            timeSinceLastFrame = 0;
            currentFrame.x++;
            if (currentFrame.x >= sheetSize.x) {
                currentFrame.x = 0;
                currentFrame.y++;
                if (currentFrame.y >= sheetSize.y) {
                    currentFrame.y = 0;
                }
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
    public Vector2f getPosition() {
        return position;
    }
    
    public void setPosition(Vector2f pos) {
        this.position = pos;
    }
    
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    
    public float getRotation() {
        return rotation;
    }
    
    public Image getImage() {
        Image img = sprite.getSubImage(currentFrame.x * frameSize.x, currentFrame.y * frameSize.y, frameSize.x, frameSize.y);
        
        img.setRotation(getRotation());
        return img;
    }

	@Override
	public void draw() {
		img = sprite.getSubImage(currentFrame.x * frameSize.x, currentFrame.y * frameSize.y, frameSize.x, frameSize.y);
		img.setCenterOfRotation(25, 25);
		img.setRotation(getRotation());
		
		getImage().draw(getPosition().x-12.5f, getPosition().y-12.5f);
	}
}
