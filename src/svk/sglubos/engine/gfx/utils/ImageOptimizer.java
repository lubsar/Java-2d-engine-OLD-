package svk.sglubos.engine.gfx.utils;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

public class ImageOptimizer {
	public static BufferedImage optimize(BufferedImage image) {
		return optimize(image, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration());
	}
	
	public static BufferedImage optimize(BufferedImage image, GraphicsConfiguration gfxConfig) {
		if (image.getColorModel().equals(gfxConfig.getColorModel())){
			return image;
		}
		
		BufferedImage optimized = gfxConfig.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());

		Graphics g = optimized.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return optimized; 
	}
}
