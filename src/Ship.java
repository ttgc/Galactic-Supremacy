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

import java.io.Serializable;

public class Ship implements Serializable {
	private static final long serialVersionUID = 7871425991978808270L;
	private int HP;
	private int energy;
	private int HPmax;
	private int energy_max;
	private int rck_stock;
	private int rck_stock_max;
	private Canon canon;
	private Shield shield;
	private int counttick;
	private double x;
	private double y;
	private boolean alive;
	private Hitbox hitbox;
	
	public Ship() {
		HP = 100;
		energy = 100;
		HPmax = 100;
		energy_max = 100;
		rck_stock = 0;
		rck_stock_max = 0;
		canon = new BasicCanon(500,0.5f);
		shield = null;
		counttick = 0;
		x = 0;
		y = 0;
		alive = true;
		hitbox = new Hitbox(64,64);
		hitbox.update(x, y);
	}
	
	public Ship(int hp, int ener, int rocket_stock) {
		HP = hp;
		energy = ener;
		HPmax = hp;
		energy_max = ener;
		rck_stock = 0;
		if (rocket_stock % 2 != 0) {
			rocket_stock--;
		}
		rck_stock_max = rocket_stock;
		canon = new BasicCanon(500,0.5f);
		shield = null;
		counttick = 0;
		x = 0;
		y = 0;
		hitbox = new Hitbox(64,64);
		hitbox.update(x, y);
	}
	
	public int upgrade_rocket() {
		if (rck_stock_max < 8) {
			rck_stock_max += 2;
		}
		return rck_stock_max;
	}
	
	public int heal(int amount) {
		HP += Math.abs(amount);
		if (HP > HPmax){
			HP = HPmax;
		}
		return HP;
	}
	
	public boolean damage(int amount) {
		boolean shielded = false;
		if (shield != null && shield.isActiv()) {
			shield.setHP(shield.getHP()-Math.abs(amount));
			if (shield.getHP() <= 0) {
				amount = Math.abs(shield.getHP());
				shield.disable();
				if (!shield.isRegenerate()) {
					shield = null;
					consume(shield.getEnergytick());
				} else {
					shielded = true;
				}
			}
		}
		HP -= Math.abs(amount);
		if (!shielded) {
			canon.reduceDurability();
			canon.tick();
		}
		return (HP > 0);
	}
	
	public int recharge(int amount) {
		energy += Math.abs(amount);
		if (energy > energy_max){
			energy = energy_max;
		}
		return energy;
	}
	
	public boolean consume(int amount) {
		if (Math.abs(amount) > energy) {
			return false;
		}
		energy -= Math.abs(amount);
		return true;
	}
	
	public void tick() {
		alive = (HP > 0);
		if (!alive) {
			return;
		}
		if (shield != null && shield.isActiv()) {
			boolean test = consume(shield.getEnergytick());
			if (!test){
				shield.disable();
			}
		}
		if (canon.getDurability() <= 0) {
			canon = null;
		}
		counttick++;
		if (counttick >= 60) {
			counttick = 0;
			canon.setHeat(canon.getHeat()-1);
			if (shield != null && shield.isRegenerate()) {
				shield.setHP((int)(shield.getHP()+(shield.getHPmax()*shield.getRegen())));
			}
		}
	}
	
	public int add_rocket(int amount) {
		rck_stock += Math.abs(amount);
		int resultat = Math.abs(amount);
		if (rck_stock > rck_stock_max) {
			resultat -= (rck_stock_max-rck_stock);
			rck_stock = rck_stock_max;
		}
		return resultat;
	}
	
	public Rocket use_rocket() {
		if (rck_stock <= 0) {
			return null;
		}
		rck_stock--;
		return new Rocket(x,y);
	}
	
	public int getEnergy() {
		return energy;
	}

	public int getRck_stock() {
		return rck_stock;
	}

	public Canon getCanon() {
		return canon;
	}

	public void setCanon(Canon canon) {
		this.canon = canon;
	}

	public Shield getShield() {
		return shield;
	}

	public void setShield(Shield shield) {
		this.shield = shield;
	}

	public int getHP() {
		return HP;
	}

	public int getRck_stock_max() {
		return rck_stock_max;
	}

	public int getHPmax() {
		return HPmax;
	}

	public void setHPmax(int hPmax) {
		if (hPmax < 100) {
			return;
		}
		HPmax = hPmax;
		if (HP > HPmax) {
			HP = HPmax;
		}
	}

	public int getEnergy_max() {
		return energy_max;
	}

	public void setEnergy_max(int energy_max) {
		if (energy_max < 100) {
			return;
		}
		this.energy_max = energy_max;
		if (energy > this.energy_max){
			energy = this.energy_max;
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		hitbox.update(x, y);
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		hitbox.update(x, y);
	}

	public boolean isAlive() {
		return alive;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

}
