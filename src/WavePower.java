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

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class WavePower extends SuperPower {
	private static final long serialVersionUID = -1789964618676479849L;
	private float alpha;
	private boolean flash;
	
	public WavePower() {
		super(null);
		// TODO Auto-generated constructor stub
		alpha = 0;
		flash = true;
	}

	@Override
	protected void reset() {
		// TODO Auto-generated method stub
		alpha = 0;
		flash = false;

	}

	@Override
	protected void effect(Player player, Level lvl) {
		// TODO Auto-generated method stub
		for (int i=0;i<10;i++) {
			lvl.getExisting_shoot().add(new Shoot(player.getShip().getX(), player.getShip().getY(), i*20, true));
		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void drawme(Graphics g) {
		// TODO Auto-generated method stub
		if (flash) {
			Color old = g.getColor();
			g.setColor(new Color(255,255,255,alpha));
			g.drawRect(0, 0, 800, 600);
			g.setColor(old);
		}
	}

	public void refresh() {
		// TODO Auto-generated method stub
		if (time%30 == 25) {
			flash = true;
		}
		if (flash && alpha < 0.5f && time%30 >= 25) {
			alpha += 0.1f;
		}
		if (flash && alpha > 0f && time%30 <= 6) {
			alpha -= 0.1f;
		}
		if (time%30 == 5) {
			flash = false;
			alpha = 0;
		}
		if (time%30 == 0) {
			effect(Game.player,lvl);
			if (time/30 == 2) {
				stop();
			}
		}
	}

}
