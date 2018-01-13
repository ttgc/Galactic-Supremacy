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

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import exceptions.SpawnException;
import gameplay.player.Player;
import states.levels.Level;

public class StarcupBoss extends Ennemy implements Boss {
	private Animation dmged;
	private Animation trans;
	private Animation destr;
	private boolean indmg;
	private boolean intrans;
	private boolean indestr;
	private Image sprite;
	private Image sprite_anim;
	private boolean initialized;
	private int phase;
	private int animcalc;
	private boolean anim;

	public StarcupBoss(double x, double y, Level lvl) throws SpawnException {
		super(x, y, 0, 2, 100, lvl);
		// TODO Auto-generated constructor stub
		dmged = new Animation();
		dmged.setLooping(false);
		//code to insert anim of damage here
		sprite = Level.getEnnemies_res()[getId()];
		try {
			sprite_anim = new Image("Pictures/starcupBoss_anim.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialized = false;
		indmg = false;
		indestr = false;
		intrans = false;
		phase = 0;
		animcalc = 0;
		anim = false;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		//dialogue
		initialized = true;
	}

	@Override
	public void finalize(Animation destruction) {
		// TODO Auto-generated method stub
		destr = destruction;
		indestr = true;
		destr.restart();
	}

	@Override
	public void damaged() {
		// TODO Auto-generated method stub
		indmg = true;
		dmged.restart();
		Animation anim;
		switch(HP) {
		case 50:
			anim = new Animation();
			anim.setLooping(false);
			//code of anim trans phase 1 -> 2
			try {
				transition(anim, new Image(""/*path of spr phase 2*/));
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 25:
			anim = new Animation();
			anim.setLooping(false);
			//code of anim trans phase 2 -> 3
			try {
				transition(anim, new Image(""/*path of spr phase 3*/));
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	public void transition(Animation anim, Image newspr) {
		// TODO Auto-generated method stub
		trans = anim;
		intrans = true;
		trans.restart();
		phase++;
	}

	@Override
	public void loot(Player pl) {
		// TODO Auto-generated method stub
		pl.earnmoney(500);
		pl.setLives(pl.getLives()+1);
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (indmg) {
			g.drawAnimation(dmged, (float) x-64, (float) y-64);
			return;
		}
		if (intrans) {
			g.drawAnimation(trans, (float) x-64, (float) y-64);
			return;
		}
		if (indestr) {
			g.drawAnimation(destr, (float) x-64, (float) y-64);
			return;
		}
		if (anim) {
			sprite_anim.drawCentered((float) x, (float) y);
		} else {
			sprite.drawCentered((float) x, (float) y);
		}
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		if (!initialized) {
			return;
		}
		if (indestr && destr.isStopped()) {
			alive = false;
			return;
		}
		if (phase == 3) {
			Animation anim = new Animation();
			anim.setLooping(false);
			//code to insert anim of destruction here
			finalize(anim);
		}
		if (intrans || indmg) {
			return;
		}
		
		animcalc += delta;
		if (animcalc > 33) {
			animcalc = 0;
			anim = !anim;
		}
	}

	@Override
	public int getColDmg() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if (!initialized) {
			return;
		}
		super.tick();
		if (x <= 64 || x >= 736) {
			bounce(0);
		}
	}
	
	@Override
	public boolean damage(int amount) {
		// TODO Auto-generated method stub
		super.damage(5);
		alive = true;
		damaged();
		return (HP > 0);
	}

	public boolean isIndmg() {
		return indmg;
	}

	public boolean isIntrans() {
		return intrans;
	}

	public boolean isIndestr() {
		return indestr;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public int getPhase() {
		return phase;
	}

}
