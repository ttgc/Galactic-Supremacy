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
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

public class TitleScreen extends BasicGameState {
	private Image back;
	//private HUD hud;

	public TitleScreen() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		Level.initRessources();
		Level.initFont();
		Ennemy.initHitbox();
		back = new Image("Pictures/background.png");
		gc.setMusicOn(true);
		gc.setSoundOn(true);
		
		//testing
		/*hud = new HUD(gc.getGraphics());
		Game.player.getShip().getCanon().setHeat(50);
		Game.player.getShip().consume(10);*/

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.setBackground(new Color(255,255,255));
		g.drawImage(back, 0, 0);
		g.setColor(new Color(255,255,255));
		FontUtils.drawCenter(Level.fonts[0], "Galactic Supremacy", 0, 16, 800, g.getColor());
		g.resetFont();
		FontUtils.drawCenter(g.getFont(), "Copyright (c) 2017, 2018 PIOT Thomas", 0, 568, 800, g.getColor());
		
		//testing
		Player player = Game.player;
		player.getShip().setX(300);
		player.getShip().setY(300);
		Level.player_res[0].drawCentered((float)player.getShip().getX(), (float)player.getShip().getY());
		Level.player_res[player.getShip().getCanon().getID()+2].drawCentered((float)player.getShip().getX()+0.5f, (float)player.getShip().getY()-10);
		Level.player_res[6].setAlpha(0.5f);
		Level.player_res[6].drawCentered((float)player.getShip().getX(), (float)player.getShip().getY());
		/*g.setFont(Level.fonts[0]);
		g.setBackground(new Color(171, 0, 154));
		g.setColor(new Color(0, 0, 0));
		g.drawString("Galactic Supremacy", 150, 32);
		g.setFont(Level.fonts[1]);
		g.drawString("This is an anouncement text\nLorem ipsum dolor sit amet", 150, 200);
		g.resetFont();
		hud.renderHUD();*/

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
