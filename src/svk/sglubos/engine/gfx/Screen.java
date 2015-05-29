/*
 *	Copyright 2015 ¼ubomír Hlavko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package svk.sglubos.engine.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import svk.sglubos.engine.gfx.sprite.Sprite;
import svk.sglubos.engine.utils.debug.MessageHandler;

public class Screen {
	protected int width;
	protected int height;
	protected int xOffset = 0;
	protected int yOffset = 0;
	protected boolean ignoreOffset;
	
	protected int[] pixels;
	
	protected Color defaultScreenColor;
	protected BufferedImage renderLayer;
	protected Graphics g;
	
	public Screen(int width, int height, Color defaultColor) {
		renderLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)renderLayer.getRaster().getDataBuffer()).getData();
		g = renderLayer.createGraphics();
		
		this.width = width;
		this.height = height;
		this.defaultScreenColor = defaultColor;
	}
		
	public void renderFilledRectangle(int x, int y, int width, int height,	Color color) {
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.fillRect(x, y, width, height);
	}
	
	public void renderFilledRectangle(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.fillRect(x, y, width, height);
	}
	
	public void renderRectangle(int x, int y, int width, int height, Color color) {
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawRect(x, y, width, height);
	}
	
	public void renderRectangle(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawRect(x, y, width, height);
	}

	public void renderImage(BufferedImage img, int x, int y, int width, int height) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawImage(img, x, y, width, height, null);
	}
	
	public void renderImage(BufferedImage img, int x, int y) {
		if(!ignoreOffset){
			x -= xOffset; 
			y -= yOffset;
		}
		
		g.drawImage(img, x, y, img.getWidth(), img.getHeight(), null);
	}
	
	public void renderString(String text, int x, int y, Font font, Color color) {
		setFont(font);
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawString(text, x, y);
	}
	
	public void renderString(String text, int x, int y, Font font) {
		setFont(font);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawString(text, x, y);
	}
	
	public void renderString(String text, int x, int y) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawString(text, x, y);
	}
	
	public void renderFilledOval(int x, int y, int width, int height, Color color) {
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.fillOval(x, y, width, height);
	}
	
	public void renderFilledOval(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.fillOval(x, y, width, height);
	}
	
	public void renderOval(int x, int y, int width, int height, Color color) {
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawOval(x, y, width, height);
	}
	
	public void renderOval(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawOval(x, y, width, height);
	}

	public void renderLine(int x, int y, int xa, int ya, Color color) {
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
			xa -= xOffset;
			ya -= yOffset;
		}
		
		g.drawLine(x, y, xa, ya);
	}
	
	public void renderLine(int x, int y, int xa, int ya) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
			xa -= xOffset;
			ya -= yOffset;
		}
		
		g.drawLine(x, y, xa, ya);
	}
	
	public void renderFilledArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color){
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.fillArc(x, y, width, height, startAngle, arcAngle);
	}
	
	public void renderFilledArc(int x, int y, int width, int height, int startAngle, int arcAngle){
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.fillArc(x, y, width, height, startAngle, arcAngle);
	}
	
	public void renderArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color){
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawArc(x, y, width, height, startAngle, arcAngle);
	}
	
	public void renderArc(int x, int y, int width, int height, int startAngle,	int arcAngle) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawArc(x, y, width, height, startAngle, arcAngle);
	}
	
	public void renderSprite(Sprite sprite, int xCoord, int yCoord) {
		int[] spritePixels = sprite.getPixels();
		
		if(!ignoreOffset){
			xCoord -= xOffset;
			yCoord -= yOffset;
		}
		
		xCoord--;
		yCoord--;
		
		int spriteWidth = sprite.getWidth();
		int spriteHeight = sprite.getHeight();
		
		int pixelX = 0;
		int pixelY = 0;
		
		for(int y = 0; y < spriteHeight; y++){
			pixelY = y + yCoord;
			for(int x = 0; x < spriteWidth; x++){
				pixelX = x + xCoord;
				
				if(spritePixels[x + y * spriteWidth] == 0){
					continue;
				}
				
				if(pixelY >= 0 && pixelY < this.height && pixelX >= 0 && pixelX < this.width){
					this.pixels[pixelX + pixelY * this.width] = spritePixels[x + y * spriteWidth];
				}
			}
		}
	}
	
	public void renderSprite(Sprite sprite, int xCoord, int yCoord, int scale){
		int[] spritePixels = sprite.getPixels();
		
		if(!ignoreOffset){
			xCoord -= xOffset;
			yCoord -= yOffset;
		}
		
		int spriteWidth = sprite.getWidth();
		int spriteHeight = sprite.getHeight();

		int pixelX = 0;
		int pixelY = 0;
		
		int scaledPixelX;
		int scaledPixelY;
		
		for(int y = 0; y < spriteHeight; y++){
			pixelY = y * scale + yCoord;
			for(int x = 0; x < spriteWidth; x++){
				pixelX = x * scale + xCoord;
				
				System.out.println(spritePixels[x + y * spriteWidth]);
				if(spritePixels[x + y * spriteWidth] == 0){
					continue;
				}
				
				if(scale > 1){
					for(int yScaler = 0; yScaler < scale; yScaler++) {
						for(int xScaler = 0; xScaler < scale; xScaler++) {
							scaledPixelY = pixelY + yScaler;
							scaledPixelX = pixelX + xScaler;
							
							if(scaledPixelY  >= 0 && scaledPixelY  < this.height && scaledPixelX  >= 0 && scaledPixelX  < this.width) {
								this.pixels[scaledPixelX  + scaledPixelY * this.width] = spritePixels[x + y * spriteWidth];
							}
						}
 					}
				} else {
					if(pixelY > 0 && pixelY < this.height && pixelX > 0 && pixelX < this.width){
						this.pixels[pixelX + pixelY * this.width] = spritePixels[x + y * spriteWidth];
					}
				}
				
			}
		}
	}
	
	public void clear(){
		int colorValue = defaultScreenColor.getRGB();
		clear(defaultScreenColor);
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = colorValue;
		}
	}
	
	public void clear(Color color) {
		int colorValue = color.getRGB();
		Arrays.fill(pixels, 0, pixels.length, colorValue);
	}
	
	public void setColor(Color color) {
		if (color == null) {
			MessageHandler.printMessage(MessageHandler.ERROR, "Screen color cannot be set to null");
			throw new IllegalArgumentException("Screen color cannot be set to null");
		}
		
		g.setColor(color);
	}
	
	public void setFont(Font font) {
		if (font == null) {
			MessageHandler.printMessage(MessageHandler.ERROR, "Screen font cannot be set to null, font stays set to current font");
			return;
		}
		g.setFont(font);
	}
	
	public void setOffset(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void setIngoreOffset(boolean ignore){
		this.ignoreOffset = ignore;
	}
	
	public Graphics getGraphics() {
		return g;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getXOffset(){
		return xOffset;
	}
	
	public int getYOffset(){
		return yOffset;
	}
	
	public BufferedImage getRenderLayer() {
		return renderLayer;
	}

}	
