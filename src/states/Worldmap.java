package states;
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

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import gameplay.MapPath;
import main.Game;
import states.levels.Level;

public class Worldmap extends BasicGameState {
	private Image back;
	private MapPath map;
	private Image ship;
	private StateBasedGame game;

	public Worldmap() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		back = new Image("Pictures/background.png");
		map = new MapPath(400,556);
		//définition des niveaux
		map.add_level(600, 508);
		map.add_level(508, 418);
		map.add_level(200, 316);
		map.add_level(308, 236);
		map.add_level(308, 188);
		map.add_level(416, 108);
		ship = new Image("Pictures/ship.png");
		game= sbg;

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(back, 0, 0);
		map.draw(g, new Color(255,255,255), new Color(0,255,0), new Color(255,0,0), ship);
		g.setFont(Level.getFonts()[1]);
		g.setColor(new Color(255,255,255));
		g.drawString("Echap = Menu principal\nEnter = Lancer niveau\nEspace = Garage", 16, 540);
		g.resetFont();

	}
	
	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		super.keyPressed(key, c);
		if (key == Keyboard.KEY_ESCAPE) {
			Game.player.save();
			game.enterState(0);
		} else if (key == Keyboard.KEY_RETURN) {
			game.enterState(10+map.getPosition());
		} else if (key == Keyboard.KEY_SPACE) {
			game.enterState(6);
		} else if (key == Keyboard.KEY_UP || key == Keyboard.KEY_RIGHT) {
			map.forward();
		} else if (key == Keyboard.KEY_DOWN || key == Keyboard.KEY_LEFT) {
			map.backward();
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		//update map
		if (map.getAvalaible_level() != Game.player.getLevel()) {
			int diff = Game.player.getLevel()-map.getAvalaible_level();
			if (diff < 0) {
				map = new MapPath(400,572);
				diff = Game.player.getLevel()-map.getAvalaible_level();
			}
			while (diff > 0) {
				map.enable_next();
				diff--;
			}
		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 5;
	}

}
