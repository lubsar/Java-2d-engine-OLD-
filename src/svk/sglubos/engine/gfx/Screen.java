package svk.sglubos.engine.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import svk.sglubos.engine.gfx.sprite.Sprite;
import svk.sglubos.engine.utils.MessageHandler;

/**
 * Handles rendering of basic shapes, images, texts, sprites and supports implementation of own rendering.<br>
 * <code>BufferedImage</code> {@link #renderLayer} contains all rendered graphics
 * and this <code>BufferedImage</code> is returned by method <code>getRenderLayer()</code>.
 *<p>
 * A java.awt.Graphics object which can draw on renderLayer is returned by method <code>getGraphics()</code>.
 * </p>
 * <p>
 * Before rendering game content every frame you need to call <code>prepare()</code> method, which creates new Graphics object
 * and fills entire screen with defaultColor passed in constructor of this class.  
 * After rendering game content you need to call <code>disposeGraphics()</code> method which disposes Graphics object to release system resources.
 * </p>
 * <h1>Example:render method called every frame </h1>
 * <p>
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
 * <h1>Rendering expansion </h1>
 * The <code> Screen</code> also provides ability to use your own rendering by {@link svk.sglubos.engine.gfx.ScreenComponent ScreenComponent abstract class}.<br>
 * The <code> ScreenComponent </code> class provides ability to access {@link #g screen graphics object} but also {@link #pixels screen pixels array}, and also to <code> screen class instance to which is added. <br>
 * To make your screen component communicate with screen, add object of your component to the {@link #components ArrayList of ScreenComponents} by using {@link #addScreenComponent(ScreenComponent)} method.<br>
 * To remove your screen component use method {@link #removeScreenComponent(ScreenComponent)}.<br>   
 * <p>
 * @see java.awt.Graphics
 * @see svk.sglubos.engine.gfx.sprite.Sprite
 * @see #setOffset(int, int) offseting of screen
 * 
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
	 * Horizontal offset of screen in pixels. Default value 0.<br>
	 * Offset can be set by {@link #setOffset(int, int) setOffset(xOffset, yOffset)} method.
	 * 
	 * @see #setOffset(int xOffset,int yOffset)
	 */
	protected int xOffset = 0;
	
	/**
	 * Vertical offset of screen in pixels. Default value 0.<br>
	 * Offset can be set by {@link #setOffset(int, int) setOffset(xOffset, yOffset)} method.
	 * 
	 * @see #setOffset(int xOffset,int yOffset)
	 */
	protected int yOffset = 0;
	
	/**
	 * If true offsets are ignored when rendering content, else position of rendered content is offset. <br>
	 * Can be set by {@link #setIngoreOffset(boolean) setIgnoreOffset(boolean ignoreOffset)} method.
	 * 
	 *  @see #xOffset xOffset
	 *  @see #yOffset yOffset
	 *  @see #setOffset(int, int) setOffset(xOffset, yOffset)
	 */
	protected boolean ignoreOffset;
	
	/**
	 * Color used in {@link #prepare()} method, entire screen is filled with this color when {@link #prepare()} is called.<br>
	 * The value of this color is initialized in constructor and is last passed parameter in {@link #Screen(int, int, Color) constructor}.
	 */
	protected Color defaultScreenColor;
	
	/**
	 * BufferedImage which contains all rendered graphics. To display your rendered graphics display this image.<br>
	 * The renderLayer is initialized in {@link #Screen(int, int, Color) constructor}.<br>
	 * This image is returned by {@link #getRenderLayer()} method. <br>
	 */
	protected BufferedImage renderLayer;
	
	/**
	 * {@link java.awt.Graphics} object which provides ability to draw on renderLayer.<br>
	 * This object is initialized in method {@link #prepare()}. <br>
	 * It should be initialized every frame before rendering and disposed at the end of rendering for better performance. <br>
	 * 
	 * @see #prepare()
	 * @see #renderLayer
	 * @see #disposeGraphics()
	 */
	protected Graphics g;
	
	/**
	 * Array of screen pixels, changing values changes colors of pixels of screen. <br>
	 * This array used for example to render {@link svk.sglubos.engine.gfx.sprite.Sprite sprites} and is passed in initialization of screen {@link #components components}<br>
	 * This array is initialized in {@link #Screen(int, int, Color) constructor} of this class.
	 * 
	 *@see svk.sglubos.engine.gfx.sprite.Sprite 
	 *@see svk.sglubos.engine.gfx.ScreenComponent
	 */
	protected int[] pixels;
	
	/**
	 * {@link java.util.List} object which stores all {@link svk.sglubos.engine.gfx.ScreenComponent} objects which have ability to use {@link #g graphics object} and change {@link #pixels Screen pixels} on their own. <br>
	 * Objects can be added by {@link #addScreenComponent(ScreenComponent)} method and removed by {@link #removeScreenComponent(ScreenComponent)} method. <br>
	 * If component is added component gains ability to use {@link #g Graphics object} and change {@link #pixels}. If that component is removed by {@link #removeScreenComponent(ScreenComponent)} method, it looses this abilities. 
	 *  
	 *  @see svk.sglubos.engine.gfx.ScreenComponent
	 *  @see #addScreenComponent(ScreenComponent)
	 *  @see #removeScreenComponent(ScreenComponent)
	 */
	protected List<ScreenComponent> components = new ArrayList<ScreenComponent>();
	
	/**
	 * Constructs new object of Screen class.<br>
	 * 
	 * <h1>Initializes:</h1><br> 
	 * <code>BufferedImage {@link #renderLayer}</code> with arguments: <code>width</code>, <code>height</code> and <code>BufferedImage.TYPE_IN_RGB</code>.<br>
	 * <code>Int array {@link #pixels}</code> with data got from data buffer of Raster of renderLayer: 
	 * <br><code>((DataBufferInt) renderLayer.getRaster().getDataBuffer()).getData()</code> <br>
	 * <p>
	 * <code>Int {@link #width}</code> with value of passed parameter width <br>
	 * <code>Int {@link #height}</code> with value of passed parameter height <br>
	 * <code>Color </code> {@link #defaultScreenColor} with value of passed parameter defaultColor
	 * </p> 
	 * <p>
	 * @param width (width of screen)
	 * @param height (height of screen)
	 * @param defaultColor (entire screen is filled with this color in method <code>prepare()</code>)
	 * </p>
	 * 
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
	 * Draws filled rectangle with specified position, size and color.<br>
	 * Uses {@link #renderFilledRectangle(int, int, int, int) renderFilledRectangle(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of rectangle
	 * @param height height of rectangle
	 * @param color color of rectangle
	 */
	public void renderFilledRectangle(int x, int y, int width, int height,	Color color) {
		setColor(color);
		renderFilledRectangle(x, y, width, height);
	}
	
	/**
	 * Draws filled rectangle with specified position and size. Uses current color set in Graphics object.<br>
	 * Uses {@link java.awt.Graphics#fillRect(int, int, int, int) g.fillRect(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of rectangle
	 * @param height height of rectangle<br><br>
	 * 
	 * @see #setColor(Color)
	 */
	public void renderFilledRectangle(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.fillRect(x, y, width, height);
	}
	
	/**
	 * Draws rectangle with specified position, size and color. <br>
	 * Uses {@link #renderRectangle(int, int, int, int) renderRectangle(x,y,width,height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of rectangle
	 * @param height height of rectangle
	 * @param color color of rectangle
	 */
	public void renderRectangle(int x, int y, int width, int height, Color color) {
		setColor(color);
		renderRectangle(x, y, width, height);
	}
	
	/**
	 * Draws rectangle with specified position and size. Uses current color set in Graphics object.<br>
	 * Uses {@link java.awt.Graphics#drawRect(int, int, int, int) g.drawRect(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of rectangle
	 * @param height height of rectangle<br><br>
	 * 
	 * @see #setColor(Color)
	 */
	public void renderRectangle(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.drawRect(x, y, width, height);
	}

	/**
	 * Draws BufferedImage with specified position and size. <br>
	 * Uses {@link java.awt.Graphics#drawImage(java.awt.Image, int, int,int,int, java.awt.image.ImageObserver) g.drawImage(img, x, y, width, height,null)} method.
	 * 
	 * @param img image which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of image
	 * @param height height of image<br><br>
	 * 
	 * @see java.awt.image.BufferedImage
	 */
	public void renderImage(BufferedImage img, int x, int y, int width, int height) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.drawImage(img, x, y, width, height, null);
	}
	
	/**
	 * Draws BufferedImage with specified position and default size of image. <br>
	 * Uses {@link java.awt.Graphics#drawImage(java.awt.Image, int, int,int,int, java.awt.image.ImageObserver) g.drawImage(img, x, y, img.getWidth(), img.getHeight(), null)} method.
	 * 
	 * @param img image which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate<br><br>
	 *
	 * @see java.awt.image.BufferedImage
	 */
	public void renderImage(BufferedImage img, int x, int y) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.drawImage(img, x, y, img.getWidth(), img.getHeight(), null);
	}
	
	/**
	 * Draws String with specified position, font and color.<br>
	 * Uses {@link #renderString(String, int, int, Font) renderString(text, x, y, font)} method.
	 * 
	 * @param text text which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param font font of text
	 * @param color color of text <br>
	 * 
	 *@see java.awt.Font
	 */
	public void renderString(String text, int x, int y, Font font, Color color) {
		setColor(color);
		renderString(text, x, y, font);
	}
	
	/**
	 * Draws String with specified position and font. Uses current color set in Graphics object.<br>
	 * Uses {@link #renderString(String, int, int) renderString(text, x, y)} method.
	 * 
	 * @param text text which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param font font of text<br><br>
	 * 
	 * @see #setColor(Color)
	 * @see java.awt.Font
	 */
	public void renderString(String text, int x, int y, Font font) {
		setFont(font);
		renderString(text,x,y);
	}
	
	/**
	 * Draws String with specified position. Uses current color and font set in Graphics object.<br>
	 * Uses {@link java.awt.Graphics2D#drawString(String, int, int) g.drawString(text, x, y)} method.
	 * 
	 * @param text text which will be drawn
	 * @param x horizontal coordinate
	 * @param y vertical coordinate<br><br>
	 * 
	 * @see #setColor(Color)
	 * @see #setFont(Font)
	 */
	public void renderString(String text, int x, int y) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.drawString(text, x,y);
	}
	
	/**
	 * Draws oval of specified position, size and color.<br>
	 * Uses {@link #renderOval(int, int, int, int) renderOval(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of oval
	 * @param height height of oval
	 * @param color color of oval
	 */
	public void renderOval(int x, int y, int width, int height, Color color) {
		setColor(color);
		renderOval(x, y, width, height);
	}
	
	/**
	 * Draws oval with specified position and size. Uses current color set in Graphics object<br>
	 * Uses {@link java.awt.Graphics#drawOval(int, int, int, int) g.drawOval(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of oval
	 * @param height height of oval
	 * 
	 * @see #setColor(Color)
	 */
	public void renderOval(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.drawOval(x,y, width, height);
	}
	
	/**
	 * Draws filled oval with specified position and size. Uses current color set in Graphics object<br>
	 * Uses {@link #renderFilledOval(int, int, int, int) renderOval(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of oval
	 * @param height height of oval
	 * @param color color of filled oval<br><br>
	 * 
	 * @see #setColor(Color)
	 */
	public void renderFilledOval(int x, int y, int width, int height, Color color) {
		setColor(color);
		renderFilledOval(x, y, width, height);
	}
	
	/**
	 * Draws filled oval with specified position and size. Uses current color set in Graphics object<br>
	 * Uses {@link java.awt.Graphics#fillOval(int, int, int, int) g.fillOval(x, y, width, height)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of oval
	 * @param height height of oval<br><br>
	 * 
	 * @see #setColor(Color)
	 */
	public void renderFilledOval(int x, int y, int width, int height) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.fillOval(x, y, width, height);
	}

	/**
	 * Draws line from specified starting point to specified ending point with specified color.<br>
	 * Uses {@link #renderLine(int, int, int, int) renderLine(x, y, xa, ya)} method.
	 * 
	 * @param x horizontal coordinate of starting point
	 * @param y vertical coordinate of starting point
	 * @param xa horizontal coordinate of ending point
	 * @param ya vertical coordinate of ending point
	 * @param color color of line
	 */
	public void renderLine(int x, int y, int xa, int ya, Color color) {
		setColor(color);
		renderLine(x, y, xa, ya);
	}
	
	/**
	 * Draws line from specified starting point to specified ending point. Uses current color set in Graphics object.<br>
	 * Uses {@link java.awt.Graphics#drawLine(int, int, int, int) g.drawLine(x, y, xa, ya)} method.
	 * 
	 * @param x horizontal coordinate of starting point
	 * @param y vertical coordinate of starting point
	 * @param xa horizontal coordinate of ending point
	 * @param ya vertical coordinate of ending point <br><br>
	 * 
	 * @see #setColor(Color)
	 */
	public void renderLine(int x, int y, int xa, int ya) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
			xa = offsetCoordinate(xa,xOffset);
			ya = offsetCoordinate(ya,yOffset);
		}
		g.drawLine(x, y, xa, ya);
	}
	
	/**
	 * Draws filled arc with specified position, size, angles and color.<br>
	 * Uses {@link #renderFilledArc(int, int, int, int, int, int) rendeFilledrArc(x, y, width, height, startAngle, arcAngle)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of filled arc
	 * @param height height offilled arc
	 * @param startAngle arc begins on this angle
	 * @param arcAngle arc finishes on this angle
	 * @param color color of filled  arc <br><br>
	 * 
	 * @see java.awt.Graphics#fillArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	 */
	public void renderFilledArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color){
		setColor(color);
		renderFilledArc(x, y, width, height, startAngle, arcAngle, color);
	}
	
	/**
	 * Draws filled arc with specified position, size and angles. Uses current color set in Graphics object.<br>
	 * Uses {@link java.awt.Graphics#fillArc(int, int, int, int, int, int) g.fillArc(x, y, width, height, startAngle, arcAngle)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of arc
	 * @param height height of arc
	 * @param startAngle arc begins on this angle
	 * @param arcAngle arc finishes on this angle<br><br>
	 * 
	 * @see #setColor(Color)
	 * @see java.awt.Graphics#fillArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	 */
	public void renderFilledArc(int x, int y, int width, int height, int startAngle, int arcAngle){
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.fillArc(x, y, width, height, startAngle, arcAngle);
	}
	
	/**
	 * Draws arc with specified position, size, angles and color.<br>
	 * Uses {@link #renderArc(int, int, int, int, int, int) renderArc(x, y, width, height, startAngle, arcAngle)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of arc
	 * @param height height of arc
	 * @param startAngle arc begins on this angle
	 * @param arcAngle arc finishes on this angle
	 * @param color color of arc<br><br>
	 * 
	 * @see java.awt.Graphics#drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	 */
	public void renderArc(int x, int y, int width, int height, int startAngle, int arcAngle, Color color){
		setColor(color);
		renderArc(x, y, width, height, startAngle, arcAngle);
	}
	
	/**
	 * Draws arc with specified position, size and angles. Uses current color set in Graphics object.<br>
	 * Uses {@link java.awt.Graphics#drawArc(int, int, int, int, int, int) g.drawArc(x, y, width, height, startAngle, arcAngle)} method.
	 * 
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param width width of arc
	 * @param height height of arc
	 * @param startAngle arc begins on this angle
	 * @param arcAngle arc finishes on this angle<br><br>
	 * 
	 * @see #setColor(Color)
	 * @see java.awt.Graphics#drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	 */
	public void renderArc(int x, int y, int width, int height, int startAngle,	int arcAngle) {
		if(!ignoreOffset){
			x = offsetCoordinate(x,xOffset);
			y = offsetCoordinate(y,yOffset);
		}
		g.drawArc(x, y, width, height, startAngle, arcAngle);
	}
	
	//TODO documentation and scaling
	public void renderSprite(Sprite sprite, int xCoord, int yCoord){
		int[] spritePixels = sprite.getPixels();
		
		if(!ignoreOffset){
			xCoord = offsetCoordinate(xCoord,xOffset);
			yCoord = offsetCoordinate(yCoord,yOffset);
		}
		
		int spriteWidth = sprite.getWidth();
		int spriteHeight = sprite.getHeight();
		
		int pixelX;
		int pixelY;
		
		for(int y = 0; y < spriteHeight; y++){
			pixelY = y + yCoord;
			for(int x = 0; x < spriteWidth; x++){
				pixelX = x + xCoord;
				if(spritePixels[x + y * spriteWidth] == 0){
					continue;
				}
				if(pixelY > 0 && pixelY < this.height && pixelX > 0 && pixelX < this.width ){
					this.pixels[pixelX + pixelY * this.width] = spritePixels[x + y * spriteWidth];
				}
			}
		}
	}
	
	/**
	 * Prepares screen before rendering any content.<br>
	 * Initializes <code>Graphics</code> object with Graphics object returned by this method: <code>renderLayer.getGraphics()</code>. <br> 
	 * Also fills entire screen with  {@link #defaultScreenColor defaultScreenColor}. This method should be called every frame.
	 * 
	 * @see #g Graphics object
	 * @see #defaultScreenColor
	 * 
	 */
	public void prepare(){
		g = renderLayer.getGraphics();
		g.setColor(defaultScreenColor);
		g.fillRect(0, 0, width, height);
	}
	
	/**
	 * Adds specified object which extends {@link svk.sglubos.engine.gfx.ScreenComponent ScreenComponent} to <code>ArrayList</code> {@link #components} 
	 * and prepares it to use by calling it`s {@link svk.sglubos.engine.gfx.ScreenComponent#bind(Graphics, int[]) bind(g, pixels)} method with arguments:{@link Screen this}, {@link #g screen graphics object} and {@link #pixels screen pixels}.<br>
	 * The <code>ScreenComponent</code> object can be removed by {@link #removeScreenComponent(ScreenComponent)} method.
	 * 
	 * @param component component which will be added to list and prepared to be used <br><br>
	 * 
	 * @see svk.sglubos.engine.gfx.ScreenComponent
	 */
	public void addScreenComponent(ScreenComponent component) {
		components.add(component);
		component.bind(this, g, pixels);
	}
	
	/**
	 * Removes specified object which extends {@link svk.sglubos.engine.gfx.ScreenComponent ScreenComponent} from <code>ArrayList</code> {@link #components} 
	 * and removes its functionality by calling it`s {@link svk.sglubos.engine.gfx.ScreenComponent#unbind() unbind()} method.<br>
	 * 
	 * @param component component which will be removed from list and his ability to draw on screen will be removed<br><br>
	 * 
	 * @see svk.sglubos.engine.gfx.ScreenComponent
	 */
	public void removeScreenComponent(ScreenComponent component) {
		components.remove(component);
		component.unbind();
	}
	
	/**
	 * Offsets specified coordinate by specified value. <br>
	 * Offseting coordinate means that coordinate is subtracted by offset.
	 * 
	 * @param coord coordinate which will be offset
	 * @param offset value which is coordinate offset<br><br>
	 * 
	 * @return coord - offset <br><br>
	 * 
	 * @see #xOffset
	 * @see #yOffset
	 * @see #ignoreOffset
	 */
	protected int offsetCoordinate(int coord, int offset){
		return coord - offset;
	}
	
	/**
	 * Sets color in {@link  #g Graphics object} to specified color.<br>
	 * <strong> If parameter color is null, message is printed and color in {@link  #g Graphics object} keeps the same. </strong>
	 * 
	 * @param color color which will be set to {@link  #g Graphics object}<br><br>
	 * 
	 * @see java.awt.Color
	 * @see java.awt.Graphics
	 */
	public void setColor(Color color) {
		if (color == null) {
			MessageHandler.printMessage(MessageHandler.ERROR, "Screen color cannot be set to null, color stays seto to current color");
			return;
		}
		g.setColor(color);
	}
	
	/**
	 * Sets font in {@link  #g Graphics object} to specified font.<br>
	 * <strong> If parameter font is null, is printed and font in {@link  #g Graphics object} keeps the same. </strong>
	 * 
	 * @param font font which will be set to {@link  #g Graphics object}<br><br>
	 * 
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
	 * Sets horizontal and vertical offset of screen to specified values. <br>
	 * Offseting means subtracting offsets from rendered content`s coordinates <br>
	 * 
	 * @param xOffset Horizontal offset of screen (offset on x axis)
	 * @param yOffset Vertical offset of screen (offset on y axis)<br><br>
	 * 
	 * @see #xOffset
	 * @see #yOffset
	 * @see #ignoreOffset
	 */
	public void setOffset(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	/**
	 * Sets {@link #ignoreOffset} to specified value. <br>
	 * Sets if screen offset is ignored while rendering content.
	 * 
	 * @param ignore if true offset of screen is ignored else position of rendered content is recalculated with screen offset<br><br>
	 * 
	 * @see #xOffset
	 * @see #yOffset
	 * @see #setOffset(int,int) setOffset(xOffset, yOffset)
	 */
	public void setIngoreOffset(boolean ignore){
		this.ignoreOffset = ignore;
	}
	
	/**
	 * @return returns {@link #g Graphics object} which is used to draw on {@link #renderLayer}
	 * 
	 * @see java.awt.Graphics
	 */
	public Graphics getGraphics() {
		return g;
	}
	
	/**
	 * Disposes {@link #g Graphics object} which is used to draw on {@link #renderLayer}.
	 * This method should be called at the end of rendering content for better performance.<br><br>
	 * 
	 * @see java.awt.Graphics
	 */
	public void disposeGraphics() {
//		ImageCapabilities im = renderLayer.getCapabilities(null);
//		System.out.println(im.isAccelerated());
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
	 * @return <code>BufferedImage</code>{@link #renderLayer} which contains all rendered graphics.<br><br>
	 * 
	 * @see java.awt.image.BufferedImage
	 */
	public BufferedImage getRenderLayer() {
		return renderLayer;
	}
}
