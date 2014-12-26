package svk.sglubos.engine.gfx;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Screen {
	private int width;
	private int height;
	
	private Color defaultColor;
	BufferStrategy bs;
	
	private Graphics g;
	
	public Screen(Canvas canvas, int buffers, Color defaultColor){
		bs = canvas.getBufferStrategy();
		if(bs == null){
			canvas.createBufferStrategy(buffers);
			bs = canvas.getBufferStrategy();
		}
		g = bs.getDrawGraphics();
		
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();
		this.defaultColor = defaultColor;
	}
	
	public void renderSquare(int width, int height, int x, int y){
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
	}
	
	public void renderString(String text, int x, int y){
		g.setColor(new Color(168,168,168));
		g.setFont(new Font("Sans Serif",Font.BOLD,15));
		g.drawString(text, x, y);
	}
	
	public void show(){
		g.dispose();
		bs.show();
	}
	
	public void ClearToDefaultColor(){
		g = bs.getDrawGraphics();
		
		g.setColor(defaultColor);
		g.fillRect(0, 0, width, height);
	}
	
	public void setWith(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
}
