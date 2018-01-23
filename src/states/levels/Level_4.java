package states.levels;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import basics.Hitbox;
import exceptions.SpawnException;

public class Level_4 extends Level {

	public Level_4() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		super.init(gc, sbg);
		limit = 2048;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		super.render(gc, sbg, g);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		super.update(gc, sbg, delta);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 13;
	}

	@Override
	public boolean isSpawnable() {
		// TODO Auto-generated method stub
		return ((view < 1024 && ennemies.size() < 5) || (view < 2000 && view > 1024 && ennemies.size() < 7));
	}

	@Override
	public void generateSpawn() throws SpawnException {
		// TODO Auto-generated method stub
		if (isSpawnable()) {
			int sb = 0, sc = 0, sr = 0;
			for (int i=0;i<ennemies.size();i++) {
				switch(ennemies.get(i).getId()) {
				case 0:
					sc++;
					break;
				case 1:
					sb++;
					break;
				case 3:
					sr++;
					break;
				}
			}
			
			Hitbox calc = new Hitbox(64,64);
			boolean col = false;
			int posx, posy;
			Random rdm = new Random();
			int count = 0;
			if (sb < 2 && view < 1024) {
				do {
					posx = 32+rdm.nextInt(304);
					posy = 32+rdm.nextInt(64);
					calc.update(posx, posy);
					col = false;
					for (int i=0;i<ennemies.size();i++) {
						if (calc.check_collision(ennemies.get(i).getHitbox())) {
							col = true;
							break;
						}
					}
					if (count == 10) {
						return;
					}
					count++;
				} while (col);
				spawn(1, posx, posy, 260+rdm.nextInt(10), rdm.nextInt(150)+1);
			} else if (sc < 2 && view < 1024) {
				spawn(0, 464+rdm.nextInt(304), 32+rdm.nextInt(64), 270, rdm.nextInt(50)+1);
			} else if (sr < 1) {
				spawn(3, 400, 64, 0, 50);
			} else {
				int area;
				do {
					area = rdm.nextInt(2);
					posx = 32+(464*area)+rdm.nextInt(304);
					posy = 32+rdm.nextInt(64);
					calc.update(posx, posy);
					col = false;
					for (int i=0;i<ennemies.size();i++) {
						if (calc.check_collision(ennemies.get(i).getHitbox())) {
							col = true;
							break;
						}
					}
					if (count == 10) {
						return;
					}
					count++;
				} while (col);
				spawn(4, posx, posy, 260+rdm.nextInt(10), rdm.nextInt(100)+1);
			}
		}
	}

	@Override
	public void launch_endlevel() {
		// TODO Auto-generated method stub
		if (view == limit && ennemies.isEmpty()) {
			setfinished();
		}
	}

	@Override
	protected void initObstacle() {
		// TODO Auto-generated method stub
		return;
	}

}
