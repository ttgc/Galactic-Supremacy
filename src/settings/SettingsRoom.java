package settings;
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

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import basics.Hitbox;
import main.Game;
import states.levels.Level;


public class SettingsRoom extends BasicGameState {
	private Settings settings;
	private Image back;
	private StateBasedGame game;
	private Image button;

	public SettingsRoom() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		settings = Game.settings;
		back = new Image("Pictures/background.png");
		game = sbg;
		button = new Image("Pictures/button.png");

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(back, 0, 0);
		g.setColor(new Color(255,255,255));
		FontUtils.drawCenter(Level.getFonts()[0], "Parametres", 0, 32, 800, g.getColor());
		int nbr_options = 3;
		float height_auto = 400.f/nbr_options;
		String[] labels = {"Fullscreen","Affichage minimal","Changement auto canon"};
		boolean[] cheker = {settings.isFullscreen(),settings.isMinimal_hud(),settings.isAuto_swap()};
		for (int i=0;i<nbr_options;i++) {
			FontUtils.drawCenter(Level.getFonts()[1], labels[i], 0, (int) (100+(i+1)*(height_auto/2)-6), 600);
			g.fillRect(690, 100+(i+1)*(height_auto/2), 20, 20);
			g.setColor(new Color(0,0,0));
			g.drawRect(690, 100+i*(height_auto/2), 20, 20);
			if (cheker[i]) {
				g.setLineWidth(2);
				g.drawLine(690, 100+(i+1)*(height_auto/2), 710, 100+(i+1)*(height_auto/2)+20);
				g.drawLine(710, 100+(i+1)*(height_auto/2), 690, 100+(i+1)*(height_auto/2)+20);
				g.resetLineWidth();
			}
			g.setColor(new Color(255,255,255));
		}
		float posy_min = 100+nbr_options*(height_auto/2)+64;
		float inter = 800-128-2*button.getWidth();
		g.drawImage(button, 64, posy_min);
		g.drawImage(button, 64+button.getWidth()+inter, posy_min);
		g.setColor(new Color(0,0,0));
		FontUtils.drawCenter(Level.getFonts()[1], "Retour", 64, (int) (posy_min+button.getHeight()/2), button.getWidth(), g.getColor());
		FontUtils.drawCenter(Level.getFonts()[1], "Par defaut", (int) (64+button.getWidth()+inter), (int) (posy_min+button.getHeight()/2), button.getWidth(), g.getColor());

	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		// TODO Auto-generated method stub
		super.mouseClicked(button, x, y, clickCount);
		Hitbox click_zone = new Hitbox(20,20);
		int nbr_options = 3;
		float height_auto = 400.f/nbr_options;
		float posy_min = 100+nbr_options*(height_auto/2)+64;
		float inter = 800-128-2*this.button.getWidth();
		JFrame frame = new JFrame();
		for (int i=0;i<nbr_options;i++) {
			click_zone.update(700, 100+(i+1)*(height_auto/2)+10);
			if (click_zone.check_collision_point(x, y)) {
				switch(i) {
				case 0:
					settings.setFullscreen(!settings.isFullscreen());
					try {
						game.getContainer().setFullscreen(settings.isFullscreen());
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						settings.setFullscreen(!settings.isFullscreen());
						JOptionPane.showMessageDialog(frame, "Erreur :\nImpossible de changer votre affichage", "Erreur", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
					break;
				case 1:
					settings.setMinimal_hud(!settings.isMinimal_hud());
					break;
				case 2:
					settings.setAuto_swap(!settings.isAuto_swap());
				}
				settings.save();
			}
		}
		click_zone.setHeight(this.button.getHeight());
		click_zone.setWidth(this.button.getWidth());
		click_zone.update(64+this.button.getWidth()/2, posy_min+this.button.getHeight()/2);
		if (click_zone.check_collision_point(x, y)) {
			settings.save();
			game.enterState(0);
		}
		click_zone.update((64+this.button.getWidth())+(this.button.getWidth()/2)+inter, posy_min+this.button.getHeight()/2);
		if (click_zone.check_collision_point(x, y)) {
			if (Game.settings.isFullscreen()) {
				try {
					game.getContainer().setFullscreen(false);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			int result = JOptionPane.showConfirmDialog(frame, "Restaurer les valeurs des parametres a leur valeur par defaut", "Restaurer parametres", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				settings = new Settings();
				settings.save();
				Game.settings = settings;
				try {
					game.getContainer().setFullscreen(settings.isFullscreen());
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			if (Game.settings.isFullscreen()) {
				try {
					game.getContainer().setFullscreen(true);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

}
