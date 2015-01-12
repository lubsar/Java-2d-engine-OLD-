package svk.sglubos.engine.test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import svk.sglubos.engine.IO.ImagePort;
import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.ScreenComponent;

public class ScreenComponentTest extends ScreenComponent {
	private BufferedImage shadowMap = new BufferedImage(500,500,BufferedImage.TYPE_INT_ARGB);
	int shadowPixels[] = ((DataBufferInt)shadowMap.getRaster().getDataBuffer()).getData();
	private BufferedImage map = ImagePort.loadImage("G:\\Dokumenty\\eclipseWS\\GameEngine\\res\\shadow.png");
	private BufferedImage mp = new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB);
	private int light = map.getRGB(0,0);;
	public void bind(Screen screen,Graphics g, int[] pixels) {
		super.bind(screen, g, pixels);
		shadowMap.setRGB(0, 0, 500, 500, map.getRGB(0, 0, 500, 500, null, 0, 500), 0, 500);
		for(int i = 0; i < 100; i++) {
			mp.setRGB(i%10, i/10, light);
		}
	}
	
	public void shadeItAll(int transp) {
		if(!bound) {
			return;
		}
		for(int y = 0; y < 500; y+= 10){
			for(int x = 0; x < 500; x+= 10){
				g.drawImage(mp, x, y,null);				
			}
		}
//		for(int i =0; i <pixels.length; i++){
//			pixels[i] = mixColorsWithAlpha(pixels[i], light,23);		
//		}
	}
	
	public int mixColorsWithAlpha(int color1, int color2, int alpha)
	{
	    float factor = alpha / 255f;
	    int red =  (int) ((((color1 >>16)&0xff) * (1 - factor)) + (((color2>>16)&0xff) * factor));
	    int green = (int) (((color1>>8)&0xff) * (1 - factor) + (((color2>>8)&0xff) * factor));
	    int blue = (int) ((color1&0xff) * (1 - factor) + (color2&0xff) * factor);
	    return (255 <<24) | (red << 16) | (green << 8) | (blue);
	}
}
