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


import java.util.Vector;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public abstract class Level extends BasicGameState {
	protected static Image[] ressources;
	protected static Image[] player_res;
	protected static Image[] ennemies_res;
	protected static Image[] powerup_res;
	protected static UnicodeFont[] fonts;
	protected Player player;
	private int ticker;
	private boolean canshoot;
	private int cdcalculator;
	private int recovcalculator;
	protected Vector<Shoot> existing_shoot;
	protected Vector<Ennemy> ennemies;
	protected Vector<Powerup> powerup;
	protected Rocket rck;
	private boolean canonheat;
	private HUD hud;
	
	public Level() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		player = Game.player;
		ticker = 0;
		canshoot = true;
		cdcalculator = 0;
		recovcalculator = 0;
		existing_shoot = new Vector<Shoot>();
		ennemies = new Vector<Ennemy>();
		powerup = new Vector<Powerup>();
		rck = null;
		canonheat = false;
		hud = new HUD(gc.getGraphics());
		hud.setFull(!Game.settings.isMinimal_hud());

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		hud.renderHUD();

	}
	
	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		if (key == Keyboard.KEY_LEFT && player.getShip().getX() > 32) {
			player.getShip().setX(player.getShip().getX()-1);
		}
		if (key == Keyboard.KEY_RIGHT && player.getShip().getX() < 768) {
			player.getShip().setX(player.getShip().getX()+1);
		}
		if (key == Keyboard.KEY_DOWN && player.getShip().getY() > 32) {
			player.getShip().setY(player.getShip().getY()-1);
		}
		if (key == Keyboard.KEY_UP && player.getShip().getY() < 568) {
			player.getShip().setY(player.getShip().getY()+1);
		}
		if (key == Keyboard.KEY_SPACE && canshoot) {
			canshoot = false;
			Shoot[] tirs = player.getShip().getCanon().shoot(player.getShip().getX(),player.getShip().getY());
			for (int i=0;i<tirs.length;i++) {
				existing_shoot.add(tirs[i]);
			}
		}
		if (key == Keyboard.KEY_1 && player.getShip().getRck_stock() > 0 && rck != null) {
			//missile
			if (player.getShip().consume(10)) {
				rck = player.getShip().use_rocket();
			}
		}
		if (key == Keyboard.KEY_2 && player.getShip().getShield() != null) {
			if (player.getShip().getShield().isActiv()) {
				player.getShip().getShield().disable();
			} else {
				player.getShip().getShield().enable();
			}
		}
		if (key == Keyboard.KEY_3 && (!player.getShip().getCanon().isOverheat())) {
			player.getShip().getCanon().overclock();
		}
		if (key == Keyboard.KEY_4 && player.getPower().isAvalaible() && (!player.getPower().isUsed())) {
			switch (player.getPower().getID()) {
			default:
				player.use_power(null);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		ticker += delta;
		if (ticker >= 33) {
			int frames;
			frames = ticker/33;
			ticker %= 33;
			for (int i=0;i<frames;i++) {
				player.getShip().tick();
				laser_col();
				ennemy_col();
				powerup_col();
				if (!player.getShip().isAlive()) {
					player.lose_life();
					if (player.isGame_over()) {
						sbg.enterState(1);
					} else {
						sbg.enterState(sbg.getCurrentStateID());
					}
				}
				if (rck != null) {
					rck.tick();
					for (int k=0;k<ennemies.size();k++) {
						if (rck.getHitbox().check_collision(ennemies.get(k).getHitbox())) {
							rck.explosion();
							break;
						}
					}
					if (rck.isDestroyed()) {
						rck = null;
					}
				}
			}
		} 
		if (!canshoot) {
			cdcalculator += delta;
			if (player.getShip().getCanon().getCooldown()*1000 >= cdcalculator) {
				canshoot = true;
				cdcalculator = 0;
			}
		}
		recovcalculator += delta;
		if (recovcalculator >= 10000) {
			player.getShip().recharge(1);
			recovcalculator = 0;
		}
		
		if (canonheat) {
			if (player.getShip().getCanon().getHeat() == 0) {
				player.getShip().getCanon().reload();
				canonheat = false;
			}
		} else {
			canonheat = player.getShip().getCanon().isOverheat();
		}
	}
	
	private void powerup_col() {
		for (int i=0;i<powerup.size();i++) {
			powerup.get(i).tick();
			if (powerup.get(i).getHitbox().check_collision(player.getShip().getHitbox())) {
				powerup.get(i).transfer(player);
				powerup.remove(i);
			}
			//sortie de zone
			if (powerup.get(i).getX() > 800 || powerup.get(i).getX() < 0
					|| powerup.get(i).getY() > 600 || powerup.get(i).getY() < 0) {
				powerup.remove(i);
			}
		}
	}
	
	private void ennemy_col() {
		for (int i=0;i<ennemies.size();i++) {
			ennemies.get(i).tick();
			
			//verification collision
			for (int k=0;k<ennemies.size();k++) {
				if (k == i) {
					continue;
				}
				if (ennemies.get(i).getHitbox().check_collision(ennemies.get(k).getHitbox())) {
					ennemies.get(i).bounce(0); //need change
					ennemies.get(k).bounce(0); //need change
				}
			}
			
			if (rck != null && rck.getHitbox().check_collision(ennemies.get(i).getHitbox())) {
				if (!ennemies.get(i).damage(50)) {
					Powerup gener = ennemies.get(i).drop();
					if (gener != null) {
						powerup.add(gener);
					}
					ennemies.remove(i);
				}
			}
			
			//sortie de zone
			if (ennemies.get(i).getX() > 800 || ennemies.get(i).getX() < 0
					|| ennemies.get(i).getY() > 600 || ennemies.get(i).getY() < 0) {
				ennemies.remove(i);
			}
		}
	}
		
	private void laser_col() {
		for (int i=0;i<existing_shoot.size();i++) {
			existing_shoot.get(i).tick();
			
			//verification collision
			if (existing_shoot.get(i).isAlly())	{
				for (int k=0;i<ennemies.size();i++) {
					/*if (existing_shoot.get(i).getX() > ennemies.get(k).getX()-(Ennemy.getHitbox_ref()[ennemies.get(k).getId()].getWidth()/2) && existing_shoot.get(i).getX() < ennemies.get(k).getX()+(Ennemy.getHitbox_ref()[ennemies.get(k).getId()].getWidth()/2)
							&& existing_shoot.get(i).getY() > ennemies.get(k).getY()-(Ennemy.getHitbox_ref()[ennemies.get(k).getId()].getHeight()/2) && existing_shoot.get(i).getY() < ennemies.get(k).getY()+(Ennemy.getHitbox_ref()[ennemies.get(k).getId()].getHeight()/2)) {*/
					if (ennemies.get(k).getHitbox().check_collision_point(existing_shoot.get(i).getX(),
							existing_shoot.get(i).getY())) {
						existing_shoot.get(i).setCollision(true);
						if (!ennemies.get(k).damage(10)) {
							Powerup gener = ennemies.get(k).drop();
							if (gener != null) {
								powerup.add(gener);
							}
							ennemies.remove(k);
						}
						break;
					}
				}
			} else {
				/*if (existing_shoot.get(i).getX() > player.getShip().getX()-32 && existing_shoot.get(i).getX() < player.getShip().getX()+32
						&& existing_shoot.get(i).getY() > player.getShip().getY()-32 && existing_shoot.get(i).getY() < player.getShip().getY()+32) {*/
				if (player.getShip().getHitbox().check_collision_point(existing_shoot.get(i).getX(), existing_shoot.get(i).getY())) {
						existing_shoot.get(i).setCollision(true);
						player.getShip().damage(10);
						if (player.getShip().getShield() != null && player.getShip().getShield().isActiv() && player.getShip().getShield().isReflectiv()) {
							existing_shoot.get(i).bounce(0); //angle a modifier
						}
				}
			}
			
			//sortie de zone
			if (existing_shoot.get(i).getX() > 800 || existing_shoot.get(i).getX() < 0
					|| existing_shoot.get(i).getY() > 600 || existing_shoot.get(i).getY() < 0) {
				existing_shoot.get(i).setCollision(true);
			}
			
			if (existing_shoot.get(i).isCollision()) {
				existing_shoot.remove(i);
			}
		}

	}

	@Override
	public abstract int getID();
	
	public void spawn(double x, double y, int dir, int id, int hp) throws Exception {
		ennemies.add(new Ennemy(x, y, dir, id, hp));
	}
	
	public Vector<Shoot> getExisting_shoot() {
		return existing_shoot;
	}

	public Vector<Ennemy> getEnnemies() {
		return ennemies;
	}

	public Vector<Powerup> getPowerup() {
		return powerup;
	}

	public Rocket getRck() {
		return rck;
	}

	public HUD getHud() {
		return hud;
	}
	
	//static
	public static void initRessources() throws SlickException {
		ressources = new Image[10];
		ressources[0] = new Image("Pictures/life.png");
		player_res = new Image[4];
		ennemies_res = new Image[10];
		powerup_res = new Image[10];
	}
	
	@SuppressWarnings("unchecked")
	public static void initFont() throws SlickException {
		fonts = new UnicodeFont[2];
		ColorEffect a = new ColorEffect();
		
		String[] list = {"Font/vgafix.fon","Font/vgasts.fon"};
		int[] size = {20,14};
		boolean[] bold = {true,false};
		boolean[] italic = {false,false};
		
		for (int i=0;i<fonts.length;i++) {
			fonts[i] = new UnicodeFont(list[i],size[i],bold[i],italic[i]);
			fonts[i].addAsciiGlyphs();
			fonts[i].getEffects().add(a);
			fonts[i].loadGlyphs();
		}
	}

	public static Image[] getRessources() {
		return ressources;
	}

	public static Image[] getPlayer_res() {
		return player_res;
	}

	public static Image[] getEnnemies_res() {
		return ennemies_res;
	}

	public static Image[] getPowerup_res() {
		return powerup_res;
	}

	public static UnicodeFont[] getFonts() {
		return fonts;
	}

}