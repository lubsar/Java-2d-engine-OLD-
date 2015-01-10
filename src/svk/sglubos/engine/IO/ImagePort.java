package svk.sglubos.engine.IO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import svk.sglubos.engine.utils.MessageHandler;

/**
 * Provides ability to load and export {@link java.awt.image.BufferedImage BufferedImage}. <br>
 * The image can be obtained from specified resource path or path of file. <br>
 * To load images are used methods from {@link javax.imageio.ImageIO}
 * 
 *	@see javax.imageio.ImageIO
 *	@see java.awt.image.BufferedImage
 */
public class ImagePort {
	
	/**
	 * Returns {@link java.awt.image.BufferedImage} as a product of decoding resource at specified path. <br>
	 * Uses {@link javax.imageio.ImageIO#read(java.net.URL) ImageIO.read(URL)} with parameter <code> ImagePort.class.getResource(path) </code> <br>
	 * Catches {java.io.IOException IOException}.<br>
	 * If caught, error message is printed through {@link svk.sglubos.engine.utils.MessageHandler message handler} and stack trace is printed. <br>
	 * <p>
	 * @param path path of resource which will be read
	 * <p>
	 * @return BufferedImage decoded from specified resource
	 * 
	 * @see javax.imageio.ImageIO#read(java.net.URL)
	 */
	public static BufferedImage getImageAsResource(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(ImagePort.class.getResource(path));
		} catch (IOException e) {
			MessageHandler.printMessage("IMAGE_PORT", MessageHandler.ERROR, "IOException occured when loading image as resource:" + path);
			e.printStackTrace();
		}
		return image;
	}
	
	/**
	 * Returns {@link java.awt.image.BufferedImage} as a product of decoding File at specified path. <br>
	 * Uses {@link javax.imageio.ImageIO#read(java.io.File) ImageIO.read(File)} with parameter {@link java.io.File file} instance created with specified path,<br>
	 * Catches {java.io.IOException IOException}.<br>
	 * If caught, error message is printed through {@link svk.sglubos.engine.utils.MessageHandler message handler} and stack trace is printed. <br>
	 * <p>
	 * @param path path of file which will be read
	 * <p>
	 * @return BufferedImage decoded from specified file
	 * 
	 * @see javax.imageio.ImageIO#read(java.io.File)
	 */
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
	
	//TODO export
	public static void exportImage(BufferedImage image, String path) {
	}
	
}
