package states.levels;

import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

import basics.Hitbox;
import basics.RoundHitbox;
import exceptions.SpawnException;
import gameplay.Asteroid;
import gameplay.Dialog;
import gameplay.Wall;
import gameplay.player.superpower.RayPower;
import gameplay.player.superpower.WavePower;
import gameplay.powerup.PowerupPower;

public class Level_5 extends Level {
	private Vector<Asteroid> asteroids;
	private Dialog dial;
	private boolean activatepower;
	private boolean haspower;
	private int spchose;

	public Level_5() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		super.init(gc, sbg);
		limit = 4096;
		Random rdm = new Random();
		for (int i=0;i<6;i++) {
			try {
				spawn(4, 128*(i+1), 64, 0, rdm.nextInt(100)+1);
			} catch (SpawnException e) {
				e.printStackTrace();
			}
		}
		asteroids = new Vector<Asteroid>();
		activatepower = false;
		haspower = false;
		dial = new Dialog();
		dial.insertMsg("Transmission entrante", 1500);
		dial.insertMsg("Escadron Leader :\n\nNous avons developpe une nouvelle technologie pour vous", 3000);
		dial.insertMsg("Escadron Leader :\n\nIl s agit d une super arme\nNous allons vous la livrer mais vous devez choisir celle que vous\nsouhaitez", 4000);
		dial.insertMsg("Escadron Leader :\n\nAttention cependant elle est tres couteuse en energie\nDe plus il vous faudra trouver les super etoiles pour l activer", 6000);
		dial.insertMsg("Escadron Leader :\n\nAllez cadeau en voici une pour vous\nMaintenant choisissez et bonne chance soldat", 4000);
		spchose = 0;
		if (player.getPower() != null) {
			activatepower = true;
			haspower = true;
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		super.render(gc, sbg, g);
		for (int i=0;i<asteroids.size();i++) {
			Level.ressources[4].drawCentered(asteroids.get(i).getX(), asteroids.get(i).getY());
		}
		
		if (activatepower && dial.isStarted()) {
			dial.render(g);
		}
		if (activatepower && !haspower && !dial.isStarted()) {
			switch(spchose) {
			case 0:
				FontUtils.drawCenter(Level.fonts[0], "Wave Power", 0, 396, 800, Color.white);
				break;
			case 1:
				FontUtils.drawCenter(Level.fonts[0], "Ray Power", 0, 396, 800, Color.white);
				break;
			}
			FontUtils.drawCenter(Level.fonts[1], "Utiliser les fleches pour changer et entrer pour valider", 0, 450, 800, Color.white);
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		if (view == 3072 && !activatepower) {
			dial.start();
			activatepower = true;
			return;
		}
		if (activatepower && dial.isStarted()) {
			dial.update(delta);
			gc.getInput().clearKeyPressedRecord();
			return;
		}
		if (activatepower && !haspower) {
			Input in = gc.getInput();
			if (in.isKeyPressed(Input.KEY_LEFT)) {
				spchose--;
				if (spchose < 0) {
					spchose = 1;
				}
			}
			if (in.isKeyPressed(Input.KEY_RIGHT)) {
				spchose++;
				if (spchose > 1) {
					spchose = 0;
				}
			}
			if (in.isKeyPressed(Input.KEY_ENTER)) {
				switch (spchose) {
				case 0:
					player.setPower(new WavePower());
					break;
				case 1:
					player.setPower(new RayPower());
					break;
				}
				haspower = true;
			}
			if (haspower) {
				powerup.add(new PowerupPower(400, 32));
			}
			return;
		}
		
		super.update(gc, sbg, delta);
		if (isPaused() || isFinished()) {
			return;
		}
		
		for (int i=0;i<asteroids.size();i++) {
			asteroids.get(i).update(delta);
			if (asteroids.get(i).getHitbox().check_collision(new RoundHitbox(player.getShip().getHitbox()))) {
				player.getShip().damage(20);
				asteroids.remove(i);
				return;
			}
			if (asteroids.get(i).getY() > 632 || 
					(player.getPower() != null && player.getPower().getID() == 1 && player.getPower().isInuse() && ((RayPower) player.getPower()).getHitbox().check_collision(new Hitbox(asteroids.get(i).getHitbox())))) {
				asteroids.remove(i);
				return;
			}
			for (int k=0;k<existing_shoot.size();k++) {
				if (asteroids.get(i).getHitbox().check_collision_point(existing_shoot.get(k).getX(), existing_shoot.get(k).getY())) {
					existing_shoot.remove(k);
					break;
				}
			}
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 14;
	}

	@Override
	public boolean isSpawnable() {
		// TODO Auto-generated method stub
		return (view > 1624 && view < 4000);
	}

	@Override
	public void generateSpawn() throws SpawnException {
		// TODO Auto-generated method stub
		if (isSpawnable()) {
			if (ennemies.size() < 5) {
				Hitbox calc = new Hitbox(64,64);
				boolean col = false;
				int posx, posy;
				Random rdm = new Random();
				do {
					posx = 32+rdm.nextInt(768);
					posy = 32+rdm.nextInt(64);
					calc.update(posx, posy);
					col = false;
					for (int i=0;i<ennemies.size();i++) {
						if (calc.check_collision(ennemies.get(i).getHitbox())) {
							col = true;
							break;
						}
					}
					for (int i=0;i<asteroids.size();i++) {
						if (calc.check_collision(new Hitbox(asteroids.get(i).getHitbox()))) {
							col = true;
							break;
						}
					}
				} while (col);
				spawn(4, posx, posy, 0, rdm.nextInt(100)+1);
			}
			
			if (asteroids.size() < 3) {
				Hitbox calc = new Hitbox(64, 600);
				boolean col = false;
				int posx;
				Random rdm = new Random();
				do {
					posx = 32+rdm.nextInt(768);
					calc.update(posx, 300);
					col = false;
					for (int i=0;i<ennemies.size();i++) {
						if (calc.check_collision(ennemies.get(i).getHitbox())) {
							col = true;
							break;
						}
					}
					for (int i=0;i<asteroids.size();i++) {
						if (calc.check_collision(new Hitbox(asteroids.get(i).getHitbox()))) {
							col = true;
							break;
						}
					}
				} while (col);
				asteroids.add(new Asteroid(posx, -32));
			}
		}
	}

	@Override
	public void launch_endlevel() {
		// TODO Auto-generated method stub
		if (view == limit && ennemies.isEmpty() && asteroids.isEmpty()) {
			setfinished();
		}
	}

	@Override
	protected void initObstacle() {
		// TODO Auto-generated method stub
		for (int i=128;i<800-128;i+=32) {
			obstacle.add(new Wall(32, 32, i, -256));
		}
		for (int i=0;i<800-256;i+=32) {
			obstacle.add(new Wall(32, 32, i, -512));
		}
		for (int i=256;i<800;i+=32) {
			obstacle.add(new Wall(32, 32, i, -768));
		}
		for (int i=128;i<800-128;i+=32) {
			obstacle.add(new Wall(32, 32, i, -1024));
		}
	}

}
