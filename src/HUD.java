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

import basics.Jauge;
import basics.ProgressJauge;

public class HUD {
	private Graphics g;
	private boolean full;
	private Player p;
	
	private Jauge health;
	private Jauge energy;
	private ProgressJauge heat;

	public HUD(Graphics g) {
		// TODO Auto-generated constructor stub
		this.g = g;
		full = !Game.settings.isMinimal_hud();
		p = Game.player;
		
		health = new Jauge(g, 0, 0, 128, 32, new Color(255,255,255), new Color(0,255,0), new Color(0,0,0), false, true);
		energy = new Jauge(g, 0, 471, 32, 599, new Color(255,255,255), new Color(255,255,0), new Color(0,0,0), true, true);
		Color[] scale = {new Color(255,0,0), new Color(224,32,0), new Color(192,64,0), new Color(160,96,0), new Color(128,128,0), 
				new Color(96,160,0), new Color(64,192,0), new Color(32,224,0), new Color(0,255,0)};
		/*Color[] scale = {new Color(0,255,0), new Color(32,224,0), new Color(64,192,0), new Color(96,160,0), new Color(128,128,0),
				new Color(160,96,0), new Color(192,64,0), new Color(224,32,0), new Color(255,0,0)};*/
		heat = new ProgressJauge(g, 768, 471, 800, 599, new Color(255,255,255), scale, new Color(0,0,0), true, true);
		
		health.setVmax(p.getShip().getHPmax());
		energy.setVmax(p.getShip().getEnergy_max());
		heat.setVmax(100);
	}
	
	public void renderHUD() {
		Color old = g.getColor();
		
		health.update(p.getShip().getHP());
		energy.update(p.getShip().getEnergy());
		heat.update(p.getShip().getCanon().getHeat());
		health.draw();
		energy.draw();
		heat.draw();
		
		g.setColor(new Color(255,255,255));
		g.drawImage(Level.player_res[1], 76, 564);
		g.drawString("x"+p.getShip().getRck_stock(), 96, 584);
		for (int i=0;i<p.getLives();i++) {
			g.drawImage(Level.getRessources()[0], i*32, 32);
		}
		
		if (full) {
			g.drawString("x"+p.getShip().getCanon().getCooldown(), 720, 584);
		}
		
		g.setColor(old);
	}

	public boolean isFull() {
		return full;
	}

	public void setFull(boolean full) {
		this.full = full;
		health.setPrinted(full);
		energy.setPrinted(full);
		heat.setPrinted(full);
	}

}
