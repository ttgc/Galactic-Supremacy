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

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import exceptions.SpawnException;
import states.levels.Level;

public class Starcup extends Ennemy {
	public static Image animspr;
	private int animcalc;
	private int shootcalc;
	private boolean anim;

	public Starcup(double x, double y, int dir, int hp, Level lvl) throws SpawnException {
		super(x, y, dir, 0, hp, lvl);
		// TODO Auto-generated constructor stub
		animcalc = 0;
		shootcalc = 0;
		anim = false;
		speed = 3;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (anim) {
			animspr.drawCentered((float) x, (float) y); 
		} else {
			Level.getEnnemies_res()[0].drawCentered((float) x, (float) y); 
		}
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		animcalc += delta;
		if (animcalc > 33) {
			animcalc = 0;
			anim = !anim;
		}
		shootcalc += delta;
		if (shootcalc > 2000) {
			getLvl().insertShoot(shoot(270));
			shootcalc = 0;
		}
	}
	
	@Override
	public int bounce(double angle) {
		// TODO Auto-generated method stub
		if (speed < 5) {
			speed++;
		}
		return super.bounce(angle);
	}
	
	public static void init() throws SlickException {
		animspr = new Image("Pictures/starcup_anim.png");
	}

	@Override
	public int getColDmg() {
		// TODO Auto-generated method stub
		return 30;
	}

}
