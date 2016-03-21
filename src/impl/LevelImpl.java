package impl;

import enumeration.Nature;
import services.LevelService;

public class LevelImpl implements LevelService {

	private int width;
	private int height;
	private Nature[][] matLevel;
	private boolean editing;
	private int entranceX;
	private int entranceY;
	private int exitX;
	private int exitY;
	
	public LevelImpl (){}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Nature getNature(int x, int y) {
		return matLevel[x][y];
	}

	@Override
	public boolean editing() {
		return editing;
	}

	@Override
	public int getEntranceX() {
		return entranceX;
	}

	@Override
	public int getEntranceY() {
		return entranceY;
	}

	@Override
	public int getExitX() {
		return exitX;
	}

	@Override
	public int getExitY() {
		return exitY;
	}

	@Override
	public void init(int w, int h) {
		this.width = w;
		this.height = h;
		this.matLevel = new Nature[w][h];
		
		this.editing = false;
		
		// need some stuff here
		for(int i = 0; i < width; i++){ 
			matLevel[i][0] = Nature.METAL;
			 matLevel[i][height - 1] = Nature.METAL;
		}
	
		for(int j = 0; j < height; j++){
			matLevel[0][j] = Nature.METAL;
			matLevel[width - 1][j] = Nature.METAL;
		}
		
		
	}

	@Override
	public void setNature(int x, int y, Nature nat) {
		this.matLevel[x][y] = nat;
	}

	@Override
	public void goPlay() {
		this.editing = true;
	}

	@Override
	public void remove(int x, int y) {
		this.matLevel[x][y] = Nature.EMPTY;
	}

	@Override
	public void build(int x, int y) {
		this.matLevel[x][y] = Nature.DIRT;
		
	}

}
