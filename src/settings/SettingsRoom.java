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

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import basics.Hitbox;
import basics.Jauge;
import main.Game;
import states.levels.Level;


public class SettingsRoom extends BasicGameState {
	private Settings settings;
	private Image back;
	private StateBasedGame game;
	private Image button;
	private SettingsType affichage;
	private Jauge vjauge;
	private Jauge sjauge;
	private KeyType activkey;

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
		affichage = SettingsType.General;
		vjauge = new Jauge(gc.getGraphics(), 100, 100, 700, 150, Color.white, Color.green, Color.black, false, true);
		sjauge = new Jauge(gc.getGraphics(), 100, 300, 700, 350, Color.white, Color.green, Color.black, false, true);
		vjauge.setVmax(100);
		sjauge.setVmax(100);
		activkey = null;
		if (Game.isInit) {
			Game.music[3].loop();
		}

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(back, 0, 0);
		g.setColor(new Color(255,255,255));
		FontUtils.drawCenter(Level.getFonts()[0], "Parametres", 0, 32, 800, g.getColor());

		if (affichage.equals(SettingsType.General)) {
			int nbr_options = 5;
			float height_auto = 400.f/nbr_options;
			String[] labels = {"Fullscreen","Affichage minimal","Changement auto canon","Rendre muet la musique","Rendre muet les bruitages"};
			boolean[] cheker = {settings.isFullscreen(),settings.isMinimal_hud(),settings.isAuto_swap(),settings.isMusic_mute(),settings.isSound_mute()};
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
			float posy_min = 80+nbr_options*(height_auto/2)+64;
			float inter = 800-128-2*button.getWidth();
			g.drawImage(button, 64, posy_min);
			g.drawImage(button, 64+button.getWidth()+inter, posy_min);
			g.drawImage(button, 64, posy_min+button.getHeight()+32);
			g.drawImage(button, 64+button.getWidth()+inter, posy_min+button.getHeight()+32);
			g.setColor(new Color(0,0,0));
			FontUtils.drawCenter(Level.getFonts()[1], "Changer volumes", 64, (int) (posy_min+button.getHeight()/2), button.getWidth(), g.getColor());
			FontUtils.drawCenter(Level.getFonts()[1], "Reglage des touches", (int) (64+button.getWidth()+inter), (int) (posy_min+button.getHeight()/2), button.getWidth(), g.getColor());
			FontUtils.drawCenter(Level.getFonts()[1], "Retour", 64, (int) (posy_min+button.getHeight()/2)+button.getHeight()+32, button.getWidth(), g.getColor());
			FontUtils.drawCenter(Level.getFonts()[1], "Par defaut", (int) (64+button.getWidth()+inter), (int) (posy_min+button.getHeight()/2)+button.getHeight()+32, button.getWidth(), g.getColor());
		}
		
		if (affichage.equals(SettingsType.Volume)) {
			FontUtils.drawCenter(Level.getFonts()[1], "Musique", 0, 175, 800, Color.white);
			vjauge.draw();
			FontUtils.drawCenter(Level.getFonts()[1], "Bruitages", 0, 375, 800, Color.white);
			sjauge.draw();
			button.drawCentered(400, 500);
			FontUtils.drawCenter(Level.getFonts()[1], "Valider", 400-button.getWidth()/2, 496, button.getWidth(), Color.black);
		}
		
