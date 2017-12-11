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

public abstract class Powerup {
	private double x;
	private double y;
	private Hitbox hitbox;

	public Powerup(double x, double y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		hitbox = new Hitbox(32, 32);
		hitbox.update(x, y);
	}
	
	public void tick() {
		y++;
		hitbox.update(x, y);
	}
	
	public abstract void transfer(Player target);

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

}
