package gameplay.player;
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

public class Shield implements Serializable {
	private static final long serialVersionUID = -1355464695961360291L;
	private int energytick;
	private int HP;
	private int HPmax;
	private boolean regenerate;
	private double active_max_time;
	private boolean reflectiv;
	private boolean activ;
	private float regen;
	
	public Shield(int hp, int enertick, boolean regener, boolean reflect, double max_time, float regen) throws Exception {
		if (hp <= 0) {
			throw new Exception("HP amount must be strictly positiv");
		}
		HP = hp;
		HPmax = hp;
		if (enertick < 0) {
			throw new Exception("Energy / tick must be positiv or equal to 0");
		}
		energytick = enertick;
		regenerate = regener;
		reflectiv = reflect;
		if (max_time < 0) {
			throw new Exception("Max time must be positiv or equal to 0 to make it unlimited on time");
		}
		active_max_time = max_time;
		if (regen < 0 || regen > 1) {
			throw new Exception("regen value must be between 0 and 1");
		}
		this.regen = regen;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "HP : "+HP+"\nHP max : "+HPmax+"\nEnergy : "+energytick+" per ticks";
		String mtime = String.valueOf(active_max_time);
		if (active_max_time == 0) {
			mtime = "Unlimited";
		}
		str += ("\nActivation time max : "+mtime+"s");
		String reg = "No regen";
		if (regenerate) {
			reg = "x"+regen;
		}
		str += ("\nRegeneration : "+reg);
		String ref = "\nNot reflectiv";
		if (reflectiv) {
			ref = "\nReflectiv";
		}
		str += ref;
		return str;
	}
	
	public void enable() {
		if (!activ) {
			activ = true;
		}
	}
	
	public void disable() {
		if (activ) {
			activ = false;
		}
	}

	public int getEnergytick() {
		return energytick;
	}

	public void setEnergytick(int energytick) {
		this.energytick = energytick;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		if (HP <= 0 || HP > HPmax) {
			return;
		}
		HP = hP;
	}

	public int getHPmax() {
		return HPmax;
	}

	public void setHPmax(int hPmax) {
		if (HPmax <= 0) {
			return;
		}
		HPmax = hPmax;
		if (HPmax < HP) {
			HP = HPmax;
		}
	}

	public boolean isRegenerate() {
		return regenerate;
	}

	public void setRegenerate(boolean regenerate) {
		this.regenerate = regenerate;
	}

	public boolean isReflectiv() {
		return reflectiv;
	}

	public void setReflectiv(boolean reflectiv) {
		this.reflectiv = reflectiv;
	}

	public double getActive_max_time() {
		return active_max_time;
	}

	public boolean isActiv() {
		return activ;
	}

	public float getRegen() {
		return regen;
	}

}
