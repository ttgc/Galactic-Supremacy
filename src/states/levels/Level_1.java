package states.levels;
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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import basics.Hitbox;
import basics.Points;
import exceptions.SpawnException;
import gameplay.Dialog;
import gameplay.Wall;

public class Level_1 extends Level {
	private int ennemycount;
	private boolean initialdialog;
	private Dialog dial;

	public Level_1() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		super.init(gc, sbg);
		limit = 2048;
		ennemycount = 0;
		initialdialog = false;
		dial = new Dialog();
		dial.insertMsg("Escadron leader :\n\nOn nous attaque !", 2500);
		dial.insertMsg("Escadron leader :\n\nSoldat ! Vous devez atteindre et detruire la base ennemie !\nDepechez vous ou il sera trop tard !", 5000);
		dial.insertMsg("Escadron leader :\n\nLe monde.... compte sur vous...\n", 4000);
		dial.insertMsg("Transmission interrompue\nPreparez vous au combat", 2000);
		dial.start();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		super.render(gc, sbg, g);
		if (!initialdialog) {
			dial.render(g);
		}
	}
	
	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		if (!initialdialog) {
			return;
		}
		super.keyPressed(key, c);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		if (!initialdialog) {
			dial.update(delta);
			if (!dial.isStarted()) {
				initialdialog = true;
			}
			return;
		}
		super.update(gc, sbg, delta);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	protected void initObstacle() {
		// TODO Auto-generated method stub
		for (int i=336;i<464;i+=32) {
			obstacle.add(new Wall(32, 32, i, 344));
		}
		for (int i=0;i<256;i+=32) {
			obstacle.add(new Wall(32, 32, i, 184));
			obstacle.add(new Wall(32, 32, 544+i, 184));
		}
		for (int i=304;i<400;i+=32) {
			obstacle.add(new Wall(32, 32, i, -328-(i-304)));
		}
		obstacle.add(new Wall(32, 32, 400, -424));
		for (int i=496;i>400;i-=32) {
			obstacle.add(new Wall(32, 32, i, -328+(i-496)));
		}
		
	}

	@Override
	public boolean isSpawnable() {
		// TODO Auto-generated method stub
		return (view > 1024 && ennemycount < 20 && ennemies.size() < 5);
	}

	@Override
	public void generateSpawn() throws SpawnException {
		// TODO Auto-generated method stub
		if (isSpawnable()) {
			Random rdm = new Random();
			Hitbox calc = new Hitbox(96,96);
			int x,y;
			boolean col = false;
			do {
				x = 50+rdm.nextInt(700);
				y = 50+rdm.nextInt(100);
				calc.update(x, y);
				col = false;
				for (int i=0;i<ennemies.size();i++) {
					if (calc.check_collision(ennemies.get(i).getHitbox())) {
						col = true;
					}
				}
			} while (col);
			
			Points vec = new Points(x-player.getShip().getX(), y-player.getShip().getY());
			if (view < 1536) {
				if (rdm.nextInt(10) == 1) {
					spawn(0, x, y, (int) (vec.arg()+rdm.nextInt(21)-10), rdm.nextInt(50)+1);
				}
			} else {
				if (rdm.nextInt(5) == 1) {
					spawn(1, x, y+50, (int) vec.arg()+rdm.nextInt(21)-5, rdm.nextInt(150)+1);
					ennemycount++;
				} else if (rdm.nextInt(5) == 1) {
					spawn(0, x, y, (int) (vec.arg()+rdm.nextInt(21)-10), rdm.nextInt(50)+1);
					ennemycount++;
				}
			}
		}
	}

	@Override
	public void launch_endlevel() {
		// TODO Auto-generated method stub
		if (ennemies.isEmpty() && limit == view && ennemycount == 20) {
			setfinished();
		}
	}

}
