package svk.sglubos.engine.IO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import svk.sglubos.engine.utils.MessageHandler;


/**
 * Loads and exports image files.
 *
 */

//TODO documentation

public class ImagePort {
	
	public static BufferedImage getImageAsResource(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(ImagePort.class.getResource("path"));
		} catch (IOException e) {
			MessageHandler.printMessage("IMAGE_PORT", MessageHandler.ERROR, "IOException occured when loading image as resource:" + path);
			e.printStackTrace();
		}
		return image;
	}
	
	public static BufferedImage loadImage(String path) {
		BufferedImage image = null;
		try{
			image = ImageIO.read(new File(path));			
		}catch (IOException e){
			MessageHandler.printMessage("IMAGE_PORT", MessageHandler.ERROR, "IOException occured when loading image:" + path);
			e.printStackTrace();
		}
		return image;
	}
	
	//TODO
	public static void exportImage(BufferedImage image, String path) {
	}
	
}
