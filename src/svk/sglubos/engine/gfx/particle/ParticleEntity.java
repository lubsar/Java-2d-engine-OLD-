package svk.sglubos.engine.gfx.particle;
//TODO documment
public class ParticleEntity {
	protected int x;
	protected int y;
	
	protected double velocityX;
	protected double velocityY;
	
	public ParticleEntity(int x, int y, double velocityX, double velocityY) {
		this.x = x;
		this.y = y;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
	}
	
	public ParticleEntity(int x, int y) {
		this(x, y, 0, 0);
	}
	
	public void tick() {
		x += velocityX;
		y += velocityY;
	}
	
	public void addVelocity(double velocityX, double velocityY) {
		this.velocityX += velocityX;
		this.velocityY += velocityY;
	}
	
	public void setVelocity(double velocityX, double velocityY) {
		this.velocityX = velocityX;
		this.velocityY = velocityY;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
