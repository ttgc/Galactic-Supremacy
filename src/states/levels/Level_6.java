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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import exceptions.SpawnException;
import gameplay.ennemies.StarshooterBoss;
import gameplay.obstacles.Wall;

public class Level_6 extends Level {
	private StarshooterBoss boss;
	private int cumultime;

	public Level_6() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		super.init(gc, sbg);
		limit = 1624;
		cumultime = 0;
		try {
			boss = new StarshooterBoss(this);
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
		if (!boss.isAlive()) {
			cumultime += delta;
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 15;
	}

	@Override
	public boolean isSpawnable() {
		// TODO Auto-generated method stub
		return (view == limit && ennemies.isEmpty() && boss.isAlive());
	}

	@Override
	public void generateSpawn() throws SpawnException {
		// TODO Auto-generated method stub
		if (isSpawnable()) {
			ennemies.add(boss);
			boss.initialize();
		}
	}

	@Override
	public void launch_endlevel() {
		// TODO Auto-generated method stub
		if (!boss.isAlive() && cumultime > 1500) {
			boss.loot(player);
			setfinished();
		}
	}

	@Override
	protected void initObstacle() {
		// TODO Auto-generated method stub
		for (int i=224;i<576;i+=32) {
			obstacle.add(new Wall(32, 32, i, 0));
		}
		for (int i=0;i<256;i+=32) {
			obstacle.add(new Wall(32, 32, i, -160));
			obstacle.add(new Wall(32, 32, i+544, -160));
		}
		for (int i=0;i<416;i+=32) {
			obstacle.add(new Wall(32, 32, i, -384));
		}
		for (int i=288;i<800;i+=32) {
			obstacle.add(new Wall(32, 32, i, -640));
		}
		for (int i=0;i<256;i+=32) {
			obstacle.add(new Wall(32, 32, i, -768));
			obstacle.add(new Wall(32, 32, i+544, -768));
		}
		for (int i=224;i<576;i+=32) {
			obstacle.add(new Wall(32, 32, i, -992));
		}
	}

}
