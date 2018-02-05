package gameplay.ennemies;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import exceptions.SpawnException;
import gameplay.player.Player;
import states.levels.Level;

public class StarshooterBoss extends Ennemy implements Boss {
	private boolean initialized;
	private Animation base;
	private boolean indestr;
	private Animation dmg;
	private boolean explose;
	private boolean hasdmged;
	private int frame;
	private int time;

	public StarshooterBoss(Level lvl) throws SpawnException {
		super(400, 300, 0, 5, 1000, lvl);
		// TODO Auto-generated constructor stub
		initialized = false;
		base = new Animation();
		base.addFrame(Level.getEnnemies_res()[5], 33);
		Image img = Level.getEnnemies_res()[5].copy();
		for (int i=1;i<=45;i++) {
			img.rotate(i);
			base.addFrame(img, 33);
			img = img.copy();
		}
		for (int i=44;i>=-45;i--) {
			img.rotate(i);
			base.addFrame(img, 33);
			img = img.copy();
		}
		for (int i=-44;i<=0;i++) {
			img.rotate(i);
			base.addFrame(img, 33);
			img = img.copy();
		}
		base.setLooping(true);
		base.setAutoUpdate(true);
		indestr = false;
		try {
			dmg = new Animation(new SpriteSheet("Pictures/explosion.png", 128, 128), 33);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dmg.setLooping(false);
		dmg.setAutoUpdate(true);
		explose = false;
		hasdmged = false;
		frame = base.getFrame();
		time = 0;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		initialized = true;
		base.start();
	}

	@Override
	public void finalize(Animation destruction) {
		// TODO Auto-generated method stub
		if (!initialized) {
			return;
		}
		base.setLooping(false);
		dmg.stop();
		dmg.setCurrentFrame(0);
		indestr = true;
	}

	@Override
	public void damaged() {
		// TODO Auto-generated method stub
		switch (HP) {
		case 500:
		case 250:
			transition(dmg, null);
			break;
		}
	}

	@Override
	public void transition(Animation anim, Image newspr) {
		// TODO Auto-generated method stub
		base.setSpeed(base.getSpeed()*2);
		dmg.start();
		dmg.setCurrentFrame(0);
	}

	@Override
	public void loot(Player pl) {
		// TODO Auto-generated method stub
		pl.earnmoney(750);
		if (pl.getLives() < 5) {
			pl.setLives(pl.getLives()+1);
		} else {
			pl.earnmoney(250);
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (!initialized) {
			Level.getEnnemies_res()[getId()].drawCentered((float) x, (float) y);
		} else {
			g.drawAnimation(base, (float) x-64, (float) y-64);
			if (!dmg.isStopped()) {
				dmg.draw((float) x-32, (float) y-32, 64, 64);
			}
			if (indestr && y == 536) {
				g.drawAnimation(dmg, (float) x-64, (float) y-64);
			}
		}
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		if (!initialized) {
			return;
		}
		
		time += delta;
		if (frame != base.getFrame() && base.getImage(frame).getRotation() > base.getCurrentFrame().getRotation() && time >= 330/base.getSpeed()) {
			frame = base.getFrame();
			getLvl().insertShoot(shoot((int) (270-base.getCurrentFrame().getRotation())));
			time = 0;
		} else if (frame != base.getFrame()) {
			frame = base.getFrame();
		}
		/*time += delta;
		if (time > 500) {
			getLvl().insertShoot(shoot((int) (270-base.getCurrentFrame().getRotation())));
			time = 0;
		}*/
		
		if (indestr && base.isStopped()) {
			direction = 270;
			speed = 8;
			if (y >= 536) {
				direction = 0;
				y = 536;
				if (!explose) {
					dmg.restart();
					dmg.setCurrentFrame(0);
					explose = true;
					return;
				}
				if (dmg.isStopped()) {
					alive = false;
					indestr = false;
				}
			}
			return;
		}
		
		if (dmg.isStopped()) {
			dmg.setCurrentFrame(0);
		}
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if (!initialized) {
			return;
		}
		if (indestr && base.isStopped() && direction == 270) {
			super.tick();
		}
	}
	
	@Override
	public boolean damage(int amount) {
		// TODO Auto-generated method stub
		if (!initialized) {
			return true;
		}
		super.damage(amount);
		if (HP > 0) {
			damaged();
		} else {
			finalize(null);
		}
		return true;
	}

	@Override
	public int getColDmg() {
		// TODO Auto-generated method stub
		if (!hasdmged) {
			hasdmged = true;
			return 100;
		} 
		return 0;
	}
	
	@Override
	public boolean isBoss() {
		// TODO Auto-generated method stub
		return true;
	}

}
