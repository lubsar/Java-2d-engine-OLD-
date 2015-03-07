package svk.sglubos.engine.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import svk.sglubos.engine.utils.debug.MessageHandler;

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
	 * If caught, error message is printed through {@link svk.sglubos.engine.utils.debug.MessageHandler message handler} and stack trace is printed. <br>
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
	 * Uses {@link javax.imageio.ImageIO#read(java.io.File) ImageIO.read(File)} with argument {@link java.io.File file} instance created with specified path,<br>
	 * Catches {@link java.io.IOException IOException}.<br>
	 * If caught, error message is printed through {@link svk.sglubos.engine.utils.debug.MessageHandler message handler} and stack trace is printed.<br>
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
	
	//TODO document
	public static BufferedImage loadImage(File file) {
		BufferedImage image = null;
		try{
			image = ImageIO.read(file);			
		}catch (IOException e){
			MessageHandler.printMessage("IMAGE_PORT", MessageHandler.ERROR, "IOException occured when loading image:" + file);
			e.printStackTrace();
		}
		return image;
	}
	
	/**
	 * Saves specified {@link java.awt.image.BufferedImage image} to file at specified path, with specified name and format.<br>
	 * Uses {@link javax.imageio.ImageIO#write(java.awt.image.RenderedImage, String, File) ImageIO.write(image, format, file)} method with arguments: 
	 * image, formant, {@link java.io.File File} instance created with specified path + name.<br>
	 * Catches any {@link java.lang.Exception Exception}.<br>
	 * If caught, error message is printed through {@link svk.sglubos.engine.utils.debug.MessageHandler message handler} and also stack trace is printed.<br>
	 * 
	 * <h1>Path & name:</h1>
	 * If path ends with: "/", the {@link java.io.File File} is created with path: <code>path + name + "." + format</code>, 
	 * if path does not end with: "/" and is not empty, the {@link java.io.File File} is created with path: <code>path + "/" + name + "." + format</code>.<br>
	 * if path is empty, the {@link java.io.File File} is created wit path: </code>name + "." + format</code>.<br><br>
	 *  
	 * @param image {@link java.awt.image.BufferedImage BufferedImage} which will be saved 
	 * @param path path where image file will be saved
	 * @param name name of image file which will be saved
	 * @param format format of image file <br><br>
	 *
	 * @see javax.imageio.ImageIO#write(java.awt.image.RenderedImage, String, File)
	 */
	public static void exportImage(BufferedImage image, String path, String name, String format) {
		if(path.endsWith("/")) {
			path += name + "." + format;
		} else if(!path.isEmpty()) {
			path += "/" + name + "." + format;
		} else {
			path += name + "." + format;
		}
		
		try {
			ImageIO.write(image,format , new File(path));
			System.out.println(new File(path).getAbsolutePath());
		} catch (Exception e) {
			MessageHandler.printMessage("IMAGE_PORT", MessageHandler.ERROR, "Exception occured when writing image " + image +" to: " + path);
			e.printStackTrace();
		}
	}
}
