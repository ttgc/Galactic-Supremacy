import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Worldmap extends BasicGameState {
	private Image back;
	private MapPath map;
	private Image ship;
	private StateBasedGame game;

	public Worldmap() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		back = new Image("Pictures/background.png");
		map = new MapPath(400,556);
		//définition des niveaux
		map.add_level(600, 508);
		map.add_level(508, 418);
		map.add_level(416, 316);
		map.add_level(308, 236);
		map.add_level(308, 188);
		map.add_level(200, 108);
		ship = new Image("Pictures/ship.png");
		game= sbg;

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawImage(back, 0, 0);
		map.draw(g, new Color(255,255,255), new Color(0,255,0), new Color(255,0,0), ship);
		g.setFont(Level.fonts[1]);
		g.setColor(new Color(255,255,255));
		g.drawString("Echap = Menu principal\nEnter = Lancer niveau\nEspace = Garage", 16, 540);
		g.resetFont();

	}
	
	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		super.keyPressed(key, c);
		if (key == Keyboard.KEY_ESCAPE) {
			Game.player.save();
			game.enterState(0);
		} else if (key == Keyboard.KEY_RETURN) {
			game.enterState(10+map.getPosition());
		} else if (key == Keyboard.KEY_SPACE) {
			game.enterState(6);
		} else if (key == Keyboard.KEY_UP || key == Keyboard.KEY_RIGHT) {
			map.forward();
		} else if (key == Keyboard.KEY_DOWN || key == Keyboard.KEY_LEFT) {
			map.backward();
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		//update map
		if (map.getAvalaible_level() != Game.player.getLevel()) {
			int diff = Game.player.getLevel()-map.getAvalaible_level();
			if (diff < 0) {
				map = new MapPath(400,572);
				diff = Game.player.getLevel()-map.getAvalaible_level();
			}
			while (diff > 0) {
				map.enable_next();
				//map.add_level(xlev[map.getAvalaible_level()-1], ylev[map.getAvalaible_level()-1]);
				diff--;
			}
		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 5;
	}

}
