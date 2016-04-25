package old;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GUI extends StateBasedGame{

	public static final int MENU = 1;
	public static final int GAME = 0;
	
	public GUI(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new Game(GAME));
		this.addState(new Menu(MENU));
		
	}

	
	public static void main(String[] atgs) throws SlickException{
		AppGameContainer appGame = new AppGameContainer(new GUI("Lemmings"));
		appGame.setDisplayMode(710, 580, false);
		appGame.setTargetFrameRate(60);
		appGame.start();
	}

	
}
