package client.utility;

import java.awt.Font;
import java.io.InputStream;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class FontFactory {

	public static TrueTypeFont getResource(String name, float size) {
		if (name.equalsIgnoreCase(Constants.ARIAL)) {
			try {
				InputStream inputStream = ResourceLoader.getResourceAsStream("res/arial.ttf");

				Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
				awtFont = awtFont.deriveFont(size); // set font size
				return new TrueTypeFont(awtFont, false);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (name.equalsIgnoreCase(Constants.COMIC)) {
			try {
				InputStream inputStream = ResourceLoader.getResourceAsStream("res/comic.ttf");

				Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
				awtFont = awtFont.deriveFont(size); // set font size
				return new TrueTypeFont(awtFont, false);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (name.equalsIgnoreCase(Constants.COUR)) {
			try {
				InputStream inputStream = ResourceLoader.getResourceAsStream("res/cour.ttf");

				Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
				awtFont = awtFont.deriveFont(size); // set font size
				return new TrueTypeFont(awtFont, false);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
