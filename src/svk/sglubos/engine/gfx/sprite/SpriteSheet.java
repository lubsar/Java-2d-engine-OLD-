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
package svk.sglubos.engine.gfx.sprite;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import svk.sglubos.engine.utils.debug.DebugStringBuilder;

/**
 * SpriteSheet class provides ability to create multiple {@link svk.sglubos.engine.gfx.sprite.Sprite sprites} from single {@link java.awt.image.BufferedImage BufferedImage}.<br>
 * Before getting sprites, sprites must be created, if you use this {@link #SpriteSheet(BufferedImage, int, int) constructor}, sprites of specified size are automatically created.
 * But if used this {@link #SpriteSheet(BufferedImage) constructor}, sprites of specified size must be manually created by this method {@link #createSprites(int, int)}.<br>
 * <p>
 * <h1>The SpriteSheet class provides: </h1><br>
 * Ability to get single sprite from specified position and size by {@link #getSprite(int, int, int, int) getSprite(x, y, width, heihgt)} method.<br>
 * Ability to get array of sprites created by {@link #SpriteSheet(BufferedImage, int, int) this constructor} or {@link #createSprites(int, int) createSprites(spriteWidth, spriteHeight)} method.<br>
 * Ability to get sub-image from specified position and size of {@link java.awt.image.BufferedImage BufferedImage} from which are sprites created by {@link #getSubImage(int, int, int, int) getSubimage(x, y, width, height)}.<br>
 *
 * @see svk.sglubos.engine.gfx.sprite.Sprite
 * @see #createSprites(int, int)
 * @see #getSprites()
 * @see #getSprite(int, int, int, int)
 * @see #getSubImage(int, int, int, int)
 */
public class SpriteSheet {
	/**
	 * Width of {@link #image} which sprites and sub images are created from.<br>
	 * This variable is initialized in {@link #SpriteSheet(BufferedImage) constructor}.
	 * 
	 * @see #SpriteSheet(BufferedImage)
	 * @see #image
	 */
	private int width;
	
	/**
	 * Height of {@link #image} which sprites and sub images are created from.<br>
	 * This variable is initialized in {@link #SpriteSheet(BufferedImage) constructor}.
	 * 
	 * @see #SpriteSheet(BufferedImage)
	 * @see #image
	 */
	private int height;
	
	/**
	 * {@link java.awt.image.BufferedImage BufferedImage} object which sprites and sub images are created from.<br>
	 * This object is initialized in {@link #SpriteSheet(BufferedImage) constructor}.
	 * 
	 * 	@see #SpriteSheet(BufferedImage)
	 *  @see #createSprites(int, int)
	 *  @see #getSprite(int, int, int, int)
	 *  @see #getSprites()
	 *  @see #getSubImage(int, int, int, int)
	 */
	private BufferedImage image; 
	
	/**
	 * Array of sprites created from {@link #image}, which are initialized in {@link #SpriteSheet(BufferedImage, int, int ) this construcotr} or by {@link #createSprites(int, int) createSprites(spriteWidth,spriteHeight)} method.
	 * Can be obtained by {@link #getSprites()} method.
	 * 
	 * @see #getSprites()
	 * @see #SpriteSheet(BufferedImage, int, int)
	 */
	private Sprite[] sprites = null;
	
	/**
	 * Constructs new <code>SpriteSheet</code> from specified {@link java.awt.image.BufferedImage BufferedImage}.<br>
	 * If parameter <code>image</code> is <code>null</code>, {@link java.lang.IllegalArgumentException IllegalArgumentException} is thrown.
	 * This constructor does not handle creating of {@link #sprites}, use {@link #createSprites(int, int) createSprites(spriteWidth, spriteHeight)} to create sprites.<br>
	 * <p>
	 * <h1>Initializes:</h1><br>
	 * {@link java.awt.image.BufferedImage BufferedImage} {@link #image} with image passed in constructor.<br>
	 * Integer {@link #width} with width of image passed in constructor. <br> 
	 * Integer {@link #height} with height of image passed in constructor. <br> 
	 * 
	 * @param image {@link java.awt.image.BufferedImage BufferedImage} used to create sprites and sub-images.<br><br>
	 * 
	 * @see svk.sglubos.engine.gfx.sprite.Sprite
	 * @see #createSprites(int, int)
	 * 
	 * @throws  java.lang.IllegalArgumentException IllegalArgumentException if image is <code>null</code><br><br>
	 */
	public SpriteSheet(BufferedImage image) {
		if(image == null) {
			throw new IllegalArgumentException("BufferedImage can not be null");
		}
		
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	/**
	 * Constructs new <code>SpriteSheet</code> from specified {@link java.awt.image.BufferedImage BufferedImage} and creates {@link #sprites} of specified with and height.
	 * Uses {@link #SpriteSheet(BufferedImage)} constructor and {@link #createSprites(int, int) createSprites(spriteWidth, spriteHeight)} method.<br>
	 * <h1>Initializes:</h1>
	 * {@link svk.sglubos.engine.gfx.sprite.Sprite Sprite} array {@link #sprites}, which contains all sprites created by {@link #createSprites(int, int) screateSprites(spriteWidth, spriteHeight)} method.<br><br>
	 * 
	 * @param image {@link java.awt.image.BufferedImage BufferedImage} which are create sprites and sub-images from
	 * @param spriteWidth width of sprite which will be created
	 * @param spriteHeight height of sprite which will be created<br><br>
	 * 
	 * @see #SpriteSheet(BufferedImage)
	 * @see svk.sglubos.engine.gfx.sprite.Sprite
	 * @see #createSprites(int, int)
	 * @see #getSprites()
	 */
	public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeight) {
		this(image);
		createSprites(spriteWidth, spriteHeight);
	}
	
