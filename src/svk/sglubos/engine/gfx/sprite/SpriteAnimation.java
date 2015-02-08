package svk.sglubos.engine.gfx.sprite;

import svk.sglubos.engine.gfx.Animation;
import svk.sglubos.engine.gfx.Screen;

//TODO Documentaton

public class SpriteAnimation extends Animation {
	protected Sprite[] sprites;
	
	public SpriteAnimation(long frameDelayInMilisecs, byte timeFormat, Sprite[] sprites) {
		super(frameDelayInMilisecs, timeFormat, sprites.length);
		this.sprites = sprites;
	}
	
	public SpriteAnimation(SpriteSheet spriteSheet,long frameDelay, int startFrame, int endFrame, byte timeFormat) {
		super(frameDelay, startFrame, endFrame, spriteSheet.getSprites().length, timeFormat);
		this.sprites = spriteSheet.getSprites();
	}

	@Override
	public void render(Screen screen, int x, int y) {
		screen.renderSprite(sprites[currentFrame], x, y);
	}
}