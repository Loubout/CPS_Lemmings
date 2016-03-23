package impl;

import java.util.Scanner;

import enumeration.Nature;
import services.LevelService;

public class LevelImpl implements LevelService {

	private int width;
	private int height;
	protected Nature[][] matLevel;
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

		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				matLevel[i][j] = Nature.EMPTY;
			}
		}
		// need some stuff here
		for(int i = 0; i < width; i++){ 
			matLevel[i][0] = Nature.METAL;
			matLevel[i][height - 1] = Nature.METAL;
		}

		for(int j = 0; j < height; j++){
			matLevel[0][j] = Nature.METAL;
			matLevel[width - 1][j] = Nature.METAL;
		}

		// THIS SHOULDNT BE HERE 
		this.entranceX = 1;
		this.entranceY = height - 2;
		this.exitX = width - 2;
		this.exitY = height - 2;
	}

	@Override
	public void setNature(int x, int y, Nature nat) {
		this.matLevel[x][y] = nat;
	}

	@Override
	public void goPlay() {
		
		System.out.println("Veuillez fournir les coordonées de l'entrée x,y");

		
		boolean checkEntrance = false;
		boolean checkExit = false;
		Scanner scanner = new Scanner(System.in);
		while (!checkEntrance){
			
			String inputEntrance[] = scanner.nextLine().split(",");
			while (inputEntrance.length != 2 || !isInteger(inputEntrance[0]) || !isInteger(inputEntrance[1])){
				System.out.println("FORMAT ERROR BRO");
				inputEntrance = scanner.nextLine().split(",");
			}
			
			int eX = Integer.parseInt(inputEntrance[0]);
			int eY = Integer.parseInt(inputEntrance[1]);
			
			// check out of bound
			if (eX < 0 || eX > width - 1 || eY < 0 || eY > height - 1) {
				System.out.println("OUT OF BOUND COORIDNATES");
				continue;
			}
			// check entrance conditions
			if (getNature(eX, eY) != Nature.EMPTY || getNature(eX, eY - 1) != Nature.EMPTY){
				System.out.println("Entrance cell and cell above should be empty");
				continue;
			}
			
			this.entranceX = eX;
			this.entranceY = height - eY;
			checkEntrance = true;
		}

		System.out.println("Veuillez fournir les coordonées de la sortie x,y");
		while (!checkExit){
			String inputExit[] = scanner.nextLine().split(",");
			
			while (inputExit.length != 2 || !isInteger(inputExit[0]) || !isInteger(inputExit[1])){
				System.out.println("FORMAT ERROR BRO");
				inputExit = scanner.nextLine().split(",");
			}
			
			int eX = Integer.parseInt(inputExit[0]);
			int eY = Integer.parseInt(inputExit[1]);
			
			// check out of bound
			
			if (eX < 0 || eX > width - 1 || eY < 0 || eY > height - 1 || (eX == entranceX && eY == entranceY)) {
				System.out.println("OUT OF BOUND COORIDNATES "+ eX + " " + eY);
				continue;
			}
			// check entrance conditions
			if (getNature(eX, eY) != Nature.EMPTY || getNature(eX, eY - 1) != Nature.EMPTY){
				System.out.println("Exit cell and cell above should be empty");
				continue;
			}
			
			this.exitX = eX;
			this.exitY = height - eY;
			checkExit = true;
		}
		scanner.close();
		
		this.editing = false;
	}

	private boolean isInteger(String string) {
		try {
			Integer.valueOf(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
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
