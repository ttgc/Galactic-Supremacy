package gameplay.ennemies;

import org.newdawn.slick.Graphics;

import exceptions.SpawnException;
import gameplay.Shoot;
import states.levels.Level;

public class Starshooter extends Ennemy {
	private int reserve;
	private int shootcalc;

	public Starshooter(double x, double y, int hp, Level lvl) throws SpawnException {
		super(x, y, 0, 4, hp, lvl);
		// TODO Auto-generated constructor stub
		reserve = 10;
		speed = 1;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		Level.getEnnemies_res()[getId()].drawCentered((float) x, (float) y);
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		if (reserve == 0) {
			direction = 270;
			speed = 7;
		} else {
			shootcalc += delta;
			if (shootcalc > 500) {
				getLvl().insertShoot(shoot(270));
				shootcalc = 0;
			}
		}
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if (direction != 0) {
			super.tick();
		} 
	}
	
	@Override
	public Shoot shoot(int dir) {
		// TODO Auto-generated method stub
		reserve--;
		return super.shoot(dir);
	}

	@Override
	public int getColDmg() {
		// TODO Auto-generated method stub
		return 25;
	}

}
