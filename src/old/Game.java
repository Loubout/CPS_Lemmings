package old;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import impl.GameEngImpl;
import impl.LevelImpl;

public class Game extends BasicGameState{
	private int id;
	
	private GameEngImpl gameEng;
	private LevelImpl level;
	
	private GameContainer container;
	private TiledMap map;

	private int x = 10, y = 10;
	private int direction = 2;
	private boolean moving = false;
	private Animation[] animations = new Animation[8];

	public Game(int id){
		this.id = id;
	}
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		this.map = new TiledMap("Level/level2.tmx");
		
//		level.init(map.getWidth(), map.getHeight());
//		gameEng.init(1, 1); 
//		gameEng.bindLevel(level);
		
		SpriteSheet walkerSheet = new SpriteSheet("characters/walker16x32.png", 16, 32);
		SpriteSheet fallerSheet = new SpriteSheet("characters/faller16x32.png", 16, 32);

		this.animations[0] = loadAnimation(fallerSheet, 0, 1, 0);
		this.animations[1] = loadAnimation(walkerSheet, 0, 1, 0);
		this.animations[2] = loadAnimation(fallerSheet, 0, 1, 0);
		this.animations[3] = loadAnimation(walkerSheet, 0, 1, 1);

		this.animations[4] = loadAnimation(fallerSheet, 1, 8, 0);
		this.animations[5] = loadAnimation(walkerSheet, 1, 8, 0);
		this.animations[6] = loadAnimation(fallerSheet, 1, 8, 0);
		this.animations[7] = loadAnimation(walkerSheet, 1, 8, 1);

	}

	private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), 100);
		}
		return animation;
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		this.map.render(0, 0);
		g.drawAnimation(animations[direction + (moving ? 4 : 0)], x*16 , y*16);
		
		
		//g.fillRect(x*16, y*16, 16, 16);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		int ObstacleLayer = map.getLayerIndex("Obstacle");
		map.getTileId(0, 0, ObstacleLayer);
		
		switch (this.direction) {
		case 0://haut
			if(moving)
			if(map.getTileId(x, y-1, ObstacleLayer)==0)
				y--;
//			moving = false;
			break;
		case 1://gauche
			if(moving)
			if(map.getTileId(x-1, y, ObstacleLayer)==0)
				x--;
//			moving = false;
			break;
		case 2://bas
			if(moving)
			if(map.getTileId(x, y+1, ObstacleLayer)==0)
				y++;
//			moving = false;
			break;
		case 3://droit
			if(moving)
			if(map.getTileId(x+1, y, ObstacleLayer)==0)
				x++;
//			moving = false;
			break;
		
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		this.moving = false;
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			this.direction = 0;
			this.moving = true;
			break;
		case Input.KEY_LEFT:
			this.direction = 1;
			this.moving = true;
			break;
		case Input.KEY_DOWN:
			this.direction = 2;
			this.moving = true;
			break;
		case Input.KEY_RIGHT:
			this.direction = 3;
			this.moving = true;
			break;
		}
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

}
