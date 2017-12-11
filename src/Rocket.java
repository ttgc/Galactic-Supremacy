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

public class Rocket {
	private double x;
	private double y;
	private Hitbox hitbox;
	private boolean explosed;
	private int tickexplosion;
	private boolean destroyed;
	
	public Rocket(double x, double y) {
		this.x = x;
		this.y = y;
		hitbox = new Hitbox(8, 32);
		hitbox.update(x, y);
		explosed = false;
		tickexplosion = 0;
		destroyed = false;
	}
	
	public void tick() {
		if (destroyed) {
			return;
		}
		if (explosed) {
			tickexplosion++;
			if (tickexplosion > 30) {
				destroyed = true;
			}
			return;
		}
		y--;
		hitbox.update(x, y);
	}
	
	public void explosion() {
		if (explosed) {
			return;
		}
		hitbox.setHeight(128);
		hitbox.setWidth(128);
		hitbox.update(x, y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public boolean isExplosed() {
		return explosed;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

}
