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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = -6585445920487500144L;
	private String name;
	private Ship ship;
	private Canon[] canon_inv;
	private int rocket_stock;
	private Shield[] shield_inv;
	private int money;
	private int lives;
	private boolean game_over;
	private SuperPower power;
	private int level;

	public Player(String nom) {
		// TODO Auto-generated constructor stub
		name = nom;
		ship = new Ship();
		canon_inv = new Canon[10];
		rocket_stock = 0;
		shield_inv = new Shield[10];
		money = 0;
		lives = 3;
		game_over = false;
		power = null;
		level = 1;
		
	}
	
	public void switch_canon(int index) {
		Canon cn = ship.getCanon();
		ship.setCanon(canon_inv[index]);
		canon_inv[index] = null;
		for (int i=0;i<canon_inv.length;i++) {
			if (canon_inv[i] == null){
				canon_inv[i] = cn;
				break;
			}
		}
	}
	
	public void switch_shield(int index) {
		Shield sd = ship.getShield();
		ship.setShield(shield_inv[index]);
		shield_inv[index] = null;
		for (int i=0;i<shield_inv.length;i++) {
			if (shield_inv[i] == null){
				shield_inv[i] = sd;
				break;
			}
		}
	}
	
	public int earnmoney(int amount) {
		money += Math.abs(amount);
		return money;
	}
	
	public boolean pay(int amount) {
		if (Math.abs(amount) > money) {
			return false;
		}
		money -= Math.abs(amount);
		return true;
	}
	
	public int recharge_rockets() {
		int diff = ship.add_rocket(rocket_stock);
		rocket_stock -= diff;
		return diff;
	}
	
	public boolean lose_life() {
		lives--;
		game_over = (lives >= 0);
		return game_over;
	}
	
	public boolean use_power(Ennemy[] mobs) {
		return power.use(this, mobs);
	}
	
	public void save() {
		File f = new File("Save/"+name+".sav");
		ObjectOutputStream file = null;
		try {
			file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			file.writeObject(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean load() {
		File f = new File("Save/"+name+".sav");
		if (!f.exists() || !f.isFile()) {
			return false;
		}
		ObjectInputStream file = null;
		boolean problem = false;
		try {
			file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			try {
				Player pl = (Player)file.readObject();
				this.ship = pl.ship;
				this.canon_inv = pl.canon_inv;
				this.rocket_stock = pl.rocket_stock;
				this.shield_inv = pl.shield_inv;
				this.money = pl.money;
				this.lives = pl.lives;
				this.game_over = pl.game_over;
				this.power = pl.power;
				this.level = pl.level;
			} catch (ClassNotFoundException e) {
				problem = true;
			}
		} catch (FileNotFoundException e) {
			problem = true;
		} catch (IOException e) {
			problem = true;
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					problem = true;
				}
			}
		}
		return !problem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRocket_stock() {
		return rocket_stock;
	}

	public void setRocket_stock(int rocket_stock) {
		if (rocket_stock < 0) {
			return;
		}
		this.rocket_stock = rocket_stock;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		if (lives < 1) {
			return;
		}
		this.lives = lives;
	}

	public Ship getShip() {
		return ship;
	}

	public Canon[] getCanon_inv() {
		return canon_inv;
	}

	public Shield[] getShield_inv() {
		return shield_inv;
	}

	public int getMoney() {
		return money;
	}

	public boolean isGame_over() {
		return game_over;
	}

	public SuperPower getPower() {
		return power;
	}

	public void setPower(SuperPower power) {
		this.power = power;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		if (level <= 0 || level > 10) {
			return;
		}
		this.level = level;
	}

}
