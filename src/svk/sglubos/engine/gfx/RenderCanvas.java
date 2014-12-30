package svk.sglubos.engine.gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

//TODO Documentation

@SuppressWarnings("serial")
public class RenderCanvas extends Canvas {
	private double scale = 1.0; 
	
	private Color defaultColor;
	private BufferStrategy bs;
	private Graphics g;
	
	public RenderCanvas(Color defaultColor){
		this.defaultColor = defaultColor;
	}
	
	public void init(int buffers){
		createBufferStrategy(buffers);
		bs = getBufferStrategy();
	}
	
	public void drawRenderLayer(BufferedImage image){
		g.drawImage(image, 0, 0,(int)(image.getWidth()*scale),(int)(image.getHeight()*scale), null);
	}
	
	public void clearToDefaultcolor(){
		g = bs.getDrawGraphics();
		g.setColor(defaultColor);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public void showRenderedContent(){
		g.dispose();
		bs.show();
	}
}
