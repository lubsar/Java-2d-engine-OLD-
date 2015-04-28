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
import java.util.ArrayList;
import java.util.List;

import svk.sglubos.engine.gfx.sprite.Sprite;
import svk.sglubos.engine.utils.debug.DebugStringBuilder;
import svk.sglubos.engine.utils.debug.MessageHandler;
//TODO redocument
/**
 * Handles rendering of basic shapes, images, texts, {@link svk.sglubos.engine.gfx.sprite.Sprite sprites} and supports implementation of own rendering.
 * <p>
 * {@link java.awt.image.BufferedImage BufferedImage}{@link #renderLayer} contains all rendered graphics
 * and this {@link java.awt.image.BufferedImage} is returned by method {@link #getRenderLayer()}.
 * A {@link java.awt.Graphics Graphics} {@link #g object} which can draw on renderLayer is returned by method {@link #getGraphics()}.
 * <p>
 * Before rendering game content every frame you need to call {@link #prepare()} method, which creates new {@link java.awt.Graphics Graphics} object
 * and fills entire screen with {@link #defaultScreenColor} passed in {@link #Screen(int, int, Color) constructor} of this class.  
 * After rendering game content you need to call {@link #disposeGraphics()} method which disposes Graphics object to release system resources.
 * 
 * <h1>Example:render method called every frame</h1>
 * <code>
 * void render() {<br>
 * 		//start with preparation<br>
 * 		screen.prepare();<br>
 * 		<br>
 * 		//render content <br>
 * 		screen.renderFilledRectangle(0,0,50,50);<br>
 * 		<br>
 * 		//finish with dispose<br>
 * 		screen.dispose();<br>
 * }	
 * </code>
 * 
 * <h1>Rendering expansion (implementation of own rendering)</h1>
 * The <code> Screen</code> also provides ability to use your own rendering by {@link svk.sglubos.engine.gfx.ScreenComponent ScreenComponent abstract class}.<br>
 * The <code> ScreenComponent </code> class provides ability to access {@link #g screen graphics object} but also {@link #pixels screen pixels array}, and also to <code>screen</code> class instance to which is added.<br>
 * To make your screen component communicate with screen, add object of your component to the {@link #components ArrayList of ScreenComponents} by using {@link #addScreenComponent(ScreenComponent)} method.<br>
 * To remove your screen component use method {@link #removeScreenComponent(ScreenComponent)}.  
 * <p>
 * @see java.awt.Graphics
 * @see svk.sglubos.engine.gfx.sprite.Sprite
 * @see #setOffset(int, int) offseting of screen
 * @see #prepare()
 * @see #disposeGraphics()
 * @see #addScreenComponent(ScreenComponent)
 */
public class Screen {
	
	/**
	 * Screen width in pixels.
	 */
	protected int width;
	
	/**
	 * Screen height in pixels.
	 */
	protected int height;
	
	/**
	 * Horizontal offset of screen in pixels. Default value 0.
	 * <p>
	 * Offset can be set by {@link #setOffset(int, int) setOffset(xOffset, yOffset)} method.
	 * 
	 * @see #setOffset(int xOffset,int yOffset)
	 */
	protected int xOffset = 0;
	
	/**
	 * Vertical offset of screen in pixels. Default value 0.
	 * <p>
	 * Offset can be set by {@link #setOffset(int, int) setOffset(xOffset, yOffset)} method.
	 * 
	 * @see #setOffset(int xOffset,int yOffset)
	 */
	protected int yOffset = 0;
	
	/**
	 * If true offsets are ignored when rendering content, else position of rendered content is offset.
	 * <p>
	 * Can be set by {@link #setIngoreOffset(boolean) setIgnoreOffset(boolean ignoreOffset)} method.
	 * 
	 *  @see #xOffset xOffset
	 *  @see #yOffset yOffset
	 *  @see #setOffset(int, int) setOffset(xOffset, yOffset)
	 */
	protected boolean ignoreOffset;
	
	/**
	 * Color used in {@link #prepare()} method, entire screen is filled with this color when {@link #prepare()} is called.
	 * <p>
	 * The value of this color is initialized in constructor and is last passed parameter in {@link #Screen(int, int, Color) constructor}.
	 */
	protected Color defaultScreenColor;
	