	/**
	 * Creates sub-image from specified starting point on {@link #image} of specified size.
	 * 
	 * @param x horizontal coordinate of starting point from sub-image will be got
	 * @param y vertical coordinate of starting point from sub-image will be got
	 * @param width width of sub-image
	 * @param height height of sub-image<br><br>
	 *
	 * @return sub-image from {@link #image} from specified position and specified size
	 */
	public BufferedImage getSubImage(int x, int y, int width, int height) {
		BufferedImage image = null;
		try{
			image = this.image.getSubimage(x, y, width, height);			
		}catch (RasterFormatException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	/**
	 * Creates new {@link svk.sglubos.engine.gfx.sprite.Sprite} from specified starting point on {@link #image} of specified size.
	 * 
	 * @param x horizontal coordinate of starting point from sprite will be got
	 * @param y vertical coordinate of starting point from sprite will be got
	 * @param width width of sprite
	 * @param height height of sprite<br><br>
	 *
	 * @return sprite from {@link #image} from specified position and specified size
	 */
	public Sprite getSprite(int x, int y, int width, int height) {
		return  new Sprite(width, height, image.getRGB((x * width), (y * height) , width, height,null, 0, width));
	}
	
	/**
	 * If {@link #sprites} is <code>null</code>, {@link java.lang.NullPointerException NullPointerException} is thrown.
	 * 
	 * @return sprites created by {@link #createSprites(int, int)} method <br><br>
	 * @throws java.lang.IllegalStateException IllegalStateException if {@link #sprites} were not created.
	 */
	public Sprite[] getSprites() {
		if(sprites == null){
			throw new IllegalStateException("Sprites were not created");
		}
		return sprites;
	}
	
	/**
	 * Initializes array of {@link svk.sglubos.engine.gfx.sprite.Sprite Sprite} objects {@link #sprites}.<br>
	 * These sprite objects contains pixels obtained from {@link #image sprite sheet image file}.
	 * Each {@link svk.sglubos.engine.gfx.sprite.Sprite Sprite} has size specified by arguments of this method.
	 * <p>
	 * Maximum number of sprites with specified size is created, first sprite (sprite at index 0) has all pixels from 0, 0 to spriteWidth-1, spriteHeight-1.
	 * Sprites are stored in array from left to right and from top to bottom. (sprite at index 0 is first sprite in top line and sprite at index sprites.length-1 is the last sprite in bottom line).
	 * <p>
	 * The created sprites are returned by {@link #getSprites()} method.
	 * 
	 * @param spriteWidth width of sprites which will be created
	 * @param spriteHeight height of sprites which will be created
	 * 
	 * @see #sprites
	 * @see #getSprites()
	 */
	public void createSprites(int spriteWidth, int spriteHeight) {
		int horizontalSprites = this.width / spriteWidth;
		int verticalSprites = this.height / spriteHeight;
		
		Sprite[] sprites = new Sprite[this.width / spriteWidth * this.height / spriteHeight];
		
		for(int y = 0; y < verticalSprites; y++) {
			for(int x = 0; x < horizontalSprites; x++) {
				sprites[x + y * horizontalSprites] = new Sprite(spriteWidth, spriteHeight, image.getRGB((x * spriteWidth), (y * spriteHeight), spriteWidth, spriteHeight, null, 0, spriteWidth));
			}
		}
		
		this.sprites = sprites;
	}
	
	//TODO documment
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.append(getClass(), hashCode());
		ret.addLayer();
		ret.append("width", width);
		ret.append("height", height);
		ret.append(image, "image");
		ret.append(sprites, "sprites");
		ret.removeLayer();
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}
