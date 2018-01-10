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

import java.util.Random;

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

public class Shop extends BasicGameState {
	private ShopManager manager;
	private Image button;
	private Image back;
	private Image[] sprites;
	private StateBasedGame game;

	public Shop() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		ShopManager.import_data();
		int[] avalaible = null;
		if (Game.player.getLevel() < 10) {
			avalaible = new int[3];
			avalaible[0] = 0;
			avalaible[1] = 3;
			avalaible[2] = 4;
		} else if (Game.player.getLevel() < 20) {
			
		} else if (Game.player.getLevel() < 30) {
			
		} else {
			
		}
		manager = new ShopManager(avalaible);
		button = new Image("Pictures/button.png");
		back = new Image("Pictures/background.png");
		sprites = new Image[5];
		initSprites();
		game = sbg;

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(back, 0, 0);
		g.setColor(new Color(255,255,255));
		g.setFont(Level.fonts[1]);
		g.drawString("POO restants : "+Game.player.getMoney(), 4, 4);
		g.resetFont();
		sprites[manager.getID()].drawCentered(400, 300);
		FontUtils.drawCenter(Level.fonts[0], manager.getName(), 0, 128, 800, g.getColor());
		FontUtils.drawCenter(Level.fonts[1], manager.getDescription(), 0, 450, 800, g.getColor());
		FontUtils.drawCenter(Level.fonts[0], manager.getPrice()+" POO", 0, 176, 800, g.getColor());
		button.drawCentered(400, 524);
		FontUtils.drawCenter(Level.fonts[1], "Retour", 0, 518, 800, new Color(0,0,0));
		if (manager.isBought() && manager.isOnlyonetime()) {
			g.setLineWidth(4);
			g.setColor(new Color(255,0,0));
			g.drawLine(50, 100, 750, 500);
			g.drawLine(750, 100, 50, 500);
			g.setColor(new Color(255,255,255));
			g.resetLineWidth();
		}

	}
	
	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		super.keyPressed(key, c);
		if (key == Keyboard.KEY_LEFT) {
			manager.swapleft();
		} else if (key == Keyboard.KEY_RIGHT) {
			manager.swapright();
		} else if (key == Keyboard.KEY_RETURN) {
			JFrame frame = new JFrame();
			if (manager.buy(Game.player)) {
				float alea = (float)Math.random();
				if (alea < 0.35f) {
					alea += 0.4f;
				} else if (alea > 0.85f) {
					alea -= 0.2f;
				}
				Random rdm = new Random();
				boolean refund = false;
				switch (manager.getID()) {
				case 0:
					Canon cnd = new DoubleCanon(500+rdm.nextInt(1001), alea);
					refund = !Game.player.add_cannon(cnd);
					break;
				case 1:
					Canon cnt = new TripleCanon(500+rdm.nextInt(1501), alea);
					refund = !Game.player.add_cannon(cnt);
					break;
				case 2:
					Canon cnq = new QuintupleCanon(750+rdm.nextInt(501), alea);
					refund = !Game.player.add_cannon(cnq);
					break;
				case 3:
					try {
						Shield sdp = new Shield(500, 0, false, false, 5, 0);
						refund = !Game.player.add_shield(sdp);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case 4:
					Canon cnb = new BasicCanon(250+rdm.nextInt(500), alea);
					refund = !Game.player.add_cannon(cnb);
					break;
				}
				if (refund) {
					Game.player.earnmoney(manager.getPrice());
					ShopManager.resetBought(manager.getID());
					if (Game.settings.isFullscreen()) {
						try {
							game.getContainer().setFullscreen(false);
						} catch (SlickException e) {
							e.printStackTrace();
						}
					}
					JOptionPane.showMessageDialog(frame, "Achat impossible !\nVotre inventaire est plein !", "Achat impossible", JOptionPane.ERROR_MESSAGE);
					if (Game.settings.isFullscreen()) {
						try {
							game.getContainer().setFullscreen(true);
						} catch (SlickException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				if (Game.settings.isFullscreen()) {
					try {
						game.getContainer().setFullscreen(false);
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
				if (manager.isBought() && manager.isOnlyonetime()) {
					JOptionPane.showMessageDialog(frame, "Achat impossible !\nCet objet a deja ete achete et ne peut l'etre qu'une seule fois", "Achat impossible", JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame, "Achat impossible !\nVous n'avez pas assez de POO", "Achat impossible", JOptionPane.ERROR_MESSAGE);
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
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		// TODO Auto-generated method stub
		super.mouseClicked(button, x, y, clickCount);
		Hitbox click_zone = new Hitbox(this.button.getWidth(),this.button.getHeight());
		click_zone.update(400, 524);
		if (click_zone.check_collision_point(x, y)) {
			ShopManager.export_data();
			game.enterState(0);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 3;
	}
	
	private void initSprites() throws SlickException {
		// TODO Auto-generated method stub
		sprites[0] = new Image("Pictures/canon_shop.png");
		sprites[3] = new Image("Pictures/shield_shop.png");
		sprites[4] = new Image("Pictures/canon_shop.png");
		
	}

}
