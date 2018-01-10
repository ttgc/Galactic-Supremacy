import basics.Path;
import basics.Points;
import exceptions.SpawnException;

public abstract class PathedEnnemy extends Ennemy {
	private Path path;
	private boolean path_defined;

	public PathedEnnemy(double x, double y, int dir, int res_i, int hp) throws SpawnException {
		super(x, y, dir, res_i, hp);
		// TODO Auto-generated constructor stub
		path = new Path(new Points(x, y), new Points(x,y), speed, true, false, true);
		path_defined = false;
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		path.step();
		x = path.getCurrent().x;
		y = path.getCurrent().getTrueY();
		direction = path.getDirection();
		hitbox.update(x, y);
	}
	
	public void definePath(Path path) {
		if (!path_defined) {
			this.path = path;
			path_defined = true;
		}
	}

	public Path getPath() {
		return path;
	}

}
