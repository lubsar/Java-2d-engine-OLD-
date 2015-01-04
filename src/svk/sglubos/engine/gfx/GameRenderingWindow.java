package svk.sglubos.engine.gfx;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

//TODO documentation

public class GameRenderingWindow extends JFrame {
	protected BufferedImage renderLayer;
	protected BufferStrategy bs;
	
	//TODO fix size
	public GameRenderingWindow(String title, BufferedImage renderLayer, int width,int height){
		getContentPane().setPreferredSize(new Dimension(width, height));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		createBufferStrategy(2);
		bs = getBufferStrategy(); 
		
		this.renderLayer = renderLayer;
	}
	
	public void render(){
		Graphics g = bs.getDrawGraphics();
		g.drawImage(renderLayer, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
}
