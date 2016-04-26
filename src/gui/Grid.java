package gui;

import impl.DisplayImpl;
import impl.GameEngImpl;
import impl.LevelImpl;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Global.QtMsgType;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPaintEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QWidget;

import enumeration.Nature;
import services.GameEngService;
import services.LemmingService;
import services.LevelService;

public class Grid extends QWidget{
	private QWidget parent;
	private GameEngService eng;
	private int tileSize = 16;



	int entranceX = -1;
	int entranceY = -1;
	int exitX = -1;
	int exitY = -1;


	int selectedLemming;
	boolean paused = false;
	public Grid(QWidget parent){
		super(parent);
		this.parent= parent;
		//	pauseSignal.connect(parent, "waitPlayer()");
	}

	public Signal1<Boolean> pauseSignal = new Signal1<Boolean>();

	public void bindEngine(GameEngService eng){
		this.eng = eng;
	}

	protected void paintEvent(QPaintEvent e){
		QPainter painter = new QPainter(this);

		QPen p = new QPen();
		p.setColor(QColor.white);
		painter.setPen(p); // no contour on tiles
		QColor dirt = QColor.darkRed;
		QColor empty = QColor.white;
		QColor metal = QColor.gray;



		// drawing level
		for (int i = 0; i < eng.getLevel().getWidth(); i++){
			for (int j = 0; j < eng.getLevel().getHeight(); j++){
				switch(eng.getLevel().getNature(i, j)){
				case DIRT:
					painter.setBrush(dirt);
					break;
				case EMPTY:
					painter.setBrush(empty);
					break;
				case METAL:
					painter.setBrush(metal);
					break;
				}
				painter.drawRect(i*tileSize, j*tileSize, tileSize, tileSize);
			}
		}
		// drawing entrance & exit

	
		if (entranceX != -1 && entranceY != -1){
			painter.setBrush(QColor.black); // entrance
			painter.drawRect(entranceX*tileSize, entranceY*tileSize, tileSize, tileSize);
		}
		if (exitX != -1 && exitY != -1){
			painter.setBrush(QColor.red); // sortie
			painter.drawRect(exitX*tileSize, exitY*tileSize, tileSize, tileSize);
		}


		// drawing lemmings

		for (int num : eng.getLemmingsNum()){

			LemmingService lemmy = eng.getLemming(num);
			painter.setBrush(QColor.darkGreen);
			painter.drawRect(lemmy.getX()*tileSize, (lemmy.getY()-1)*tileSize, tileSize, tileSize);
			painter.setBrush(QColor.green);
			painter.drawRect(lemmy.getX()*tileSize, lemmy.getY()*tileSize, tileSize, tileSize);
		}
		painter.end();
	}

	@Override
	protected void mouseReleaseEvent(QMouseEvent e){
		System.out.println(e.x() + "," + e.y());
		System.out.println(e.button().toString());
		int coordX = e.x() / tileSize;
		int coordY = e.y() / tileSize;
		//int coordY = (tileSize * eng.getLevel().getHeight() - e.y()) / tileSize;
		System.out.println("result : " + coordX + "," + coordY);

		if (eng.getLevel().editing()){
			if (e.button() == Qt.MouseButton.LeftButton){
				// edit this tile
				Nature next = null;
				switch (eng.getLevel().getNature(coordX, coordY)){
				case EMPTY:
					next = Nature.DIRT;
					break;
				case DIRT:
					next = Nature.METAL;
					break;
				case METAL:
					next = Nature.EMPTY;
					break;
				}
				eng.getLevel().setNature(coordX, coordY, next);
			}else if (e.button() == Qt.MouseButton.RightButton){
				System.out.println("rightbutton click");
				if (coordX == entranceX && coordY == entranceY){ // entrance bcomes exit
					entranceX = -1;
					entranceY = -1;				
				}else if (coordX == exitX && coordY == exitY){ // reset 
					exitX = -1;
					exitY = -1;
				}else{ // if it's a new cell
					if (entranceX != -1 && entranceY != -1){
						exitX = coordX;
						exitY = coordY;
					}else{
						entranceX = coordX;
						entranceY = coordY;
					}
				}
			}
			this.repaint();
			System.out.println("entrance " + entranceX + "," + entranceY);
			System.out.println("exit " + exitX + "," + exitY);
		}else{
			System.out.println("click lemmings !! ");
			// lemming selection
			for (int num : eng.getLemmingsNum()){
				LemmingService lemmy = eng.getLemming(num);
				if (coordX == lemmy.getX() && coordY == lemmy.getY()){
					System.out.println("lemming selected id " + lemmy.getNumber());
					this.paused = true;
					pauseSignal.emit(true); 
					break;
				}
			}
		}
	}
	public boolean isPaused(){
		return this.paused;
	}

}