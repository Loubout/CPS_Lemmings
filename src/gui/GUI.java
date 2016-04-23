package gui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GUI extends StateBasedGame{

	public static final int MENU = 0;
	public static final int GAME = 1;
	
	public GUI(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new Menu(MENU));
		this.addState(new Game(GAME));
	}

	
	public static void main(String[] atgs) throws SlickException{
		AppGameContainer appGame = new AppGameContainer(new GUI("Lemmings"));
		appGame.setDisplayMode(710, 580, false);
		appGame.setTargetFrameRate(60);
		appGame.start();
	}

	
}
