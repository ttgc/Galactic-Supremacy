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

package gameplay.player.superpower;

import gameplay.player.Player;

import main.Game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import basics.Hitbox;

import states.levels.Level;

public class RayPower extends SuperPower {
	private static final long serialVersionUID = -6395027885373717666L;
	private static Image spr = Level.getRessources()[3];
	private Animation anim;
	private Hitbox hitbox;
	private float x;
	private float y;
	private Level lvl;

	public RayPower() {
		super(spr);
		// TODO Auto-generated constructor stub
		anim = new Animation();
		Image cpy;
		for (int i=29;i>0;i--) {
			cpy = spr.getScaledCopy(spr.getWidth(), spr.getHeight()/10);
			anim.addFrame(cpy, 66);
		}
		anim.addFrame(spr, 33);
		anim.setAutoUpdate(true);
		anim.setLooping(false);
		hitbox = new Hitbox(anim.getCurrentFrame().getWidth(), anim.getCurrentFrame().getHeight());
		x = (float) Game.player.getShip().getX();
		y = (float) (Game.player.getShip().getY()-32);
		hitbox.update(x, y);
		lvl = null;
	}

	@Override
	protected void reset() {
		// TODO Auto-generated method stub
		anim.stop();
		anim.setCurrentFrame(0);
		lvl = null;

	}

	@Override
	protected void effect(Player player, Level lvl) {
		// TODO Auto-generated method stub
		anim.start();
		this.lvl = lvl;

	}

	@Override
	public void drawme(Graphics g) {
		// TODO Auto-generated method stub
		if (!anim.isStopped()) {
			g.drawAnimation(anim, x-(sprite.getWidth()/2), y-anim.getCurrentFrame().getHeight());
		} else {
			g.drawImage(sprite, x-(sprite.getWidth()/2), y-sprite.getHeight());
		}

	}

	@Override
	protected void refresh() {
		// TODO Auto-generated method stub
		x = (float) Game.player.getShip().getX();
		y = (float) Game.player.getShip().getY()-32;
		hitbox.update(x, y);
		
		for (int i=0;i<lvl.getEnnemies().size();i++) {
			if (lvl.getEnnemies().get(i).getHitbox().check_collision(hitbox)) {
				lvl.getEnnemies().get(i).damage(100);
			}
		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}

}
