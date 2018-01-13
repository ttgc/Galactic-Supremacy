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

import basics.Path;
import basics.Points;
import exceptions.SpawnException;
import states.levels.Level;

public abstract class PathedEnnemy extends Ennemy {
	protected Path path;
	private boolean path_defined;

	public PathedEnnemy(double x, double y, int dir, int res_i, int hp, Level lvl) throws SpawnException {
		super(x, y, dir, res_i, hp, lvl);
		// TODO Auto-generated constructor stub
		path = new Path(new Points(x, 600-y), new Points(x,600-y), speed, true, false);
		path_defined = false;
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		path.step();
		x = path.getCurrent().x;
		y = path.getCurrent().getTrueY();
		direction = path.getDirection();
		hitbox.update(x, y);
	}
	
	protected void definePath(Path path) {
		if (!path_defined) {
			this.path = path;
			path_defined = true;
		}
	}

	public Path getPath() {
		return path;
	}

}
