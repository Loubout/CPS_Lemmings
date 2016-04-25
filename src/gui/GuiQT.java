package gui;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QWidget;

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
		int nbTours = 50;
		int cpt = 0;
		while (cpt < nbTours){
			if (!w.isPaused()){
				gameEng.nextTurn();
				w.repaint();
				Thread.sleep(100); 
				cpt++;
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
