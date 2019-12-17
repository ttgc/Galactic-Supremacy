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
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import basics.Hitbox;
import basics.Jauge;
import gameplay.player.canon.BasicCanon;
import main.Game;
import resources.ResourceManager;
import resources.loader.FontLoadable;
import resources.loader.FontLoaderData;
import resources.loader.MusicLoadable;
import resources.loader.ResourceLoader;
import states.levels.Level;

public class Garage extends BasicGameState {
	private Jauge durab;
	private Image back;
	private int indexc;
	private int indexs;
	private StateBasedGame sbg;
	private Image button;
	private ResourceLoader<MusicLoadable, String> bgm;
	private ResourceLoader<FontLoadable, FontLoaderData> titleFont;
	private ResourceLoader<FontLoadable, FontLoaderData> basicFont;

	public Garage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		if (Game.player.getShip().getCanon() == null) {
			boolean refuel = true;
			for (int i=0;i<Game.player.getCanon_inv().length;i++) {
				if (Game.player.getCanon_inv()[i] != null) {
					refuel = false;
				}
			}
			if (refuel) {
				Game.player.getShip().setCanon(new BasicCanon(500, 1));
			}
		}
		durab = new Jauge(gc.getGraphics(), 300, 350, 500, 375, new Color(255,255,255), new Color(255,0,0), new Color(0,0,0), false, true);
		durab.setVmax(Game.player.getShip().getCanon().getDurability_max());
		back = new Image("Pictures/background.png");
		indexc = 0;
		indexs = 0;
		this.sbg = sbg;
		button = new Image("Pictures/button.png");
		if (Game.isInit) {
			bgm = ResourceManager.instance.getMusic("garage");
			bgm.load().loop();
		}
		titleFont = ResourceManager.instance.getFonts("title");
		titleFont.load();
		basicFont = ResourceManager.instance.getFonts("dialog");
		basicFont.load();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(back, 0, 0);
		FontUtils.drawCenter(titleFont.getRes(), "GARAGE", 0, 32, 800, new Color(255,255,255));
		g.setFont(basicFont.getRes());
		g.setColor(new Color(255,255,255));
		g.drawString("ESC = Retour", 4, 4);
		Level.getPlayer_res()[0].drawCentered(400, 300);
		Level.getPlayer_res()[Game.player.getShip().getCanon().getID()+2].drawCentered(400+0.5f, (float)300-10);
		FontUtils.drawCenter(titleFont.getRes(), "Canon", 0, 132, 800, Color.white);
		FontUtils.drawCenter(titleFont.getRes(), "Shield", 0, 432, 800, Color.white);
		if (Game.player.getShip().getShield() != null) {
			Level.getPlayer_res()[6].setAlpha(0.3f);
			Level.getPlayer_res()[6].drawCentered(400, 300);
		}
		durab.draw();
		g.setColor(new Color(255,255,255,0.3f));
		g.fillRect(0, 376, 224, 224);
		g.setFont(basicFont.getRes());
		g.setColor(new Color(0,0,0));
		String cname = Game.player.getShip().getCanon().getClass().getSimpleName();
		cname = cname.replace("Canon", "");
		String sname = "None";
		if (Game.player.getPower() != null) {
			sname = Game.player.getPower().getClass().getSimpleName();
			sname = sname.replace("Power", "");
		}
		String shinfo = "None";
		if (Game.player.getShip().getShield() != null) {
			shinfo = Game.player.getShip().getShield().toString();
		}
		g.drawString(Game.player.getName()+"\nCanon : "+cname+"\nCooldown : x"+Game.player.getShip().getCanon().getNatural_cooldown()
				+"\nRockets : "+Game.player.getShip().getRck_stock()+"\nRockets max : "+Game.player.getShip().getRck_stock_max()
				+"\nPower : "+sname+"\n\nShield :\n"+shinfo, 8, 384);
		g.setColor(new Color(255,0,0,0.75f));
		Polygon tri = new Polygon();
		tri.addPoint(250, 300);
		tri.addPoint(300, 275);
		tri.addPoint(300, 325);
		tri.setY(-150);
		g.fill(tri);
		tri.setY(150);
		g.fill(tri);
		tri = new Polygon();
		tri.addPoint(500, 275);
		tri.addPoint(500, 325);
		tri.addPoint(550, 300);
		tri.setY(-150);
		g.fill(tri);
		tri.setY(150);
		g.fill(tri);
		g.drawImage(button, 800-button.getWidth(), 600-button.getHeight());
		FontUtils.drawCenter(basicFont.getRes(), "Recharger rockets", 800-button.getWidth(), 594-(button.getHeight()/2), button.getWidth(), new Color(0,0,0));

	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		// TODO Auto-generated method stub
		super.mouseClicked(button, x, y, clickCount);
		Hitbox clickzone = new Hitbox(50,50);
		clickzone.update(275, 150);
		if (clickzone.check_collision_point(x, y)) {
			indexc--;
			if (indexc < 0) {
				indexc = Game.player.getCanon_inv().length-1;
			}
			while (Game.player.getCanon_inv()[indexc] == null) {
				indexc--;
				if (indexc < 0) {
					return;
				}
			}
			Game.player.switch_canon(indexc);
			durab.setVmax(Game.player.getShip().getCanon().getDurability_max());
		}
		clickzone.update(525, 150);
		if (clickzone.check_collision_point(x, y)) {
			indexc++;
			if (indexc >= Game.player.getCanon_inv().length) {
				indexc = 0;
			}
			while (Game.player.getCanon_inv()[indexc] == null) {
				indexc++;
				if (indexc >= Game.player.getCanon_inv().length) {
					return;
				}
			}
			Game.player.switch_canon(indexc);
			durab.setVmax(Game.player.getShip().getCanon().getDurability_max());
		}
		
		clickzone.update(275, 450);
		if (clickzone.check_collision_point(x, y)) {
			indexs--;
			if (indexs < 0) {
				indexs = Game.player.getShield_inv().length-1;
			}
			while (Game.player.getShield_inv()[indexs] == null) {
				indexs--;
				if (indexs < 0) {
					return;
				}
			}
			Game.player.switch_shield(indexs);
		}
		clickzone.update(525, 450);
		if (clickzone.check_collision_point(x, y)) {
			indexs++;
			if (indexs >= Game.player.getShield_inv().length) {
				indexs = 0;
			}
			while (Game.player.getShield_inv()[indexs] == null) {
				indexs++;
				if (indexs >= Game.player.getShield_inv().length) {
					return;
				}
			}
			Game.player.switch_shield(indexs);
		}
		clickzone.setHeight(this.button.getHeight());
		clickzone.setWidth(this.button.getWidth());
		clickzone.update(800-(this.button.getWidth()/2), 600-(this.button.getHeight()/2));
		if (clickzone.check_collision_point(x, y)) {
			Game.player.recharge_rockets();
		}
	}
	
	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		super.keyPressed(key, c);
		if (key == Keyboard.KEY_ESCAPE) {
			Game.player.save();
			bgm.getRes().stop();
			bgm.unload();
			titleFont.unload();
			basicFont.unload();
			sbg.enterState(5);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		durab.setValue(Game.player.getShip().getCanon().getDurability());

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 6;
	}

}
