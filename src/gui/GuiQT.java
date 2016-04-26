package gui;

import com.trolltech.qt.gui.QApplication;


import impl.DisplayImpl;
import impl.GameEngImpl;
import impl.LevelImpl;
import services.LevelService;

public class GuiQT {


	public GuiQT() throws InterruptedException{
		DisplayImpl display = new DisplayImpl();
		GameEngImpl gameEng = new GameEngImpl();
		LevelService level = new LevelImpl();		


		level.init(60, 10);
		gameEng.init(10, 2);
		gameEng.bindLevel(level);

		Grid w = new Grid(null);
		w.bindEngine(gameEng);
		w.show();
		

		level.goPlay();

		int nbTours = 100;
		for (int i = 0 ; i < nbTours; i++){
			gameEng.nextTurn();
			w.repaint();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
		}
	}


	public static void main(String[] args) throws InterruptedException {
		QApplication.initialize(args);

		new GuiQT();

		QApplication.execStatic();
		QApplication.shutdown();
	}
}
