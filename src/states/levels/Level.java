package states.levels;
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

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import basics.Hitbox;
import basics.RoundHitbox;
import exceptions.SpawnException;
import gameplay.HUD;
import gameplay.Shoot;
import gameplay.ennemies.Ennemy;
import gameplay.ennemies.Starball;
import gameplay.ennemies.Starcup;
import gameplay.ennemies.Starroll;
import gameplay.ennemies.Starshooter;
import gameplay.obstacles.Wall;
import gameplay.player.Player;
import gameplay.player.Rocket;
import gameplay.player.Ship;
import gameplay.player.superpower.RayPower;
import gameplay.powerup.Powerup;
import main.Game;
import resources.ResourceManager;
import resources.loader.FontLoadable;
import resources.loader.FontLoaderData;
import resources.loader.MusicLoadable;
import resources.loader.ResourceLoader;


public abstract class Level extends BasicGameState {
	protected static Image[] ressources;
	protected static Image[] player_res;
	protected static Image[] ennemies_res;
	protected static Image[] powerup_res;
	protected static Animation flameship;
	protected Player player;
	private int ticker;
	private boolean canshoot;
	private int cdcalculator;
	private int recovcalculator;
	private int shieldcalculator;
	private int shieldregencalculator;
	protected Vector<Shoot> existing_shoot;
	protected Vector<Ennemy> ennemies;
	protected Vector<Powerup> powerup;
	protected Vector<Wall> obstacle;
	protected Rocket rck;
	private boolean canonheat;
	private HUD hud;
	protected Image back;
	protected int limit;
	protected int view;
	private boolean paused;
	private boolean finished;
	private boolean afterlevel;
	private StateBasedGame sbgsave;
	private ResourceLoader<MusicLoadable, String> bgm;
	protected ResourceLoader<FontLoadable, FontLoaderData> font;
	protected ResourceLoader<FontLoadable, FontLoaderData> endFont;
	
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
		shieldcalculator = 0;
		shieldregencalculator = 0;
		existing_shoot = new Vector<Shoot>();
		ennemies = new Vector<Ennemy>();
		powerup = new Vector<Powerup>();
		obstacle = new Vector<Wall>();
		rck = null;
		canonheat = false;
		finished = false;
		afterlevel = false;
		hud = new HUD(gc.getGraphics());
		hud.setFull(!Game.settings.isMinimal_hud());
		back = new Image("Pictures/background.png");
		limit = 0;
		view = 0;
		paused = false;
		sbgsave = sbg;
		player.getShip().setX(400);
		player.getShip().setY(550);
		player.getShip().getHitbox().update(400, 550);
		player.getShip().recharge(player.getShip().getEnergy_max());
		player.getShip().getCanon().reload();
		initObstacle();
		initScrolling();
		bgm = ResourceManager.instance.getMusic("battle");
		if (Game.isInit) {
			if (!bgm.isLoaded()) bgm.load();
			bgm.getRes().loop();
		}
		font = ResourceManager.instance.getFonts("title");
		if (!font.isLoaded()) font.load();
		endFont = ResourceManager.instance.getFonts("dialog");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(back, 0, 0);
		for (int i=0;i<obstacle.size();i++) {
			ressources[2].drawCentered((float)obstacle.get(i).getX(), (float)obstacle.get(i).getY());
		}
		for (int i=0;i<powerup.size();i++) {
			powerup_res[powerup.get(i).getID()].drawCentered((float)powerup.get(i).getX(), (float)powerup.get(i).getY());
		}
		for (int i=0;i<ennemies.size();i++) {
			//ennemies_res[ennemies.get(i).getId()].drawCentered((float)ennemies.get(i).getX(), (float)ennemies.get(i).getY());
			ennemies.get(i).render(g);
		}
		if (rck != null) {
			player_res[1].drawCentered((float)rck.getX(), (float)rck.getY());
		}
		player_res[0].drawCentered((float)player.getShip().getX(), (float)player.getShip().getY());
		player_res[player.getShip().getCanon().getID()+2].drawCentered((float)player.getShip().getX()+0.5f, (float)player.getShip().getY()-10);
		flameship.draw((float) player.getShip().getX()-6, (float) (player.getShip().getY()+27));
		if (player.getShip().getShield() != null && player.getShip().getShield().isActiv()) {
			player_res[6].setAlpha(0.5f);
			player_res[6].drawCentered((float)player.getShip().getX(), (float)player.getShip().getY());
		}
		for (int i=0;i<existing_shoot.size();i++) {
			ressources[1].drawCentered((float)existing_shoot.get(i).getX(), (float)existing_shoot.get(i).getY());
		}
		if (player.getPower() != null && player.getPower().isInuse()) {
			player.getPower().drawme(g);
		}
		hud.renderHUD();
		if (paused) {
			FontUtils.drawCenter(font.getRes(), "PAUSE", 0, 220, 800, new Color(255,255,255));
			FontUtils.drawCenter(font.getRes(), "=========", 0, 225+font.getRes().getHeight("PAUSE"), 800, new Color(255,255,255));
			FontUtils.drawCenter(font.getRes(), "ESC = Reprendre", 0, 230+(font.getRes().getHeight("PAUSE")+font.getRes().getHeight("ESC = Reprendre")), 800, new Color(255,255,255));
			FontUtils.drawCenter(font.getRes(), "F2 = Retour au menu", 0, 235+(font.getRes().getHeight("PAUSE")+font.getRes().getHeight("ESC = Reprendre")+font.getRes().getHeight("F1 = Aide")), 800, new Color(255,255,255));
		}
		
