package svk.sglubos.engine.gfx;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

//TODO documentation fix window size

public class GameRenderingWindow extends JFrame {
	
	protected BufferedImage renderLayer;
	protected BufferStrategy bs;
	
	public GameRenderingWindow(String title, BufferedImage renderLayer, int width,int height){
		setSize(width,height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		createBufferStrategy(2);
		bs = getBufferStrategy(); 
		
		this.renderLayer = renderLayer;
	}
	
	public void render(){
		Graphics g = null;
		try{
			g = bs.getDrawGraphics();
		}catch(IllegalStateException e){
			createBufferStrategy(2);
			bs = getBufferStrategy();
			g = bs.getDrawGraphics();
		}
		g.drawImage(renderLayer, 0, 0,getWidth(),getHeight(),null);
		g.dispose();
		bs.show();
	}
}
