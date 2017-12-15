import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

public class LoadingScreen extends BasicGameState {
	private Image[] keys;
	private StateBasedGame game;

	public LoadingScreen() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		keys = new Image[6];
		keys[0] = new Image("Pictures/arrow_keyboard.png");
		keys[1] = new Image("Pictures/space_keyboard.png");
		keys[2] = new Image("Pictures/1_keyboard.png");
		keys[3] = new Image("Pictures/2_keyboard.png");
		keys[4] = new Image("Pictures/3_keyboard.png");
		keys[5] = new Image("Pictures/4_keyboard.png");
		game = sbg;
				
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.setBackground(new Color(0,0,0));
		g.setColor(new Color(255,255,255));
		FontUtils.drawCenter(Level.fonts[0], "Commandes", 0, 32, 800, g.getColor());
		g.setFont(Level.fonts[1]);
		//48 h = touche normale + espace
		//96 h = directionnelles
		g.drawImage(keys[0], 32, 96);
		g.drawString("Deplacement", 400, 138);
		g.drawImage(keys[1], 32, 208);
		g.drawString("Tirer", 400, 226);
		g.drawImage(keys[2], 32, 272);
		g.drawString("Lancer missile", 400, 290);
		g.drawImage(keys[3], 32, 336);
		g.drawString("Bouclier", 400, 354);
		g.drawImage(keys[4], 32, 400);
		g.drawString("Overclock canon", 400, 418);
		g.drawImage(keys[5], 32, 464);
		g.drawString("Super pouvoir", 400, 482);
		g.resetFont();
		FontUtils.drawCenter(Level.fonts[1], "Appuyez sur une touche pour continuer", 0, 548, 800, g.getColor());

	}
	
	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		super.keyPressed(key, c);
		game.enterState(5);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 4;
	}

}
