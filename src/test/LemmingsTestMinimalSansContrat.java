package test;

import impl.DisplayImpl;
import impl.GameEngImpl;
import impl.LevelImpl;
import services.LevelService;

public class LemmingsTestMinimalSansContrat {

	public static void main(String[] args) throws InterruptedException {
		
		DisplayImpl display = new DisplayImpl();
		GameEngImpl gameEng = new GameEngImpl();
		LevelService level = new LevelImpl();		
		
		
		level.init(60, 10);
		gameEng.init(10, 2);
		gameEng.bindLevel(level);
		
		display.bindEngine(gameEng);
		
//		level.goPlay();
//		int nbTours = 100;
//		for (int i = 0 ; i < nbTours; i++){
//			gameEng.nextTurn();
//			display.displayLevel();
//			Thread.sleep(300);    
//		}
		
		display.displayLevel();
		

	}

}
