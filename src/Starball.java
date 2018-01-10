import org.newdawn.slick.Graphics;

import basics.Path;
import basics.Points;
import exceptions.SpawnException;

public class Starball extends PathedEnnemy {

	public Starball(double x, double y, int dir, int res_i, int hp) throws SpawnException {
		super(x, y, dir, res_i, hp);
		// TODO Auto-generated constructor stub
		speed = 2;
		Path pt = new Path(new Points(0,500), new Points(800,100), hp, true, false, true);
		definePath(pt);
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub

	}

}
