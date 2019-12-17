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

package states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import main.Game;
import resources.ResourceManager;
import resources.loader.FontLoadable;
import resources.loader.FontLoaderData;
import resources.loader.ResourceLoader;

public class Credits extends BasicGameState {
	private String[] lines;
	private int speed;
	private Image back;
	private float position;
	private boolean end;
	private StateBasedGame sbg;
	private ResourceLoader<FontLoadable, FontLoaderData> titleFont;
	private ResourceLoader<FontLoadable, FontLoaderData> dialogFont;
	private ResourceLoader<FontLoadable, FontLoaderData> basicFont;

	public Credits() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		back = new Image("Pictures/background.png");
		speed = 30;
		position = 608;
		lines = new String[45];
		initLines();
		end = false;
		this.sbg = sbg;
		titleFont = ResourceManager.instance.getFonts("title");
		titleFont.load();
		dialogFont = ResourceManager.instance.getFonts("dialog");
		dialogFont.load();
		basicFont = ResourceManager.instance.getFonts("basic");
		basicFont.load();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(back, 0, 0);
		if (end) {
			FontUtils.drawCenter(titleFont.getRes(), "THE END", 0, 292, 800, Color.white);
			FontUtils.drawCenter(dialogFont.getRes(), "Appuyez sur une touche pour retourner au menu principal", 0, 300+titleFont.getRes().getHeight("THE END"), 800, Color.white);
		} else {
			FontUtils.drawCenter(titleFont.getRes(), lines[0], 0, (int) position, 800, Color.white);
			int sum = titleFont.getRes().getHeight(lines[0])+128;
			for (int i=1;i<lines.length;i++) {
				if (lines[i-1].equals("")) {
					sum += 64;
				} else {
					sum += basicFont.getRes().getHeight(lines[i-1]);
				}
				FontUtils.drawCenter(basicFont.getRes(), lines[i], 0, (int) (position+sum), 800, Color.white);
			}
			sum += 32;
			if (position+sum < 0) {
				end = true;
			}
		}

	}
	
	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		super.keyPressed(key, c);
		if (!end) {
			speed = 120;
		} else {
			Game.player.save();
			titleFont.unload();
			dialogFont.unload();
			basicFont.unload();
			sbg.enterState(0);
		}
	}
	
	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub
		super.keyReleased(key, c);
		if (!end) {
			speed = 30;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		position -= speed*(((float) delta)/1000.f);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 16;
	}
	
	private void initLines() {
		// TODO Auto-generated method stub
		lines[0] = "GALACTIC SUPREMACY";
		lines[1] = "";
		lines[2] = "Developed by";
		lines[3] = "Thomas PIOT";
		lines[4] = "";
		lines[5] = "";
		lines[6] = "Musics";
		lines[7] = "";
		lines[8] = "TitleScreen music";
		lines[9] = "Generated with AmperMusic";
		lines[10] = "";
		lines[11] = "Shop music";
		lines[12] = "Generated with AmperMusic";
		lines[13] = "";
		lines[14] = "Settings music";
		lines[15] = "Generated with AmperMusic";
		lines[16] = "";
		lines[17] = "Garage music";
		lines[18] = "Generated with AmperMusic";
		lines[19] = "";
		lines[20] = "Worldmap music";
		lines[21] = "Generated with AmperMusic";
		lines[22] = "";
		lines[23] = "Battle music";
		lines[24] = "Freedom by Karstenholymoly";
		lines[25] = "(c) copyright 2017 Licensed";
		lines[26] = "under Creative Commons";
		lines[27] = "Attribution Noncommercial";
		lines[28] = "(3.0) license";
		lines[29] = "http://dig.ccmixter.org/files/Karstenholymoly/55921";
		lines[30] = "Ft: MissJudged";
		lines[31] = "";
		lines[32] = "";
		lines[33] = "Designed by";
		lines[34] = "Thomas PIOT";
		lines[35] = "Nicolas OLIVIER";
		lines[36] = "";
		lines[37] = "";
		lines[38] = "Special Thanks";
		lines[39] = "Anthony MARCHAND";
		lines[40] = "";
		lines[41] = "";
		lines[42] = "";
		lines[43] = "Thanks for playing the game";
		lines[44] = "Copyright (c) 2017, 2018 PIOT Thomas";
	}

}
