package gui;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Menu extends BasicGameState{

	private int id;
	private GameContainer container;
	private TiledMap map;

	private float x = 200, y = 200;
	private int direction = 2;
	private boolean moving = false;
	private Animation[] animations = new Animation[8];
	
	public Menu(int id){
		this.id = id;
	}
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.container = container;
		this.map = new TiledMap("Level/level2.tmx");

		SpriteSheet walkerSheet = new SpriteSheet("characters/walker.png", 32, 32);
		SpriteSheet fallerSheet = new SpriteSheet("characters/faller.png", 32, 32);
		
		this.animations[0] = loadAnimation(fallerSheet, 0, 1, 0);
		this.animations[1] = loadAnimation(walkerSheet, 0, 1, 0);
		this.animations[2] = loadAnimation(fallerSheet, 0, 1, 0);
		this.animations[3] = loadAnimation(walkerSheet, 0, 1, 1);
		
		this.animations[4] = loadAnimation(fallerSheet, 1, 8, 0);
		this.animations[5] = loadAnimation(walkerSheet, 1, 8, 0);
		this.animations[6] = loadAnimation(fallerSheet, 1, 8, 0);
		this.animations[7] = loadAnimation(walkerSheet, 1, 8, 1);
		
		
//		SpriteSheet spriteSheet = new SpriteSheet("characters/character.png", 64, 64);
//		this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
//		this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
//		this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
//		this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
//		this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
//		this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
//		this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
//		this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
		
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
//		g.setColor(new Color(0, 0, 0, .5f));
//		g.fillOval(x - 16, y - 8, 32, 16);
		g.drawAnimation(animations[direction + (moving ? 4 : 0)], x , y);
	}
	private void updateCharacter(int delta) {
		if (this.moving) {
			float futurX = getFuturX(delta);
			float futurY = getFuturY(delta);
			boolean collision = isCollision(futurX, futurY);
			if (collision) {
				this.moving = false;
			} else {
				this.x = futurX;
				this.y = futurY;
			}
		}
	}

	private boolean isCollision(float x, float y) {
		int tileW = this.map.getTileWidth();
		int tileH = this.map.getTileHeight();
		int logicLayer = this.map.getLayerIndex("collision");
		Image tile = this.map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
		boolean collision = tile != null;
		if (collision) {
			Color color = tile.getColor((int) x % tileW, (int) y % tileH);
			collision = color.getAlpha() > 0;
		}
		return collision;
	}

	private float getFuturX(int delta) {
		float futurX = this.x;
		switch (this.direction) {
		case 1:
			futurX = this.x - .1f * delta;
			break;
		case 3:
			futurX = this.x + .1f * delta;
			break;
		}
		return futurX;
	}

	private float getFuturY(int delta) {
		float futurY = this.y;
		switch (this.direction) {
		case 0:
			futurY = this.y - .1f * delta;
			break;
		case 2:
			futurY = this.y + .1f * delta;
			break;
		}
		return futurY;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		updateCharacter(delta);
//		if (this.moving) {
//			switch (this.direction) {
//			case 0:
//				this.y -= .1f * delta;
//				break;
//			case 1:
//				this.x -= .1f * delta;
//				break;
//			case 2:
//				this.y += .1f * delta;
//				break;
//			case 3:
//				this.x += .1f * delta;
//				break;
//			}
//		}
		
	}

	@Override
	public int getID() {
		return id;
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

}
