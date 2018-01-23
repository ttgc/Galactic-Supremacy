package gameplay;

import basics.RoundHitbox;

public class Asteroid {
	private static int width = 64;
	private static int height = 64;
	private static int speed = 6;
	private RoundHitbox hitbox;
	private float x;
	private float y;
	private int time;

	public Asteroid(float x, float y) {
		// TODO Auto-generated constructor stub
		hitbox = new RoundHitbox(32);
		hitbox.update(x, y);
		this.x = x;
		this.y = y;
		time = 0;
	}
	
	public void update(int delta) {
		time += delta;
		if (time > 33) {
			y += speed;
			hitbox.update(x, y);
			time = 0;
		}
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public RoundHitbox getHitbox() {
		return hitbox;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public static int getSpeed() {
		return speed;
	}

}
