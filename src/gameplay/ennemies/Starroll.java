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

package gameplay.ennemies;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import exceptions.SpawnException;
import states.levels.Level;

public class Starroll extends Ennemy {
	private Animation anim;
	private int frame;

	public Starroll(double x, double y, int hp, Level lvl) throws SpawnException {
		super(x, y, 270, 3, hp, lvl);
		anim = new Animation();
		Image cpy = Level.getEnnemies_res()[3].copy();
		anim.addFrame(cpy, 125);
		for (int i=0;i<32;i++) {
			cpy = cpy.copy();
			cpy.rotate(-11.25f*(i+1));
			anim.addFrame(cpy, 125);
		}
		anim.setLooping(true);
		anim.setAutoUpdate(false);
		frame = anim.getFrame();
		anim.start();
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		anim.draw((float) x-(anim.getHeight()/2), (float) y-(anim.getHeight()/2));
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		anim.update(delta);
		if (frame != anim.getFrame()) {
			frame = anim.getFrame();
			getLvl().insertShoot(shoot((int) (frame*11.25f)));
		}
	}

	@Override
	public int getColDmg() {
		// TODO Auto-generated method stub
		return 100;
	}

}
