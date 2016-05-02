package test;

import java.util.Scanner;

import contract.GameEngContract;
import contract.LevelContract;
import enumeration.Nature;
import impl.DisplayImpl;
import impl.GameEngImpl;
import impl.LevelImpl;

public class LemmingsTestMinimalAvecContrat {


	public static void main(String[] args) throws InterruptedException {

		DisplayImpl display = new DisplayImpl();
		GameEngImpl gameEngService = new GameEngImpl();
		LevelImpl levelService = new LevelImpl();		

		GameEngContract gameEng = new GameEngContract(gameEngService);
		LevelContract level = new LevelContract(levelService);

		level.init(100, 40);
		gameEng.init(12, 2);
		gameEng.bindLevel(level);
		
		System.out.println("main gameeng level " + gameEng.getLevel());
		System.out.println(gameEng.getLevel());
		display.bindEngine(gameEng);


		System.out.println("Veuillez fournir les coordonées et la nature de la case à éditer: x,y,NATURE (DIRT, METAL, EMPTY)");
		System.out.println("Saisir goPlay si vous désirez quitter le mode editing");
		System.out.println(level.getHeight());
		display.displayLevel();
		
		Scanner scanner = new Scanner(System.in);
//		while (level.editing()){
//			
//			System.out.println("Votre saisie :");
//			String inputEdit[] = scanner.nextLine().split(",");
//
//			while (inputEdit.length != 3 || !isInteger(inputEdit[0]) || !isInteger(inputEdit[1]) || !(inputEdit[2] instanceof String)){
//				if(inputEdit[0].compareTo("goPlay")==0){
//					level.goPlay();
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
//				if (eX < 0 || eX > level.getWidth()- 1 || eY < 0 || eY > level.getHeight()- 1 || (eX == level.getEntranceX() && eY == level.getEntranceY())) {
//					System.out.println("OUT OF BOUND COORIDNATES "+ eX + " " + eY);
//					continue;
//				}
//
//				//edit the specified square
//				level.setNature(eX, eY, nat);
//			}	
//		}
//		scanner.close();




		while(!gameEng.gameOver()){
			gameEng.nextTurn();
			display.displayLevel();
			Thread.sleep(300);    
		}
		display.displayLevel();
	}

	private static Nature getNatureFromString(String nat){
		if(nat.compareTo("DIRT")==0)
			return Nature.DIRT;
		else if(nat.compareTo("EMPTY")==0)
			return Nature.EMPTY;
		else if(nat.compareTo("METAL")==0)
			return Nature.METAL;
		else return null;
	}

	private static boolean isInteger(String string) {
		try {
			Integer.valueOf(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}


