package svk.sglubos.engine.gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;

public class Screen {
	private int width;
	private int height;
	
	private Color defaultColor;
	BufferStrategy bs;
	
	private Graphics g;
	
	public Screen(Canvas canvas, int buffers, Color defaultColor){
		canvas.createBufferStrategy(buffers);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		
		canvas.addComponentListener(new ComponentListener(){
			
			@Override
			public void componentResized(ComponentEvent e) {
				width = canvas.getWidth();
				height = canvas.getHeight();
			}

			@Override
			public void componentMoved(ComponentEvent e) {}

			@Override
			public void componentShown(ComponentEvent e) {}
			
			@Override
			public void componentHidden(ComponentEvent e) {}
			
		});
		
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();
		this.defaultColor = defaultColor;
	}
	
	
	
	
	//<temporaries> (yes html tag... why not ? :D )
	public void renderRectangle(int width, int height, int x, int y, Color color){
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	public void renderString(String text, int x, int y){
		g.setColor(new Color(168,168,168));
		g.setFont(new Font("Sans Serif",Font.BOLD,15));
		g.drawString(text, x, y);
	}
	//</temporaries>
	
	/**
	 * Shows rendered graphics on screen and disposes java.awt.Graphics object.
	 */
	
	public void show(){
		g.dispose();
		bs.show();
	}
	
	/**
	 * Initializes java.awt.Graphics object before rendering next frame and fills entire screen with default color.
	 */
	
	public void ClearToDefaultColor(){
		g = bs.getDrawGraphics();
		
		g.setColor(defaultColor);
		g.fillRect(0, 0, width, height);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
