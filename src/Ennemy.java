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

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import basics.Hitbox;
import basics.RoundHitbox;

public class Ennemy extends Ennemi {
	protected static Hitbox[] hitbox_ref;
	protected Hitbox hitbox;
	protected RoundHitbox rhitbox;
	protected double x;
	protected double y;
	protected int direction;
	private int id;
	protected int HP;
	public boolean alive;
	private Image sprite;

	public Ennemy(double x, double y, int dir, int res_i, int hp) throws Exception {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		direction = dir;
		if (id < 0 || id >= 10) {
			throw new Exception("ressource index doesnt exist");
		}
		id = res_i;
		HP = hp;
		alive = true;
		hitbox = new Hitbox(hitbox_ref[res_i]);
		rhitbox = new RoundHitbox(hitbox);
		hitbox.update(x, y);
		sprite = Level.ennemies_res[res_i];
	}
	
	public int bounce(double angle) {
		direction = (int) ((180-direction)+(2*angle));
		return direction;
	}
	
	public void tick() {
		x += Math.cos(Math.toRadians(direction));
		y += -Math.sin(Math.toRadians(direction));
		hitbox.update(x,y);
	}
	
	public Shoot shoot(int dir) {
		return new Shoot(x,y,dir);
	}
	
	public boolean damage(int amount) {
		HP -= Math.abs(amount);
		alive = (HP > 0);
		return (HP > 0);
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		sprite.drawCentered((float) x, (float) y);
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unused")
	public Powerup drop() {
		if (alive) {
			return null;
		}
		Powerup item = null;
		Random rdm = new Random();
		int val = rdm.nextInt(100);
		return item;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id < 0 || id >= 10) {
			return;
		}
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getDirection() {
		return direction;
	}

	public int getHP() {
		return HP;
	}

	public boolean isAlive() {
		return alive;
	}
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public RoundHitbox getRhitbox() {
		return rhitbox;
	}
	
	public Image getSprite() {
		return sprite;
	}
	
	//STATIC
	public static void initHitbox() {
		hitbox_ref = new Hitbox[Level.getEnnemies_res().length];
		for (int i=0;i<Level.getEnnemies_res().length;i++) {
			hitbox_ref[i] = new Hitbox(32,32);
		}
	}

	public static Hitbox[] getHitbox_ref() {
		return hitbox_ref;
	}

}
