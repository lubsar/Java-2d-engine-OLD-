package svk.sglubos.engine.test;

import svk.sglubos.engine.gfx.ScreenComponent;

public class ScreenComponentTest extends ScreenComponent {
	
	int light = 0;
	
	int ambientAlpha = 0;
	float ambientFactor = ambientAlpha / 255f;
	float colorFactor = 1 -ambientFactor;
	float ambientRed = (light >> 16 & 0xff) * ambientFactor;
	float ambientGreen = (light >> 8 &0xff) * ambientFactor;
	float ambientBlue = (light & 0xff) * ambientFactor;
	
	int outAlpha = 255 << 24;
	
	public void changeLight(int alpha) {
	 ambientAlpha = alpha & 255;
	 ambientFactor = ambientAlpha / 255f;
	 colorFactor = 1 -ambientFactor;
	 ambientRed = (light >> 16 & 0xff) * ambientFactor;
	 ambientGreen = (light >> 8 &0xff) * ambientFactor;
	 ambientBlue = (light & 0xff) * ambientFactor;
	}
	
	public void shadeItAll() {
		if(!bound) {
			return;
		}
		for(int i =0; i <pixels.length; i++){
			pixels[i] = applyLightningOnPixels(pixels[i]);
		}
	}
	
	public int applyLightningOnPixels(int color1) {
	    int red =  (int) ((color1 >>16 & 0xff) * colorFactor + ambientRed);
	    int green = (int) (((color1>>8) & 0xff) * colorFactor + ambientGreen);
	    int blue = (int) ((color1 & 0xff) * colorFactor + ambientBlue);
	    return outAlpha | (red << 16) | (green << 8) | (blue);
	}
}
