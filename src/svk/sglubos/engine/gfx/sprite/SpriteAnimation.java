package svk.sglubos.engine.gfx.sprite;

import svk.sglubos.engine.gfx.Animation;
import svk.sglubos.engine.gfx.Screen;

public class SpriteAnimation extends Animation {
	protected Sprite[] sprites;
	
	public SpriteAnimation(long frameDelayInMilisecs, byte timeFormat, Sprite[] sprites) {
		super(frameDelayInMilisecs, timeFormat, sprites.length);
		this.sprites = sprites;
	}

	@Override
	public void render(Screen screen, int x, int y) {
		screen.renderSprite(sprites[currentFrame], x , y);
	}
}