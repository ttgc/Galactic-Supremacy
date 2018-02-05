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

import basics.Path;
import basics.Points;
import exceptions.SpawnException;
import main.Game;
import states.levels.Level;

public class Starball extends PathedEnnemy {
	private int countshoot;

	public Starball(double x, double y, int dir, int hp, Level lvl) throws SpawnException {
		super(x, y, dir, 1, hp, lvl);
		// TODO Auto-generated constructor stub
		speed = 2;
		Path pt = new Path(new Points(x,600-y), new Points(x,600-y), speed, true, false);
		pt.addPoint(new Points(x+100,600-(y+50)));
		pt.addPoint(new Points(x,600-(y+100)));
		pt.addPoint(new Points(x-100,600-(y+50)));
		definePath(pt);
		countshoot = 0;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		Level.getEnnemies_res()[1].drawCentered((float) x, (float) y);
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		countshoot += delta;
		if (countshoot > 1000) {
			Points vec = new Points(Game.player.getShip().getX()-x, 600-Game.player.getShip().getY()-y);
			getLvl().insertShoot(shoot((int) vec.arg()));
			countshoot = 0;
		}
	}

	@Override
	public int getColDmg() {
		// TODO Auto-generated method stub
		return 10;
	}

}
