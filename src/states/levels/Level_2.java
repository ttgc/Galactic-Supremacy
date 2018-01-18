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

package states.levels;

import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import basics.Hitbox;
import basics.Points;
import exceptions.SpawnException;
import gameplay.Wall;

public class Level_2 extends Level {
	private Vector<Points> pregen;
	private Vector<Boolean> hasSpawned;

	public Level_2() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		super.init(gc, sbg);
		limit = 4096;
		pregen = new Vector<Points>();
		hasSpawned = new Vector<Boolean>();
		initPregenEnnemies();
		try {
			spawn(3, 400, 16, 0, 50);
		} catch (SpawnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		super.render(gc, sbg, g);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		super.update(gc, sbg, delta);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 11;
	}

	@Override
	public boolean isSpawnable() {
		// TODO Auto-generated method stub
		return (view > 1024 && view < 1280 && ennemies.size() < 5);
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
			spawn(0, x, y, (int) (vec.arg()+rdm.nextInt(21)-10), rdm.nextInt(50)+1);
		}
		
		Hitbox calc2 = new Hitbox(96,96);
		boolean col = false;
		for (int i=0;i<pregen.size();i++) {
			calc2.update(pregen.get(i).x, pregen.get(i).y-view);
			col = false;
			for (int k=0;k<ennemies.size();k++) {
				if (calc2.check_collision(ennemies.get(k).getHitbox())) {
					col = true;
					break;
				}
			}
			if (pregen.get(i).y <= view && !col && !hasSpawned.get(i)) {
				spawn(3, pregen.get(i).x, pregen.get(i).y-view, 0, 50);
				hasSpawned.set(i, true);
			}
		}
	}

	@Override
	public void launch_endlevel() {
		// TODO Auto-generated method stub
		if (view == limit && ennemies.isEmpty()) {
			setfinished();
		}
	}

	@Override
	protected void initObstacle() {
		// TODO Auto-generated method stub
		for (int i=0;i<256;i+=32) {
			obstacle.add(new Wall(32, 32, i, 0));
			obstacle.add(new Wall(32, 32, i+544, 0));
		}
		for (int i=224;i<576;i+=32) {
			obstacle.add(new Wall(32, 32, i, -128));
		}
		for (int i=0;i<256;i+=32) {
			obstacle.add(new Wall(32, 32, i, -256));
			obstacle.add(new Wall(32, 32, i+544, -256));
		}
		for (int i=224;i<576;i+=32) {
			obstacle.add(new Wall(32, 32, i, -512));
		}
		
		for (int i=0;i<256;i+=32) {
			obstacle.add(new Wall(32, 32, i, -1024-i));
			obstacle.add(new Wall(32, 32, i, -1632+i));
			obstacle.add(new Wall(32, 32, i+544, -1248+i));
			obstacle.add(new Wall(32, 32, i+544, -1408-i));
		}
		
		//320 à 480
		obstacle.add(new Wall(32, 32, 320, -2112));
		obstacle.add(new Wall(32, 32, 352, -2080));
		obstacle.add(new Wall(32, 32, 384, -2048));
		obstacle.add(new Wall(32, 32, 416, -2080));
		obstacle.add(new Wall(32, 32, 448, -2112));
		obstacle.add(new Wall(32, 32, 352, -2144));
		obstacle.add(new Wall(32, 32, 384, -2176));
		obstacle.add(new Wall(32, 32, 416, -2144));
		
		//64 à 224
		obstacle.add(new Wall(32, 32, 64, -2112-512));
		obstacle.add(new Wall(32, 32, 96, -2080-512));
		obstacle.add(new Wall(32, 32, 128, -2048-512));
		obstacle.add(new Wall(32, 32, 160, -2080-512));
		obstacle.add(new Wall(32, 32, 192, -2112-512));
		obstacle.add(new Wall(32, 32, 96, -2144-512));
		obstacle.add(new Wall(32, 32, 128, -2176-512));
		obstacle.add(new Wall(32, 32, 160, -2144-512));
		
		//576 à 736
		obstacle.add(new Wall(32, 32, 576, -2112-512));
		obstacle.add(new Wall(32, 32, 608, -2080-512));
		obstacle.add(new Wall(32, 32, 640, -2048-512));
		obstacle.add(new Wall(32, 32, 672, -2080-512));
		obstacle.add(new Wall(32, 32, 704, -2112-512));
		obstacle.add(new Wall(32, 32, 608, -2144-512));
		obstacle.add(new Wall(32, 32, 640, -2176-512));
		obstacle.add(new Wall(32, 32, 672, -2144-512));
		
		//320 à 480
		obstacle.add(new Wall(32, 32, 320, -2112-1024));
		obstacle.add(new Wall(32, 32, 352, -2080-1024));
		obstacle.add(new Wall(32, 32, 384, -2048-1024));
		obstacle.add(new Wall(32, 32, 416, -2080-1024));
		obstacle.add(new Wall(32, 32, 448, -2112-1024));
		obstacle.add(new Wall(32, 32, 352, -2144-1024));
		obstacle.add(new Wall(32, 32, 384, -2176-1024));
		obstacle.add(new Wall(32, 32, 416, -2144-1024));
		
		for (int i=-4096;i<-3456;i+=32) {
			obstacle.add(new Wall(32, 32, 0, i));
			obstacle.add(new Wall(32, 32, 768, i));
		}
	}
	
	private void initPregenEnnemies() {
		// TODO Auto-generated method stub
		pregen.add(new Points(400, 384-16));
		pregen.add(new Points(400, 1248-16));
		pregen.add(new Points(400, 1408-16));
		pregen.add(new Points(160, 2112-16));
		pregen.add(new Points(640, 2112-16));
		pregen.add(new Points(400, 2112+512-16));
		pregen.add(new Points(160, 2112+1024-16));
		pregen.add(new Points(640, 2112+1024-16));
		pregen.add(new Points(400, 3456-16));
		pregen.add(new Points(160, 3456+128-16));
		pregen.add(new Points(640, 3456+128-16));
		pregen.add(new Points(400, 4096-128-16));
		
		for (int i=0;i<pregen.size();i++) {
			hasSpawned.add(false);
		}
	}

}
