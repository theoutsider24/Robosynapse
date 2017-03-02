package client.utility;

import java.util.logging.Level;

import org.lwjgl.util.vector.Vector2f;

/*
 * Contains variables that need to be seen across the application and also
 * serves as a location for important settings e.g Port
 */
public class Constants {
	public static Vector2f baseSpeed 					= new Vector2f(2, 2);
	public static final int WINDOW_WIDTH 				= 1280;
	public static final int WINDOW_HEIGHT 				= 720;
	public static final int ARENA_WIDTH               = 2000;//The area game makes use of, separating from menu width and height
    public static final int ARENA_HEIGHT               = 1125;
	public static final short VERTICAL 					= 1;
	public static final short HORIZONTAL 				= 2;
	public static final float ZOOM 						= 0.5f;
	public static final int BACKGROUND_IMAGE_X_OFFSET 	= -200;
	public static final int BACKGROUND_IMAGE_Y_OFFSET 	= -200;
	
	public static int SERVER_LISTENING_PORT  	= 2000;
	public static int CLIENT_DESTINATION_PORT 	= 9877;
	public static int DATA_SIZE 				= 10000;
	public static int BEAT 						= 5000;
	public static final int MAP_SQUARE_SIZE 	= 50;
	public static final int MAP_SQUARE_IMAGE_SIZE	= 50;
	public static Level DEBUG_LEVEL 			= Level.FINE;
	
	public static String COMIC 	= "Comic";
	public static String COUR 	= "Cour";
	public static String ARIAL 	= "Arial";
	
	public static int EXTRA_SMALL 	= 25;
	public static int SMALL 		= 50;
	public static int MEDIUM 		= 75;
	public static int LARGE 		= 100;
}
