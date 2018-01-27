/*******************************************************************************
	Galactic Supremacy, Shoot'em up game
	Copyright (C) 2017, 2018  PIOT Thomas
		
	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
		
	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with this program. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package gameplay.obstacles;

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
