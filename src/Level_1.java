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

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Level_1 extends Level {

	public Level_1() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		super.init(gc, sbg);
		limit = 1024;
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

}
