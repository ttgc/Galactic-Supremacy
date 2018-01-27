package gameplay.obstacles;
import basics.Hitbox;

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

public class Wall {
	private boolean solid;
	private Hitbox hitbox;
	private boolean visible;
	private boolean reflect;
	private boolean loaded;
	private boolean gravity;
	private double x;
	private double y;
	private int direction;

	public Wall(int width, int height, double x_nw, double y_nw) {
		// TODO Auto-generated constructor stub
		solid = true;
		visible = true;
		reflect = false;
		loaded = false;
		gravity = false;
		x = x_nw+(width/2);
		y = y_nw+(height/2);
		hitbox = new Hitbox(width, height);
		hitbox.update(x, y);
		direction = -1;
	}
	
	public void update() {
		if (gravity) {
			y++;
			hitbox.update(x, y);
		}
		if (direction != -1) {
			x += Math.cos(Math.toRadians(direction));
			y += -Math.sin(Math.toRadians(direction));
		}
	}
	
	public double setMovement(double vx, double vy) {
		double mod = Math.sqrt((vx*vx)+(vy*vy));
		double true_dir = Math.acos(vx/mod);
		if (vy < 0) {
			true_dir = (360-true_dir)-true_dir;
		}
		true_dir = Math.toDegrees(true_dir);
		direction = (int) Math.round(true_dir);
		return true_dir;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isReflect() {
		return reflect;
	}

	public void setReflect(boolean reflect) {
		this.reflect = reflect;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean isGravity() {
		return gravity;
	}

	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
