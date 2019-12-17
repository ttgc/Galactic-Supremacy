package states.levels;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import exceptions.SpawnException;
import gameplay.obstacles.BlackHole;

public class Level_7 extends Level {
	private BlackHole bh1;
	private BlackHole bh2;

	public Level_7() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		super.init(gc, sbg);
		limit = 1024;
		bh1 = new BlackHole(100, 100);
		bh2 = new BlackHole(700, 100, bh1, true);
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
		return 16;
	}

	@Override
	public boolean isSpawnable() {
		// TODO Auto-generated method stub
		return (view > 800);
	}

	@Override
	public void generateSpawn() throws SpawnException {
		// TODO Auto-generated method stub

	}

	@Override
	public void launch_endlevel() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initObstacle() {
		// TODO Auto-generated method stub

	}

}