		if (affichage.equals(SettingsType.Keymapping)) {
			button.drawCentered(400, 500);
			FontUtils.drawCenter(Level.getFonts()[1], "Valider", 400-button.getWidth()/2, 496, button.getWidth(), Color.black);
			int nbrkey = 10;
			String[] namesL = {"Gauche","Droite","Haut","Bas","Tirer"};
			String[] namesR = {"Missiles","Bouclier","Overclock","Pouvoir","Pause"};
			int[] keysL = {settings.getMap().getLeft(),settings.getMap().getRight(),settings.getMap().getUp(),settings.getMap().getDown(),settings.getMap().getShoot()};
			int[] keysR = {settings.getMap().getRocket(),settings.getMap().getShield(),settings.getMap().getOverclock(),settings.getMap().getPower(),settings.getMap().getPause()};
			g.setFont(Level.getFonts()[1]);
			for (int i=0;i<nbrkey/2;i++) {
				g.setColor(Color.white);
				g.drawString(namesL[i]+" :", 16, 128+(i*64)+18);
				g.fillRect(256, 128+(i*64), 128, 48);
				g.drawString(namesR[i]+" :", 416, 128+(i*64)+18);
				g.fillRect(656, 128+(i*64), 128, 48);
				g.setColor(Color.black);
				g.drawRect(256, 128+(i*64), 128, 48);
				g.drawRect(656, 128+(i*64), 128, 48);
				String left = Keyboard.getKeyName(keysL[i]);
				if (namesL[i] == Keymap.getName(activkey)) {
					left = "Saisie...";
				}
				String right = Keyboard.getKeyName(keysR[i]);
				if (namesR[i] == Keymap.getName(activkey)) {
					right = "Saisie...";
				}
				FontUtils.drawCenter(g.getFont(), left, 256, 128+(i*64)+18, 128, Color.black);
				FontUtils.drawCenter(g.getFont(), right, 656, 128+(i*64)+18, 128, Color.black);
			}
			g.setColor(Color.white);
			g.resetFont();
		}

	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		// TODO Auto-generated method stub
		super.mouseClicked(button, x, y, clickCount);
		if (affichage.equals(SettingsType.General)) {
			Hitbox click_zone = new Hitbox(20,20);
			int nbr_options = 5;
			float height_auto = 400.f/nbr_options;
			float posy_min = 80+nbr_options*(height_auto/2)+64;
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
						break;
					case 3:
						settings.setMusic_mute(!settings.isMusic_mute());
						game.getContainer().setMusicOn(!settings.isMusic_mute());
						break;
					case 4:
						settings.setSound_mute(!settings.isSound_mute());
						game.getContainer().setSoundOn(!settings.isSound_mute());
						break;
					}
					settings.save();
				}
			}
			click_zone.setHeight(this.button.getHeight());
			click_zone.setWidth(this.button.getWidth());
			click_zone.update(64+this.button.getWidth()/2, posy_min+this.button.getHeight()/2);
			if (click_zone.check_collision_point(x, y)) {
				affichage = SettingsType.Volume;
			}
			click_zone.update((64+this.button.getWidth())+(this.button.getWidth()/2)+inter, posy_min+this.button.getHeight()/2);
			if (click_zone.check_collision_point(x, y)) {
				affichage = SettingsType.Keymapping;
			}
			click_zone.update(64+this.button.getWidth()/2, posy_min+this.button.getHeight()/2+this.button.getHeight()+32);
			if (click_zone.check_collision_point(x, y)) {
				settings.save();
				game.enterState(0);
			}
			click_zone.update((64+this.button.getWidth())+(this.button.getWidth()/2)+inter, posy_min+this.button.getHeight()/2+this.button.getHeight()+32);
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
		
		if (affichage.equals(SettingsType.Volume)) {
			Hitbox click_zone = new Hitbox(this.button.getWidth(), this.button.getHeight());
			click_zone.update(400, 500);
			if (click_zone.check_collision_point(x, y)) {
				affichage = SettingsType.General;
			}
			
			click_zone.setHeight(50);
			click_zone.setWidth(610);
			click_zone.update(400, 125);
			if (click_zone.check_collision_point(x, y)) {
				int valreq = (x-100)/6;
				if (valreq > 100) {
					valreq = 100;
				} else if (valreq < 0) {
					valreq = 0;
				}
				settings.setMusic_volume(valreq);
				game.getContainer().setMusicVolume((float)valreq/100.f);
			}
			click_zone.update(400, 325);
			if (click_zone.check_collision_point(x, y)) {
				int valreq = (x-100)/6;
				if (valreq > 100) {
					valreq = 100;
				} else if (valreq < 0) {
					valreq = 0;
				}
				settings.setSound_volume(valreq);
				game.getContainer().setSoundVolume((float)valreq/100.f);
			}
		}
		
		if (affichage.equals(SettingsType.Keymapping)) {
			Hitbox click_zone = new Hitbox(this.button.getWidth(), this.button.getHeight());
			click_zone.update(400, 500);
			if (click_zone.check_collision_point(x, y)) {
				affichage = SettingsType.General;
			}
			
			click_zone.setHeight(48);
			click_zone.setWidth(128);
			if (activkey == null) {
				int nbrkey = 10;
				KeyType[] keylsL = {KeyType.Left,KeyType.Right,KeyType.Up,KeyType.Down,KeyType.Shoot};
				KeyType[] keylsR = {KeyType.Rocket,KeyType.Shield,KeyType.Overclock,KeyType.Power,KeyType.Pause};
				for (int i=0;i<nbrkey/2;i++) {
					click_zone.update(256+64, 128+(i*64)+24);
					if (click_zone.check_collision_point(x, y)) {
						activkey = keylsL[i];
						break;
					}
					click_zone.update(656+64, 128+(i*64)+24);
					if (click_zone.check_collision_point(x, y)) {
						activkey = keylsR[i];
						break;
					}
				}
			}
		}
	}
	
	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		super.keyPressed(key, c);
		if (affichage.equals(SettingsType.Keymapping) && activkey != null) {
			settings.getMap().assign(activkey, key);
			activkey = null;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		vjauge.update(settings.getMusic_volume());
		sjauge.update(settings.getSound_volume());

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

}