	/**
	 * BufferedImage which contains all rendered graphics. To display your rendered graphics display this image.
	 * <p>
	 * The renderLayer is initialized in {@link #Screen(int, int, Color) constructor}.<br>
	 * This image is returned by {@link #getRenderLayer()} method.
	 * 
	 * @see #getRenderLayer()
	 */
	protected BufferedImage renderLayer;
	
	/**
	 * {@link java.awt.Graphics} object which provides ability to draw on {@link #renderLayer renderLayer}.
	 * <p>
	 * This object is initialized in method {@link #prepare()}.<br>
	 * It should be initialized every frame before rendering and disposed at the end of rendering for better performance.<br>
	 * This object can be obtained by {@link #getGraphics()} method.
	 * 
	 * @see #prepare()
	 * @see #renderLayer
	 * @see #disposeGraphics()
	 * @see #getGraphics()
	 */
	protected Graphics g;
	
	/**
	 * Array of screen pixels, changing values changes colors of screen pixels.
	 * <p>
	 * This array used to render {@link svk.sglubos.engine.gfx.sprite.Sprite sprites} and is passed in initialization of screen {@link #components components}<br>
	 * This array is initialized in {@link #Screen(int, int, Color) constructor} of this class.
	 * 
	 * @see svk.sglubos.engine.gfx.sprite.Sprite 
	 * @see svk.sglubos.engine.gfx.ScreenComponent
	 */
	protected int[] pixels;
	
	/**
	 * {@link java.util.List} object which stores all {@link svk.sglubos.engine.gfx.ScreenComponent} objects which have ability to use {@link #g graphics object} and change {@link #pixels Screen pixels} on their own.
	 * <p>
	 * Objects can be added by {@link #addScreenComponent(ScreenComponent)} method and removed by {@link #removeScreenComponent(ScreenComponent)} method. <br>
	 * If component is added component gains ability to use {@link #g Graphics object} and change {@link #pixels}. 
	 * If that component is removed by {@link #removeScreenComponent(ScreenComponent)} method, it looses this abilities. 
	 *  
	 *  @see svk.sglubos.engine.gfx.ScreenComponent
	 *  @see #addScreenComponent(ScreenComponent)
	 *  @see #removeScreenComponent(ScreenComponent)
	 */
	protected List<ScreenComponent> components = new ArrayList<ScreenComponent>();
	
