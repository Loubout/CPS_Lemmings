package impl;

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

		this.editing = true; // editing is on after init

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
	}

	@Override
	public void setNature(int x, int y, Nature nat) {
		this.matLevel[x][y] = nat;
	}

	@Override
	public void goPlay(int x1, int y1, int x2, int y2) {
		this.entranceX = x1;
		this.entranceY = y1;
		this.exitX = x2;
		this.exitY = y2;
		
//		while (!checkEntrance){
//
//			String inputEntrance[] = scanner.nextLine().split(",");
//			while (inputEntrance.length != 2 || !isInteger(inputEntrance[0]) || !isInteger(inputEntrance[1])){
//				System.out.println("FORMAT ERROR BRO");
//				inputEntrance = scanner.nextLine().split(",");
//			}
//
//			int eX = Integer.parseInt(inputEntrance[0]);
//			int eY = Integer.parseInt(inputEntrance[1]);
//
//			// check out of bound
//			if (eX < 0 || eX > width - 1 || eY < 0 || eY > height - 1) {
//				System.out.println("OUT OF BOUND COORIDNATES");
//				continue;
//			}
//			// check entrance conditions
//			if (getNature(eX, eY) != Nature.EMPTY || getNature(eX, eY - 1) != Nature.EMPTY){
//				System.out.println("Entrance cell and cell above should be empty");
//				continue;
//			}
//
//			this.entranceX = eX;
//			this.entranceY = height - eY;
//			checkEntrance = true;
//		}
//
//		System.out.println("Veuillez fournir les coordonées de la sortie: x,y");
//		while (!checkExit){
//			String inputExit[] = scanner.nextLine().split(",");
//
//			while (inputExit.length != 2 || !isInteger(inputExit[0]) || !isInteger(inputExit[1])){
//				System.out.println("FORMAT ERROR BRO");
//				inputExit = scanner.nextLine().split(",");
//			}
//
//			int eX = Integer.parseInt(inputExit[0]);
//			int eY = Integer.parseInt(inputExit[1]);
//
//			// check out of bound
//
//			if (eX < 0 || eX > width - 1 || eY < 0 || eY > height - 1 || (eX == entranceX && eY == entranceY)) {
//				System.out.println("OUT OF BOUND COORIDNATES "+ eX + " " + eY);
//				continue;
//			}
//			// check entrance conditions
//			if (getNature(eX, eY) != Nature.EMPTY || getNature(eX, eY - 1) != Nature.EMPTY){
//				System.out.println("Exit cell and cell above should be empty");
//				continue;
//			}
//
//			this.exitX = eX;
//			this.exitY = height - eY;
//			checkExit = true;
//		}


//		System.out.println("Veuillez fournir les coordonées et la nature de la case à éditer: x,y,NATURE (DIRT, METAL, EMPTY)");
//		System.out.println("Saisir goPlay si vous désirez quitter le mode editing");
//		
//		while (editing){
//			
//			System.out.println("Votre saisie :");
//			String inputEdit[] = scanner.nextLine().split(",");
//
//			while (inputEdit.length != 3 || !isInteger(inputEdit[0]) || !isInteger(inputEdit[1]) || !(inputEdit[2] instanceof String)){
//				if(inputEdit[0].compareTo("goPlay")==0){
//					editing = false;
//					break;
//				}else{
//					System.out.println("FORMAT ERROR BRO");
//					inputEdit = scanner.nextLine().split(",");
//				}
//			}
//			if(inputEdit.length == 3){
//				int eX = Integer.parseInt(inputEdit[0]);
//				int eY = Integer.parseInt(inputEdit[1]);
//				Nature nat = getNatureFromString(inputEdit[2]);
//
//				// check out of bound
//				if (eX < 0 || eX > width - 1 || eY < 0 || eY > height - 1 || (eX == entranceX && eY == entranceY)) {
//					System.out.println("OUT OF BOUND COORIDNATES "+ eX + " " + eY);
//					continue;
//				}
//
//				//edit the specified square
//				this.setNature(eX, eY, nat);
//			}
//		}

//		scanner.close();

		this.editing = false;
	}

//	private Nature getNatureFromString(String nat){
//		if(nat.compareTo("DIRT")==0)
//			return Nature.DIRT;
//		else if(nat.compareTo("EMPTY")==0)
//			return Nature.EMPTY;
//		else if(nat.compareTo("METAL")==0)
//			return Nature.METAL;
//		else return null;
//	}
//	
//	private boolean isInteger(String string) {
//		try {
//			Integer.valueOf(string);
//			return true;
//		} catch (NumberFormatException e) {
//			return false;
//		}
//	}

	@Override
	public void remove(int x, int y) {
		this.matLevel[x][y] = Nature.EMPTY;
	}

	@Override
	public void build(int x, int y) {
		this.matLevel[x][y] = Nature.DIRT;

	}

}
