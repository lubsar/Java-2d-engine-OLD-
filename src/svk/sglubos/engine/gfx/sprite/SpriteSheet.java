/*
 *	Copyright 2015 Ľubomír Hlavko
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

public class SpriteSheet {
	private int width;
	private int height;
	
	private BufferedImage image; 
	
	private Sprite[] sprites = null;
	
	public SpriteSheet(BufferedImage image) {
		if(image == null) {
			throw new IllegalArgumentException("BufferedImage can not be null");
		}
		
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeight) {
		this(image);
		createSprites(spriteWidth, spriteHeight);
	}
	
	public BufferedImage getSubImage(int x, int y, int width, int height) {
		BufferedImage image = null;
		try{
			image = this.image.getSubimage(x, y, width, height);			
		}catch (RasterFormatException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	public Sprite getSprite(int x, int y, int width, int height) {
		return  new Sprite(width, height, image.getRGB((x * width), (y * height) , width, height,null, 0, width));
	}
	
	public Sprite[] getSprites() {
		if(sprites == null){
			throw new IllegalStateException("Sprites were not created");
		}
		return sprites;
	}
	
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
}
