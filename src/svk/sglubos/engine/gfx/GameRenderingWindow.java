package svk.sglubos.engine.gfx;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class GameRenderingWindow extends JFrame {

	private BufferedImage renderLayer;
	private BufferStrategy bs;
	private Rectangle drawingArea;
	private Insets insets;
	
	private double scale;
	
	public GameRenderingWindow(BufferedImage renderLayer, double scale) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int)(renderLayer.getWidth() * scale), (int)( renderLayer.getHeight() * scale));
		setResizable(false);
		setVisible(true);
		
		insets = getInsets();
		int insetWide = insets.left + insets.right;
		int insetTall = insets.top + insets.bottom;
		
		setSize(getWidth() + insetWide, getHeight() + insetTall);
		drawingArea = new Rectangle(insets.left, insets.top, renderLayer.getWidth(), renderLayer.getHeight());
		System.out.println(insets.top);
		setIgnoreRepaint(true);
		
		createBufferStrategy(2);
		bs = getBufferStrategy();

		this.renderLayer =  renderLayer;
		this.scale = scale;
	}

	public void render() {
		Graphics g =  bs.getDrawGraphics();
		Graphics2D drawingGraphics = (Graphics2D) g.create();
		
		drawingGraphics.translate(drawingArea.x, drawingArea.y);
		drawingGraphics.scale(scale, scale);
		drawingGraphics.drawImage(renderLayer, 0 , 0, null);
		
		drawingGraphics.dispose();
		g.dispose();
		bs.show();
	}
}