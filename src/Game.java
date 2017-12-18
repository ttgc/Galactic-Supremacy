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

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
	public static Player player;
	public static Settings settings;

	public Game(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		// TODO Auto-generated method stub
		addState(new TitleScreen());
		addState(new GameOverScreen());
		addState(new SettingsRoom());
		addState(new Shop());
		addState(new LoadingScreen());
		addState(new Worldmap());
		addState(new Level_1());

	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {
		// TODO Auto-generated method stub
		initDirectories();
		initPlayer();
		initSettings();
		ShopManager.initShops();
		
		AppGameContainer app;
		try {
			app = new AppGameContainer(new Game("Galactic Supremacy"));
			app.setDisplayMode(800, 600, false);
			app.setShowFPS(false);
			app.setFullscreen(settings.isFullscreen());
			app.setIcon("Pictures/ship.png");
			app.start();
		} catch(SlickException e){
			e.printStackTrace();
		}

	}
	
	private static void initDirectories() {
		File dir = new File("Save/");
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	private static void initPlayer() {
		JFrame frame = new JFrame();
		String name;
		do {
			name = JOptionPane.showInputDialog(frame, "Saisir votre nom :", "Launcher",JOptionPane.QUESTION_MESSAGE);
		} while (name == null || name.equals(""));
		player = new Player(name);
		if (!player.load()) {
			int result;
			result = JOptionPane.showConfirmDialog(frame, "Fichier de sauvegarde inexistant\nCreer un nouveau fichier de sauvegarde ?", "Creer nouvelle partie ?", JOptionPane.YES_NO_OPTION);
			if (result != JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			player.save();
			if (!player.load()) {
				JOptionPane.showMessageDialog(frame, "Fatal Error : Impossible de charger la sauvegarde", "Fatal Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
	}
	
	private static void initSettings() {
		settings = null;
		if (Settings.isloadable()) {
			settings = Settings.load();
			if (settings == null) {
				settings = new Settings();
				settings.save();
			}
		} else {
			settings = new Settings();
			settings.save();
		}
	}

}
