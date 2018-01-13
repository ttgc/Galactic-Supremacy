package gameplay.player.superpower;
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

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import gameplay.player.Player;
import states.levels.Level;

public abstract class SuperPower implements Serializable {
	private static final long serialVersionUID = -7070554953718840298L;
	protected transient Image sprite;
	protected boolean used;
	protected boolean avalaible;
	protected boolean inuse;
	protected int time;
	public static Level lvl;

	public SuperPower(Image spr) {
		// TODO Auto-generated constructor stub
		sprite = spr;
		used = false;
		avalaible = false;
		inuse = false;
		time = 0;
	}
	
	public boolean use(Player player, Level lvl) {
		if ((!used) && avalaible) {
			used = true;
			avalaible = false;
			inuse = true;
			SuperPower.lvl = lvl;
			effect(player,lvl);
			return true;
		}
		return false;
	}
	
	public void stop() {
		if (inuse) {
			inuse = false;
			time = 0;
			reset();
		}
	}
	
	protected abstract void reset();

	protected abstract void effect(Player player, Level lvl);
	
	public void resetuse() {
		used = false;
	}
	
	public abstract void drawme(Graphics g);
	
	protected abstract void refresh();
	
	public void update() {
		time++;
		refresh();
	}
	
	public abstract int getID();

	public Image getSprite() {
		return sprite;
	}

	public boolean isUsed() {
		return used;
	}

	public boolean isAvalaible() {
		return avalaible;
	}

	public void setAvalaible(boolean avalaible) {
		this.avalaible = avalaible;
	}

	public boolean isInuse() {
		return inuse;
	}

}
