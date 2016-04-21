package test;

import services.GameEngService;
import contract.GameEngContract;
import contract.LevelContract;
import impl.DisplayImpl;
import impl.GameEngImpl;
import impl.LevelImpl;

public class LemmingTestAvecContrat {


	public static void main(String[] args) throws InterruptedException {

		DisplayImpl display = new DisplayImpl();
		GameEngImpl gameEngService = new GameEngImpl();
		LevelImpl levelService = new LevelImpl();		

		GameEngContract gameEng = new GameEngContract(gameEngService);
		LevelContract level = new LevelContract(levelService);

		level.init(100, 40);
		gameEng.init(12, 2);
		gameEng.bindLevel(level);
		
		System.out.println(gameEng.getLevel());
		display.bindEngine(gameEng);
		System.out.println("before goplay contract main");
		level.goPlay();
		//int nbTours = 100;
		System.out.println("before for loop contract main");
		while(!gameEng.gameOver()){
			gameEng.nextTurn();
			display.displayLevel();
			Thread.sleep(300);    
		}
		display.displayLevel();
	}

}


