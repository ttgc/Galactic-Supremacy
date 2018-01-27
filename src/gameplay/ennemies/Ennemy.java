package gameplay.ennemies;
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

import basics.Hitbox;
import basics.RoundHitbox;
import exceptions.SpawnException;
import gameplay.Shoot;
import gameplay.powerup.Powerup;
import gameplay.powerup.PowerupBurn;
import gameplay.powerup.PowerupCooldown;
import gameplay.powerup.PowerupEnergy;
import gameplay.powerup.PowerupHealth;
import gameplay.powerup.PowerupHeat;
import gameplay.powerup.PowerupLife;
import gameplay.powerup.PowerupMoney;
import gameplay.powerup.PowerupRepair;
import states.levels.Level;

public abstract class Ennemy {
	protected static Hitbox[] hitbox_ref;
	protected Hitbox hitbox;
	protected RoundHitbox rhitbox;
	protected double x;
	protected double y;
	protected int direction;
	private int id;
	protected int HP;
	public boolean alive;
	protected int speed;
	private Level lvl;

	public Ennemy(double x, double y, int dir, int res_i, int hp, Level lvl) throws SpawnException {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		direction = dir;
		if (res_i < 0 || res_i >= Level.getEnnemies_res().length) {
			throw new SpawnException("ressource index doesnt exist");
		}
		id = res_i;
		if (hp <= 0) {
			throw new SpawnException("Cannot have negativ HP");
		}
		HP = hp;
		alive = true;
		hitbox = new Hitbox(hitbox_ref[res_i]);
		rhitbox = new RoundHitbox(hitbox);
		hitbox.update(x, y);
		rhitbox.update(x, y);
		speed = 1;
		this.lvl = lvl;
	}
	
	public int bounce(double angle) {
		direction = (int) ((180-direction)+(2*angle));
		return direction;
	}
	
	public void tick() {
		x += speed*Math.cos(Math.toRadians(direction));
		y += -speed*Math.sin(Math.toRadians(direction));
		hitbox.update(x,y);
		rhitbox.update(x, y);
	}
	
	public Shoot shoot(int dir) {
		return new Shoot(x,y,dir);
	}
	
	public boolean damage(int amount) {
		HP -= Math.abs(amount);
		alive = (HP > 0);
		return (HP > 0);
	}

	public abstract void render(Graphics g);
	public abstract void update(int delta);
	public abstract int getColDmg();
	
	public Powerup drop() {
		if (alive) {
			return null;
		}
		Powerup item = null;
		Random rdm = new Random();
		int val = rdm.nextInt(150);
		if (val < 20) {
			item = new PowerupMoney(x, y);
		} else if (val < 25) {
			item = new PowerupRepair(x, y);
		} else if (val < 30) {
			item = new PowerupLife(x, y);
		} else if (val < 40) {
			item = new PowerupHealth(x, y);
		} else if (val < 50) {
			item = new PowerupHeat(x, y, false);
		} else if (val < 60) {
			item = new PowerupEnergy(x, y, false);
		} else if (val < 65) {
			item = new PowerupCooldown(x, y, false);
		} else if (val < 75) {
			item = new PowerupHeat(x, y, true);
		} else if (val < 85) {
			item = new PowerupEnergy(x, y, true);
		} else if (val < 90) {
			item = new PowerupCooldown(x, y, true);
		} else if (val < 100) {
			item = new PowerupBurn(x, y);
		}
		return item;
	}
	
	public boolean isBoss() {
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id < 0 || id >= Level.getEnnemies_res().length) {
			return;
		}
		this.id = id;
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
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	//STATIC
	public static void initHitbox() {
		hitbox_ref = new Hitbox[Level.getEnnemies_res().length];
		for (int i=0;i<Level.getEnnemies_res().length;i++) {
			if (i == 2 || i == 5) {
				hitbox_ref[i] = new Hitbox(128,128);
				continue;
			}
			hitbox_ref[i] = new Hitbox(64,64);
		}
	}

	public static Hitbox[] getHitbox_ref() {
		return hitbox_ref;
	}

	public Level getLvl() {
		return lvl;
	}

}