	/**
	 * Constructs new object of Screen class.
	 * 
	 * <h1>Initializes:</h1><br> 
	 * <code>BufferedImage {@link #renderLayer}</code> with arguments: <code>width</code>, <code>height</code> and <code>BufferedImage.TYPE_IN_RGB</code>.<br>
	 * <code>Int array {@link #pixels}</code> with data got from data buffer of Raster of renderLayer: 
	 * <br><code>((DataBufferInt) renderLayer.getRaster().getDataBuffer()).getData()</code>
	 * <p>
	 * <code>Int {@link #width}</code> with value of passed parameter width <br>
	 * <code>Int {@link #height}</code> with value of passed parameter height <br>
	 * <code>Color </code> {@link #defaultScreenColor} with value of passed parameter defaultColor
	 * 
	 * @param width (width of screen)
	 * @param height (height of screen)
	 * @param defaultColor (entire screen is filled with this color in method <code>prepare()</code>)
	 * <p>
	 * @see java.awt.image.BufferedImage
	 */
	public Screen(int width, int height, Color defaultColor) {
		renderLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)renderLayer.getRaster().getDataBuffer()).getData();
		g = renderLayer.getGraphics();
		
		this.width = width;
		this.height = height;
		this.defaultScreenColor = defaultColor;
	}
	
	/**
	 * Draws filled rectangle with specified position, size and color.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawFilledRectangle(int, int, int, int) g.drawFilledRectangle(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of rectangle
	 * @param height height of rectangle
	 * @param color color of rectangle
	 */
	public void renderFilledRectangle(int x, int y, int width, int height,	Color color) {
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.fillRect(x, y, width, height);
	}
	
	/**
	 * Draws filled rectangle with specified position and size. Uses current color set in Graphics object.
	 * <p>
	 * Uses {@link java.awt.Graphics#fillRect(int, int, int, int) g.fillRect(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of rectangle
	 * @param height height of rectangle
	 * <p>
	 * @see #setColor(Color)
	 */
	public void renderFilledRectangle(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.fillRect(x, y, width, height);
	}
	
	/**
	 * Draws rectangle with specified position, size and color.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawRectangle(int, int, int, int) g.drawRectangle(x,y,width,height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of rectangle
	 * @param height height of rectangle
	 * @param color color of rectangle
	 */
	public void renderRectangle(int x, int y, int width, int height, Color color) {
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawRect(x, y, width, height);
	}
	
	/**
	 * Draws rectangle with specified position and size. Uses current color set in Graphics object.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawRect(int, int, int, int) g.drawRect(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of rectangle
	 * @param height height of rectangle
	 * <p>
	 * @see #setColor(Color)
	 */
	public void renderRectangle(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawRect(x, y, width, height);
	}

	/**
	 * Draws BufferedImage with specified position and size.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawImage(java.awt.Image, int, int,int,int, java.awt.image.ImageObserver) g.drawImage(img, x, y, width, height,null)} method.
	 * 
	 * @param img image which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of image
	 * @param height height of image
	 * <p>
	 * @see java.awt.image.BufferedImage
	 */
	public void renderImage(BufferedImage img, int x, int y, int width, int height) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawImage(img, x, y, width, height, null);
	}
	
	/**
	 * Draws BufferedImage with specified position and default size of image.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawImage(java.awt.Image, int, int,int,int, java.awt.image.ImageObserver) g.drawImage(img, x, y, img.getWidth(), img.getHeight(), null)} method.
	 * 
	 * @param img image which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate<br><br>
	 * <p>
	 * @see java.awt.image.BufferedImage
	 */
	public void renderImage(BufferedImage img, int x, int y) {
		if(!ignoreOffset){
			x -= xOffset; 
			y -= yOffset;
		}
		
		g.drawImage(img, x, y, img.getWidth(), img.getHeight(), null);
	}
	
	/**
	 * Draws String with specified position, font and color.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawString(String, int, int) g.drawString(text, x, y)} method.
	 * 
	 * @param text text which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param font font of text
	 * @param color color of text 
	 * <p>
	 * @see java.awt.Font
	 */
	public void renderString(String text, int x, int y, Font font, Color color) {
		setFont(font);
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawString(text, x, y);
	}
	
	/**
	 * Draws String with specified position and font. Uses current color set in Graphics object.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawString(String, int, int) g.drawString(text, x, y)} method.
	 * 
	 * @param text text which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param font font of text<br><br>
	 * <p>
	 * @see #setColor(Color)
	 * @see java.awt.Font
	 */
	public void renderString(String text, int x, int y, Font font) {
		setFont(font);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawString(text, x, y);
	}
	
	/**
	 * Draws String with specified position. Uses current color and font set in Graphics object.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawString(String, int, int) g.drawString(text, x, y)} method.
	 * 
	 * @param text text which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate<br><br>
	 * <p>
	 * @see #setColor(Color)
	 * @see #setFont(Font)
	 */
	public void renderString(String text, int x, int y) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawString(text, x, y);
	}
	
	/**
	 * Draws filled oval with specified position and size. Uses current color set in Graphics object.
	 * <p>
	 * Uses {@link #renderFilledOval(int, int, int, int) renderOval(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of oval
	 * @param height height of oval
	 * @param color color of filled oval<br><br>
	 * <p>
	 * @see #setColor(Color)
	 */
	public void renderFilledOval(int x, int y, int width, int height, Color color) {
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.fillOval(x, y, width, height);
	}
	
	/**
	 * Draws filled oval with specified position and size. Uses current color set in Graphics object.
	 * <p>
	 * Uses {@link java.awt.Graphics#fillOval(int, int, int, int) g.fillOval(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of oval
	 * @param height height of oval<br><br>
	 * <p>
	 * @see #setColor(Color)
	 */
	public void renderFilledOval(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.fillOval(x, y, width, height);
	}
	
	/**
	 * Draws oval of specified position, size and color.
	 * <p>
	 * Uses {@link java.awt.Graphics.#drawOval(int, int, int, int) g.drawOval(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of oval
	 * @param height height of oval
	 * @param color color of oval
	 */
	public void renderOval(int x, int y, int width, int height, Color color) {
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawOval(x, y, width, height);
	}
	
	/**
	 * Draws oval with specified position and size. Uses current color set in Graphics object.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawOval(int, int, int, int) g.drawOval(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of oval
	 * @param height height of oval
	 * <p>
	 * @see #setColor(Color)
	 */
	public void renderOval(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawOval(x, y, width, height);
	}

	/**
	 * Draws line from specified starting point to specified ending point with specified color.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawLine(int, int, int, int) g.drawLine(x, y, xa, ya)} method.
	 * 
	 * @param x horizontal coordinate of starting point
	 * @param y vertical coordinate of starting point
	 * @param xa horizontal coordinate of ending point
	 * @param ya vertical coordinate of ending point
	 * @param color color of line
	 */
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
	
	/**
	 * Draws line from specified starting point to specified ending point. Uses current color set in Graphics object.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawLine(int, int, int, int) g.drawLine(x, y, xa, ya)} method.
	 * 
	 * @param x horizontal coordinate of starting point
	 * @param y vertical coordinate of starting point
	 * @param xa horizontal coordinate of ending point
	 * @param ya vertical coordinate of ending point
	 * <p>
	 * @see #setColor(Color)
	 */
	public void renderLine(int x, int y, int xa, int ya) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
			xa -= xOffset;
			ya -= yOffset;
		}
		
		g.drawLine(x, y, xa, ya);
	}
	
	/**
	 * Draws filled arc with specified position, size, angles and color.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawFilledArc(int, int, int, int, int, int) g.drawFilledrArc(x, y, width, height, startAngle, arcAngle)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of filled arc
	 * @param height height offilled arc
	 * @param startAngle arc begins on this angle
	 * @param arcAngle arc finishes on this angle
	 * @param color color of filled  arc
	 */
	public void renderFilledArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color){
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.fillArc(x, y, width, height, startAngle, arcAngle);
	}
	
	/**
	 * Draws filled arc with specified position, size and angles. Uses current color set in Graphics object.
	 * <p>
	 * Uses {@link java.awt.Graphics#fillArc(int, int, int, int, int, int) g.fillArc(x, y, width, height, startAngle, arcAngle)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of arc
	 * @param height height of arc
	 * @param startAngle arc begins on this angle
	 * @param arcAngle arc finishes on this angle
	 * <p>
	 * 
	 * @see #setColor(Color)
	 * @see java.awt.Graphics#fillArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	 */
	public void renderFilledArc(int x, int y, int width, int height, int startAngle, int arcAngle){
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.fillArc(x, y, width, height, startAngle, arcAngle);
	}
	
	/**
	 * Draws arc with specified position, size, angles and color.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawArc(int, int, int, int, int, int) g.drawArc(x, y, width, height, startAngle, arcAngle)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of arc
	 * @param height height of arc
	 * @param startAngle arc begins on this angle
	 * @param arcAngle arc finishes on this angle
	 * @param color color of arc
	 * <p>
	 * @see java.awt.Graphics#drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	 */
	public void renderArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color){
		setColor(color);
		
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawArc(x, y, width, height, startAngle, arcAngle);
	}
	
	/**
	 * Draws arc with specified position, size and angles. Uses current color set in Graphics object.
	 * <p>
	 * Uses {@link java.awt.Graphics#drawArc(int, int, int, int, int, int) g.drawArc(x, y, width, height, startAngle, arcAngle)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of arc
	 * @param height height of arc
	 * @param startAngle arc begins on this angle
	 * @param arcAngle arc finishes on this angle
	 * <p>
	 * @see #setColor(Color)
	 * @see java.awt.Graphics#drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	 */
	public void renderArc(int x, int y, int width, int height, int startAngle,	int arcAngle) {
		if(!ignoreOffset){
			x -= xOffset;
			y -= yOffset;
		}
		
		g.drawArc(x, y, width, height, startAngle, arcAngle);
	}
	
	/**
	 * Draws specified {@link svk.sglubos.engine.gfx.sprite.Sprite sprite} at specifiied position.
	 * <p>
	 * Sprites are rendered by changing {@link #pixels pixels} at specified position to pixels from {@link svk.sglubos.engine.gfx.sprite.Sprite} object.
	 * To render scaled up sprite use {@link #renderSprite(Sprite, int, int, int)} method. 
	 * 
	 * @param sprite sprite object containing pixels which will be drawn
	 * @param xCoord horizontal coordinate where sprite will be drawn
	 * @param yCoord vertical coordinate where sprite will be drawn<br><br>
	 * <p>
	 * @see #renderSprite(Sprite, int, int, int)
	 * @see svk.sglubos.engine.gfx.sprite.Sprite
	 * @see #pixels
	 */
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
	
	/**
	 * Draws specified {@link svk.sglubos.engine.gfx.sprite.Sprite sprite} at specifiied position, with specified scale.
	 * <p>
	 * Sprites are rendered by changing {@link #pixels pixels} at specified position to pixels from {@link svk.sglubos.engine.gfx.sprite.Sprite} object.
	 * To render non scaled sprite use {@link #renderSprite(Sprite, int, int)}.<br>
	 * 
	 * @param sprite sprite object containing pixels which will be drawn
	 * @param xCoord horizontal coordinate where sprite will be drawn
	 * @param yCoord vertical coordinate where sprite will be drawn
	 * @param scale scale with sprite will be drawn<br><br>
	 * <p>
	 * @see #renderSprite(Sprite, int, int)
	 * @see svk.sglubos.engine.gfx.sprite.Sprite
	 * @see #pixels
	 */
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
	
	/**
	 * Prepares screen before rendering any content.
	 * <p>
	 * Initializes {@link #g Graphics object} with {@link java.awt.Graphics} object returned by method: {@link java.awt.image.BufferedImage#getGraphics() renderlayer.getGraphics()}.<br> 
	 * Also fills entire screen with  {@link #defaultScreenColor defaultScreenColor}. This method should be called every frame.
	 * 
	 * @see #g Graphics object
	 * @see #defaultScreenColor
	 * @see java.awt.image.BufferedImage#getGraphics()
	 */
	public void prepare(){
		g = renderLayer.getGraphics();
		g.setColor(defaultScreenColor);
		g.fillRect(0, 0, width, height);
	}
	
	/**
	 * Adds specified object which extends {@link svk.sglubos.engine.gfx.ScreenComponent ScreenComponent} to {@link java.util.ArrayList ArrayList} {@link #components}
	 * and prepares it to use by calling it's {@link svk.sglubos.engine.gfx.ScreenComponent#bind(Graphics, int[]) bind(g, pixels)} method with arguments:{@link Screen this},
	 * {@link #g screen graphics object} and {@link #pixels screen pixels}.
	 * The {@link svk.sglubos.engine.gfx.ScreenComponent ScreenComponent} object can be removed by {@link #removeScreenComponent(ScreenComponent)} method.
	 * 
	 * @param component component which will be added to list and prepared to be used
	 * <p>
	 * @see svk.sglubos.engine.gfx.ScreenComponent
	 * @see #addScreenComponent(ScreenComponent)
	 * @see #removeScreenComponent(ScreenComponent)
	 * @see #components
	 */
	public void addScreenComponent(ScreenComponent component) {
		components.add(component);
		g = renderLayer.getGraphics();
		component.bind(this, g, pixels);
	}
	
	/**
	 * Removes specified object which extends {@link svk.sglubos.engine.gfx.ScreenComponent ScreenComponent} from {@link java.util.ArrayList ArrayList} {@link #components} 
	 * and removes its functionality by calling it`s {@link svk.sglubos.engine.gfx.ScreenComponent#unbind() unbind()} method.
	 * 
	 * @param component component which will be removed from list and his ability to draw on screen will be removed
	 * <p>
	 * @see svk.sglubos.engine.gfx.ScreenComponent
	 * @see #addScreenComponent(ScreenComponent)
	 * @see #components
	 */
	public void removeScreenComponent(ScreenComponent component) {
		components.remove(component);
		component.unbind();
	}
	
	//TODO exception
	/**
	 * Sets color in {@link  #g Graphics object} to specified color.
	 * <p>
	 * <strong> If parameter color is null, message is printed and color in {@link  #g Graphics object} keeps the same. </strong>
	 * 
	 * @param color color which will be set to {@link  #g Graphics object}
	 * <p>
	 * @see java.awt.Color
	 * @see java.awt.Graphics
	 */
	public void setColor(Color color) {
		if (color == null) {
			MessageHandler.printMessage(MessageHandler.ERROR, "Screen color cannot be set to null");
			throw new IllegalArgumentException("Screen color cannot be set to null");
		}
		
		g.setColor(color);
	}
	
	//TODO Exception
	/**
	 * Sets font in {@link  #g Graphics object} to specified font.
	 * <p>
	 * <strong> If parameter font is null, is printed and font in {@link  #g Graphics object} keeps the same. </strong>
	 * 
	 * @param font font which will be set to {@link  #g Graphics object}
	 * <p>
	 * @see java.awt.Font
	 * @see java.awt.Graphics
	 */
	public void setFont(Font font) {
		if (font == null) {
			MessageHandler.printMessage(MessageHandler.ERROR, "Screen font cannot be set to null, font stays set to current font");
			return;
		}
		g.setFont(font);
	}
	
	/**
	 * Sets horizontal and vertical offset of screen to specified values.
	 * <p>
	 * Offseting means subtracting offsets from rendered content`s coordinates
	 * 
	 * @param xOffset Horizontal offset of screen (offset on x axis)
	 * @param yOffset Vertical offset of screen (offset on y axis)<br><br>
	 * <p>
	 * @see #xOffset
	 * @see #yOffset
	 * @see #ignoreOffset
	 */
	public void setOffset(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	/**
	 * Sets {@link #ignoreOffset} to specified value.
	 * <p>
	 * Sets if screen offset is ignored while rendering content.
	 * 
	 * @param ignore if true offset of screen is ignored else position of rendered content is recalculated with screen offset
	 * <p>
	 * @see #xOffset
	 * @see #yOffset
	 * @see #setOffset(int,int) setOffset(xOffset, yOffset)
	 */
	public void setIngoreOffset(boolean ignore){
		this.ignoreOffset = ignore;
	}
	
	/**
	 * @return returns {@link #g Graphics object} which is used to draw on {@link #renderLayer}
	 * <p>
	 * @see java.awt.Graphics
	 */
	public Graphics getGraphics() {
		return g;
	}
	
	/**
	 * Disposes {@link #g Graphics object} which is used to draw on {@link #renderLayer}.
	 * This method should be called at the end of rendering content for better performance.
	 * <p>
	 * @see java.awt.Graphics
	 */
	public void disposeGraphics() {
		g.dispose();
	}
	
	/**
	 * @return width of screen in pixels
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return width of screen in pixels
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return horizontal screen offset
	 */
	public int getXOffset(){
		return xOffset;
	}
	
	/**
	 * @return vertical screen offset
	 */
	public int getYOffset(){
		return yOffset;
	}
	
	/**
	 * @return <code>BufferedImage</code>{@link #renderLayer} which contains all rendered graphics.
	 * <p>
	 * @see java.awt.image.BufferedImage
	 */
	public BufferedImage getRenderLayer() {
		return renderLayer;
	}
	
	//TODO ducumment
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.appendClassDataBracket(getClass(), hashCode());
		ret.appendTab();
		ret.append("width = " + width);
		ret.append(" height = " + height);
		ret.append(" ignoreOffset = " + ignoreOffset);
		ret.append(" xOffset = " + xOffset);
		ret.append(" yOffset = " + yOffset);
		ret.appendLineSeparator();
		
		ret.appendObjectToStringTabln("defaultScreenColor = ", defaultScreenColor);
		ret.appendObjectToStringTabln("g = ",g);
		ret.appendObjectToStringTabln("renderLayer = ", renderLayer);
		ret.appendObjectToStringTabln("pixels = ", pixels);
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
