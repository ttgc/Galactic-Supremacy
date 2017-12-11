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


public class Shoot {
	private double x;
	private double y;
	private boolean ally;
	private boolean collision;
	private int direction;

	public Shoot(double x, double y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		ally = false;
		collision = false;
		direction = 90;
	}
	
	public Shoot(double x, double y, boolean fromplayer) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		ally = fromplayer;
		collision = false;
		direction = 90;
	}
	
	public Shoot(double x, double y, int dir) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		ally = false;
		collision = false;
		direction = dir;
	}
	
	public Shoot(double x, double y, int dir, boolean fromplayer) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		ally = fromplayer;
		collision = false;
		direction = dir;
	}
	
	public void tick() {
		x += Math.cos(Math.toRadians(direction));
		y += -Math.sin(Math.toRadians(direction));
	}
	
	public int bounce(double angle) {
		
		return direction;
	}

	public boolean isAlly() {
		return ally;
	}

	public void setAlly(boolean ally) {
		this.ally = ally;
	}

	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		if (direction < 0 || direction > 359) {
			return;
		}
		this.direction = direction;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
