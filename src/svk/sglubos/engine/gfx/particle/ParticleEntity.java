package svk.sglubos.engine.gfx.particle;
//TODO documment
public class ParticleEntity {
	protected double x;
	protected double y;
	
	protected double velocityX;
	protected double velocityY;
	
	public ParticleEntity(double x, double y, double velocityX, double velocityY) {
		this.x = x;
		this.y = y;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
	}
	
	public ParticleEntity(double x, double y) {
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
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void addPosition(double x, double y) {
		this.x += x;
		this.y += y;
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

	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
}
