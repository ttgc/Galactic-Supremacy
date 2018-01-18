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

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import basics.Points;
import exceptions.SpawnException;
import gameplay.player.Player;
import main.Game;
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
	private int shootcalc;
	private int shootcalc2;

	public StarcupBoss(double x, double y, Level lvl) throws SpawnException {
		super(x, y, 0, 2, 1000, lvl);
		// TODO Auto-generated constructor stub
		sprite = Level.getEnnemies_res()[getId()];
		try {
			sprite_anim = new Image("Pictures/starcupBoss_anim.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dmged = new Animation();
		dmged.setLooping(false);
		dmged.setAutoUpdate(true);
		dmged.addFrame(sprite, 33);
		Image cpy = sprite.copy();
		int prg = 255/14;
		for (int i=0;i<14;i++) {
			cpy.setImageColor(prg, 0, 0);
			dmged.addFrame(cpy, 33);
			prg += 255/14;
			cpy = cpy.copy();
		}
		cpy.setImageColor(255, 0, 0);
		dmged.addFrame(cpy, 33);
		cpy = cpy.copy();
		for (int i=0;i<14;i++) {
			cpy.setImageColor(prg, 0, 0);
			dmged.addFrame(cpy, 33);
			prg -= 255/14;
			cpy = cpy.copy();
		}
		dmged.addFrame(sprite, 33);
		initialized = false;
		indmg = false;
		indestr = false;
		intrans = false;
		phase = 0;
		animcalc = 0;
		anim = false;
		shootcalc = 0;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		Random rdm = new Random();
		direction = 200+rdm.nextInt(140);
		speed = 2;
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
		case 500:
			anim = new Animation();
			anim.setLooping(false);
			//code of anim trans phase 1 -> 2
			transition(dmged, sprite);
			break;
		case 250:
			anim = new Animation();
			anim.setLooping(false);
			//code of anim trans phase 2 -> 3
			transition(dmged, sprite);
			break;
		case 0:
			phase++;
		}
	}

	@Override
	public void transition(Animation anim, Image newspr) {
		// TODO Auto-generated method stub
		trans = anim;
		intrans = true;
		trans.restart();
		phase++;
		speed *= 2;
	}

	@Override
	public void loot(Player pl) {
		// TODO Auto-generated method stub
		pl.earnmoney(500);
		if (pl.getLives() < 5) {
			pl.setLives(pl.getLives()+1);
		} else {
			pl.earnmoney(250);
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (indmg) {
			g.drawAnimation(dmged, (float) x-64, (float) y-64);
		} else if (intrans) {
			g.drawAnimation(trans, (float) x-64, (float) y-64);
		} else if (indestr) {
			g.drawAnimation(destr, (float) x-64, (float) y-64);
		} else if (anim) {
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
		
		shootcalc += delta;
		switch (phase) {
		case 1:
			shootcalc2 += delta;
			if (shootcalc2 > 5000 && y < 256) {
				Points calc = new Points(Game.player.getShip().getX()-x, Game.player.getShip().getY()-y);
				getLvl().insertShoot(shoot((int) calc.arg()));
				shootcalc2 = 0;
				shootcalc = 0;
				break;
			}
		case 0:
			if (shootcalc > 1000 && y < 600-128) {
				getLvl().insertShoot(shoot(270));
				shootcalc = 0;
			}
			break;
		case 2:
			if (shootcalc > 2000 && y < 600-160) {
				for (float i=180;i<360;i+=22.5f) {
					getLvl().insertShoot(shoot((int) i));
				}
				getLvl().insertShoot(shoot(0));
				shootcalc = 0;
			}
		}
		
		if (indestr && destr.isStopped()) {
			alive = false;
			indestr = false;
			return;
		}
		if (phase == 3 && alive && !indestr) {
			Animation anim = new Animation();
			anim.setLooping(false);
			//code to insert anim of destruction here
			finalize(dmged);
		}
		if (intrans || indmg) {
			if (intrans && trans.isStopped()) {
				intrans = false;
			}
			if (indmg && dmged.isStopped()) {
				indmg = false;
			}
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
		return 40;
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
		if (y <= 64 || y >= 536) {
			bounce(90);
		}
	}
	
	@Override
	public boolean damage(int amount) {
		// TODO Auto-generated method stub
		super.damage(amount);
		alive = true;
		damaged();
		return true;
	}
	
	@Override
	public boolean isBoss() {
		// TODO Auto-generated method stub
		return true;
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
