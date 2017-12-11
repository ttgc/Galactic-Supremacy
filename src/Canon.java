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

public abstract class Canon implements Serializable {
	private static final long serialVersionUID = -5660490813969342989L;
	protected int durability;
	protected float cooldown;
	private float natural_cooldown;
	protected int heat;
	private boolean overheat;
	private boolean overclock;
	
	public Canon(int dur, float cd) {
		durability = dur;
		if (cd <= 0 || cd > 1) {
			cd = 0.5f;
		}
		natural_cooldown = cd;
		cooldown = cd;
		heat = 0;
		overheat = false;
		overclock = false;
	}
	
	public void overclock() {
		if (overclock) {
			overclock = false;
			cooldown *= 2;
		} else {
			overclock = true;
			cooldown /= 2;
		}
	}
	
	public abstract Shoot[] shoot(double x, double y);
	
	protected void tick() {
		heat++;
		if (heat >= 100) {
			heat = 100;
			overheat = true;
			overclock = false;
			cooldown = 0;
			durability -= 100;
		}
	}
	
	public void reload() {
		heat = 0;
		overheat = false;
		cooldown = natural_cooldown;
	}
	
	public void reduceDurability() {
		durability--;
	}
	
	public void repair(int amount) {
		durability += Math.abs(amount);
	}

	public int getDurability() {
		return durability;
	}

	public float getCooldown() {
		return cooldown;
	}
	
	public void setCooldown(float cd) {
		if (cd <= 0 || cd > 1) {
			return;
		}
		cooldown = cd;
	}

	public float getNatural_cooldown() {
		return natural_cooldown;
	}

	public int getHeat() {
		return heat;
	}
	
	public void setHeat(int heat) {
		if (heat < 0 || heat > 100) {
			return;
		}
		this.heat = heat;
		if (this.heat >= 100) {
			this.heat = 100;
			overheat = true;
			overclock = false;
			cooldown = 0;
			durability -= 100;
		} else if (this.heat <= 0) {
			reload();
		}
	}

	public boolean isOverheat() {
		return overheat;
	}

	public boolean isOverclock() {
		return overclock;
	}

}
