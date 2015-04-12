package svk.sglubos.engine.gfx.animation;

import svk.sglubos.engine.gfx.Screen;
import svk.sglubos.engine.gfx.sprite.Sprite;
import svk.sglubos.engine.gfx.sprite.SpriteSheet;
import svk.sglubos.engine.utils.debug.DebugStringBuilder;
//TODO document
/**
 *<code>Sprite Animation</code> class provides ability to create basic animation from {@link svk.sglubos.engine.gfx.sprite.Sprite Sprite} objects.
 *This class inherits from {@link svk.sglubos.engine.gfx.animation.Animation Animation} class.
 */

public class SpriteAnimation extends Animation {
	protected Sprite[] sprites;
	//fix NullPointer
	public SpriteAnimation(Sprite[] sprites, double frameDelay, byte timeFormat) {
		super(frameDelay, timeFormat, sprites.length);			
		this.sprites = sprites;
	}
	
	public SpriteAnimation(SpriteSheet spriteSheet, double frameDelay, int startFrame, int endFrame, byte timeFormat) {
		super(frameDelay, startFrame, endFrame, spriteSheet.getSprites().length, timeFormat);
		this.sprites = spriteSheet.getSprites();
	}

	@Override
	public void render(Screen screen, int x, int y) {
		screen.renderSprite(sprites[currentFrame], x, y);
	}
	
	public String toString() {
		DebugStringBuilder ret = new DebugStringBuilder();
		
		ret.appendClassDataBracket(getClass(), hashCode());
		ret.appendTabln(super.toString());
		ret.appendObjectToStringTabln("sprites = ", sprites);
		ret.appendCloseBracket();
		
		return ret.getString();
	}
}