		if (afterlevel) {
			FontUtils.drawCenter(font.getRes(), "NIVEAU TERMINE", 0, 220, 800, new Color(255,255,255));
			FontUtils.drawCenter(font.getRes(), "=========", 0, 225+font.getRes().getHeight("NIVEAU TERMINE"), 800, new Color(255,255,255));
			FontUtils.drawCenter(endFont.getRes(), "Appuyez sur une touche pour continuer", 0, 230+(font.getRes().getHeight("NIVEAU TERMINE")+font.getRes().getHeight("Appuyez sur une touche pour continuer")), 800, new Color(255,255,255));
		}
	}
	
	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		if (afterlevel) {
			endlevel();
		}
		if (finished) {
			return;
		}
		
		if (key == Game.settings.getMap().getPause()/*Keyboard.KEY_ESCAPE*/) {
			paused = !paused;
		}
		if (paused) {
			return;
		}
		
		if (key == Game.settings.getMap().getRocket()/*Keyboard.KEY_1*/ && player.getShip().getRck_stock() > 0 && rck == null) {
			//missile
			if (player.getShip().consume(10)) {
				rck = player.getShip().use_rocket();
			}
		}
		if (key == Game.settings.getMap().getShield()/*Keyboard.KEY_2*/ && player.getShip().getShield() != null) {
			shieldcalculator = 0;
			shieldregencalculator = 0;
			if (player.getShip().getShield().isActiv()) {
				player.getShip().getShield().disable();
			} else {
				player.getShip().getShield().enable();
			}
		}
		if (key == Game.settings.getMap().getOverclock()/*Keyboard.KEY_3*/ && (!player.getShip().getCanon().isOverheat())) {
			player.getShip().getCanon().overclock();
		}
		if (key == Game.settings.getMap().getPower()/*Keyboard.KEY_4*/ && player.getPower() != null && player.getPower().isAvalaible() && (!player.getPower().isUsed())) {
			player.use_power(this);
		}
	}
	
	public void permaKey(Input input) {
		// TODO Auto-generated method stub
		if (finished) {
			return;
		}
		
		if (input.isKeyDown(Game.settings.getMap().getLeft()/*Input.KEY_LEFT*/) && player.getShip().getX() > 32 && wallcheck(2)) {
			player.getShip().setX(player.getShip().getX()-Ship.speed);
		}
		if (input.isKeyDown(Game.settings.getMap().getRight()/*Input.KEY_RIGHT*/) && player.getShip().getX() < 768 && wallcheck(0)) {
			player.getShip().setX(player.getShip().getX()+Ship.speed);
		}
		if (input.isKeyDown(Game.settings.getMap().getDown()/*Input.KEY_DOWN*/) && player.getShip().getY() < 568 && wallcheck(3)) {
			player.getShip().setY(player.getShip().getY()+Ship.speed);
		}
		if (input.isKeyDown(Game.settings.getMap().getUp()/*Input.KEY_UP*/) && player.getShip().getY() > 32 && wallcheck(1)) {
			player.getShip().setY(player.getShip().getY()-Ship.speed);
		}
		if (input.isKeyDown(Game.settings.getMap().getShoot()/*Input.KEY_SPACE*/) && canshoot && (!player.getShip().getCanon().isOverheat())) {
			canshoot = false;
			Shoot[] tirs = player.getShip().getCanon().shoot(player.getShip().getX(),player.getShip().getY());
			for (int i=0;i<tirs.length;i++) {
				existing_shoot.add(tirs[i]);
			}
		}
	}
	
	private void keyF2(StateBasedGame sbg) {
		// TODO Auto-generated method stub
		if (sbg.getContainer().getInput().isKeyPressed(Input.KEY_F2) && paused) {
			JFrame frame = new JFrame();
			if (Game.settings.isFullscreen()) {
				try {
					sbg.getContainer().setFullscreen(false);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			int result;
			result = JOptionPane.showConfirmDialog(frame, "Retourner au menu principal ?\nVotre progression actuelle sera perdue", "Retourner au menu ?", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				player.getShip().fullheal();
				unloadResources();
				sbg.enterState(0);
			}
			if (Game.settings.isFullscreen()) {
				try {
					sbg.getContainer().setFullscreen(true);
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
		keyF2(sbg);
		if (paused) {
			return;
		}
		
		if (finished) {
			ticker += delta;
			if (ticker >= 33) {
				player.getShip().setY(player.getShip().getY()-5);
				Hitbox screen = new Hitbox(800, 600);
				screen.update(400, 300);
				if (!player.getShip().getHitbox().check_collision(screen)) {
					afterlevel = true;
					if (player.getPower() != null && player.getPower().isUsed()) {
						player.getPower().resetuse();
					}
				}
				ticker = 0;
			}
			return;
		}
		
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
					if (player.getPower() != null && player.getPower().isInuse()) {
						player.getPower().stop();
					}
					player.lose_life();
					if (player.isGame_over()) {
						unloadResources();
						sbg.enterState(1);
					} else {
						sbg.enterState(sbg.getCurrentStateID());
					}
				}
				if (player.getShip().getCanon() == null) {
					nocanon(sbg,gc);
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
				permaKey(gc.getInput());
				if (!wallcheck(1)) {
					player.getShip().setY(player.getShip().getY()+1);
				}
				wallcheckplus();
				for (int k=0;k<obstacle.size();k++) {
					obstacle.get(k).update();
				}
				view += 1;
				if (view >= limit) {
					view = limit;
					for (int k=0;k<obstacle.size();k++) {
						obstacle.get(k).setGravity(false);
					}
				}
				outofbound(sbg);
				try {
					generateSpawn();
				} catch (SpawnException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (player.getPower() != null && player.getPower().isInuse()) {
					player.getPower().update();
				}
			}
		}
		
		for (int i=0;i<ennemies.size();i++) {
			ennemies.get(i).update(delta);
		}
		
		verification(delta);
		launch_endlevel();
	}

	private void verification(int delta) {
		if (!canshoot) {
			cdcalculator += delta;
			if (player.getShip().getCanon().getCooldown()*1000 <= cdcalculator) {
				canshoot = true;
				cdcalculator = 0;
			}
		}
		recovcalculator += delta;
		if (recovcalculator >= 10000) {
			player.getShip().recharge(1);
			recovcalculator = 0;
		}
		if (player.getShip().getShield() != null && player.getShip().getShield().isActiv() && player.getShip().getShield().getActive_max_time() != 0) {
			shieldcalculator += delta;
			if (shieldcalculator >= player.getShip().getShield().getActive_max_time()*1000) {
				player.getShip().getShield().disable();
				shieldcalculator = 0;
			}
		}
		if (player.getShip().getShield() != null && player.getShip().getShield().isRegenerate() && !player.getShip().getShield().isActiv()) {
			shieldregencalculator += delta;
			if (shieldregencalculator >= 10000) {
				if (player.getShip().getShield().getHPmax() < player.getShip().getShield().getHP()+((int) (player.getShip().getShield().getRegen()*player.getShip().getShield().getHPmax()))) {
					player.getShip().getShield().setHP(player.getShip().getShield().getHPmax());
				} else {
					player.getShip().getShield().setHP(player.getShip().getShield().getHP()+((int) (player.getShip().getShield().getRegen()*player.getShip().getShield().getHPmax())));
				}
				shieldregencalculator = 0;
			}
		}
		
		if (canonheat) {
			if (player.getShip().getCanon().getHeat() == 0) {
				player.getShip().getCanon().reload();
				canonheat = false;
				canshoot = true;
				cdcalculator = 0;
			}
		} else {
			canonheat = player.getShip().getCanon().isOverheat();
		}
	}

	private void nocanon(StateBasedGame sbg, GameContainer gc) {
		if (Game.settings.isAuto_swap()) {
			for (int k=0;k<player.getCanon_inv().length;k++) {
				if (player.getCanon_inv()[k] != null) {
					player.switch_canon(k);
					break;
				}
			}
		}
		if (player.getShip().getCanon() == null){
			JFrame frame = new JFrame();
			if (Game.settings.isFullscreen()) {
				try {
					gc.setFullscreen(false);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			JOptionPane.showMessageDialog(frame, "Votre canon a ete detruit, vous allez etre renvoye au garage !", "Canon detruit", JOptionPane.INFORMATION_MESSAGE);
			if (Game.settings.isFullscreen()) {
				try {
					gc.setFullscreen(true);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			unloadResources();
			sbg.enterState(6);
		}
	}

	private void outofbound(StateBasedGame sbg) {
		Hitbox screen = new Hitbox(800, 600);
		screen.update(400, 300);
		if (!screen.check_collision(player.getShip().getHitbox())) {
			//sortie d'écran totale
			player.lose_life();
			if (player.isGame_over()) {
				unloadResources();
				sbg.enterState(1);
			} else {
				sbg.enterState(sbg.getCurrentStateID());
			}
		}
		
		if (rck != null && (!screen.check_collision(rck.getHitbox()))) {
			rck = null;
		}
	}

	private void wallcheckplus() {
		Hitbox calc = new Hitbox(player.getShip().getHitbox());
		for (int i=0;i<obstacle.size();i++) {
			if (obstacle.get(i).isSolid() && obstacle.get(i).getDirection() != -1) {
				calc.update(player.getShip().getX()+Math.cos(Math.toRadians(obstacle.get(i).getDirection())), player.getShip().getY()-Math.sin(Math.toRadians(obstacle.get(i).getDirection())));
				if (obstacle.get(i).getHitbox().check_collision(calc)) {
					player.getShip().setX(player.getShip().getX()+Math.cos(Math.toRadians(obstacle.get(i).getDirection())));
					player.getShip().setY(player.getShip().getY()-Math.sin(Math.toRadians(obstacle.get(i).getDirection())));
				}
			}
		}
	}

	private boolean wallcheck(int side) {
		/**side list
		 * 0 = East
		 * 1 = North
		 * 2 = West
		 * 3 = South
		 */
		Hitbox calc = new Hitbox(player.getShip().getHitbox());
		switch (side) {
		case 0:
			calc.update(calc.getXor()+3, calc.getYor());
			break;
		case 1:
			calc.update(calc.getXor(), calc.getYor()-3);
			break;
		case 2:
			calc.update(calc.getXor()-3, calc.getYor());
			break;
		case 3:
			calc.update(calc.getXor(), calc.getYor()+3);
			break;
		}
		for (int i=0;i<obstacle.size();i++) {
			if (obstacle.get(i).isSolid() && obstacle.get(i).getHitbox().check_collision(calc)) {
				return false;
			}
		}
		return true;
	}

	private void powerup_col() {
		for (int i=0;i<powerup.size();i++) {
			powerup.get(i).tick();
			if (powerup.get(i).getHitbox().check_collision(player.getShip().getHitbox())) {
				powerup.get(i).transfer(player);
				powerup.remove(i);
				continue;
			}
			//sortie de zone
			if (powerup.get(i).getX() > 800 || powerup.get(i).getX() < 0
					|| powerup.get(i).getY() > 600 || powerup.get(i).getY() < 0) {
				powerup.remove(i);
				continue;
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
				if (ennemies.get(i).getRhitbox().check_collision(ennemies.get(k).getRhitbox())) {
					ennemies.get(i).bounce(ennemies.get(i).getRhitbox().angle_tan(ennemies.get(i).getDirection())); 
					ennemies.get(k).bounce(ennemies.get(k).getRhitbox().angle_tan(ennemies.get(k).getDirection())); 
					//System.out.println(ennemies.get(i).getRhitbox());
				}
			}
			
			for (int k=0;k<obstacle.size();k++) {
				if (ennemies.get(i).getHitbox().check_collision(obstacle.get(k).getHitbox())) {
					ennemies.get(i).bounce(ennemies.get(i).getRhitbox().angle_tan(ennemies.get(i).getDirection()));
				}
			}
			
			if (rck != null && rck.getHitbox().check_collision(ennemies.get(i).getHitbox())) {
				if (!ennemies.get(i).damage(50)) {
					Powerup gener = ennemies.get(i).drop();
					if (gener != null) {
						powerup.add(gener);
					}
					ennemies.remove(i);
					continue;
				}
				if (ennemies.get(i).isBoss()) {
					rck = null;
				}
			}
			
			if (ennemies.get(i).getHitbox().check_collision(player.getShip().getHitbox())) {
				player.getShip().damage(ennemies.get(i).getColDmg());
				if (ennemies.get(i).isBoss()) {
					ennemies.get(i).bounce(ennemies.get(i).getRhitbox().angle_tan(ennemies.get(i).getDirection()));
					continue;
				}
				ennemies.remove(i);
				continue;
			}
			
			//sortie de zone
			if (ennemies.get(i).getX() > 800 || ennemies.get(i).getX() < 0
					|| ennemies.get(i).getY() > 600 || ennemies.get(i).getY() < 0) {
				ennemies.remove(i);
				continue;
			}
		}
	}
		
	private void laser_col() {
		for (int i=0;i<existing_shoot.size();i++) {
			existing_shoot.get(i).tick();
			
			//verification collision
			if (existing_shoot.get(i).isAlly())	{
				for (int k=0;k<ennemies.size();k++) {
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
			
			//collision obstacle
			for (int k=0;k<obstacle.size();k++) {
				if (obstacle.get(k).getHitbox().check_collision_point(existing_shoot.get(i).getX(), existing_shoot.get(i).getY())) {
					if (obstacle.get(k).isReflect()) {
						RoundHitbox rhit = new RoundHitbox(2);
						rhit.update(existing_shoot.get(i).getX(), existing_shoot.get(i).getY());
						existing_shoot.get(i).bounce(rhit.angle_tan(existing_shoot.get(i).getDirection()));
					} else {
						existing_shoot.get(i).setCollision(true);
					}
					break;
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
	
	public void spawn(int id, double x, double y, int dir, int hp) throws SpawnException {
		switch (id) {
		case 0:
			ennemies.add(new Starcup(x, y, dir, hp, this));
			break;
		case 1:
			ennemies.add(new Starball(x, y, dir, hp, this));
			break;
		case 3:
			ennemies.add(new Starroll(x, y, hp, this));
			break;
		case 4:
			ennemies.add(new Starshooter(x, y, hp, this));
			break;
		default:
			throw new SpawnException("wrong id of ennemy");
		}
	}
	
	public abstract boolean isSpawnable();
	public abstract void generateSpawn() throws SpawnException;
	public abstract void launch_endlevel();
	
	protected void setfinished() {
		finished = true;
		existing_shoot.clear();
		ennemies.clear();
		powerup.clear();
		try {
			endFont.load();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			endFont = font;
		}
	}
	
	private void endlevel() {
		if (player.getLevel() == getID()-9) {
			player.setLevel(player.getLevel()+1);
		}
		unloadResources();
		sbgsave.enterState(5);
		player.earnmoney(100*(getID()-9));
		player.getShip().fullheal();
		player.save();
	}
	
	public void insertShoot(Shoot ins) {
		existing_shoot.addElement(ins);
	}
	
	public void removeMob(int index) {
		ennemies.remove(index);
	}
	
	protected void initScrolling() {
		for (int i=0;i<obstacle.size();i++) {
			obstacle.get(i).setGravity(true);
		}
	}
	
	protected abstract void initObstacle();
	
	protected void unloadResources() {
		bgm.getRes().stop();
		bgm.unload();
		font.unload();
		endFont.unload();
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
	
	public boolean isPaused() {
		return paused;
	}

	public boolean isFinished() {
		return finished;
	}

	//static
	public static void initRessources() throws SlickException {
		ressources = new Image[10];
		ressources[0] = new Image("Pictures/life.png");
		ressources[1] = new Image("Pictures/laser.png");
		ressources[2] = new Image("Pictures/wall.png");
		ressources[3] = new Image("Pictures/raypower.png");
		ressources[4] = new Image("Pictures/asteroid.png");
		ressources[5] = new Image("Pictures/blackhole.png");
		ressources[6] = new Image("Pictures/blackhole-closed.png");
		player_res = new Image[7];
		player_res[0] = new Image("Pictures/ship.png");
		player_res[1] = new Image("Pictures/rocket.png");
		player_res[2] = new Image("Pictures/canon.png");
		player_res[3] = new Image("Pictures/double_canon.png");
		player_res[4] = new Image("Pictures/triple_canon.png");
		player_res[5] = new Image("Pictures/quintuple_canon.png");
		player_res[6] = new Image("Pictures/shield.png");
		ennemies_res = new Image[10];
		ennemies_res[0] = new Image("Pictures/starcup.png");
		ennemies_res[1] = new Image("Pictures/starball.png");
		ennemies_res[2] = new Image("Pictures/starcupBoss.png");
		ennemies_res[3] = new Image("Pictures/starroll.png");
		ennemies_res[4] = new Image("Pictures/starshooter.png");
		ennemies_res[5] = new Image("Pictures/starshooter_boss.png");
		powerup_res = new Image[12];
		powerup_res[0] = new Image("Pictures/burn.png");
		powerup_res[1] = new Image("Pictures/cd-up.png");
		powerup_res[2] = new Image("Pictures/energy-up.png");
		powerup_res[3] = new Image("Pictures/health.png");
		powerup_res[4] = new Image("Pictures/heat-down.png");
		powerup_res[5] = ressources[0].copy();
		powerup_res[6] = new Image("Pictures/money.png");
		powerup_res[7] = new Image("Pictures/poweritem.png");
		powerup_res[8] = new Image("Pictures/repair.png");
		powerup_res[9] = new Image("Pictures/cd-down.png");
		powerup_res[10] = new Image("Pictures/energy-down.png");
		powerup_res[11] = new Image("Pictures/heat-up.png");
		Starcup.init();
		RayPower.initRaypower();
		flameship = new Animation(new SpriteSheet("Pictures/flame-ship.png", 12, 21), 33);
		flameship.setLooping(true);
		flameship.setAutoUpdate(true);
		/*******************************************
		 * Tableau des ressources :
		 * res 0 			= life indicator
		 * res 1 			= laser
		 * res 2 			= wall
		 * res 3			= ray power
		 * res 4			= asteroid
		 * res 5			= blackhole
		 * res 6			= blackhole closed
		 * player res 0 	= ship
		 * player res 1 	= rocket
		 * player res 2 	= basic canon
		 * player res 3 	= double canon
		 * player res 4		= triple canon
		 * player res 5 	= quintuple canon
		 * player res 6 	= shield
		 * ennemies res 0 	= UFO ennemy (starcup)
		 * ennemies res 1 	= Starball
		 * ennemies res 2	= Starcup boss
		 * ennemies res 3	= Starroll
		 * ennemies res 4	= Starshooter
		 * ennemies res 5 	= Starshooter boss
		 *******************************************/
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

}
