package gui;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPaintEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QWidget;

import enumeration.Direction;
import enumeration.Nature;
import enumeration.Specialty;
import enumeration.Type;
import services.GameEngService;
import services.LemmingService;

public class Grid extends QWidget{
	private QWidget parent;
	private GameEngService eng;
	private int tileSize = 16;

	int entranceX = -1;
	int entranceY = -1;
	int exitX = -1;
	int exitY = -1;

	Signal1<Boolean> pauseSignal;

	LemmingService selectedLemming;
	boolean paused = false;
	public Grid(QWidget parent, Signal1<Boolean> pauseSig){
		super(parent);
		this.pauseSignal = pauseSig;
		this.parent= parent;
		//	pauseSignal.connect(parent, "waitPlayer()");
	}

	//	public Signal1<Boolean> pauseSignal = new Signal1<Boolean>();

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
			// highlight selected lemming
			if (lemmy == this.selectedLemming){
				QPen pen = new QPen(QColor.yellow);
				pen.setWidth(3);
				painter.setPen(pen);
			}else{
				painter.setPen(QColor.white);
			}
			
			painter.setBrush(QColor.darkGreen);
			painter.drawRect(lemmy.getX()*tileSize, (lemmy.getY()-1)*tileSize, tileSize, tileSize);
			

			//			CLIMBER,
			//			DIGGER,
			//			STOPPER,
			//			BASHER,
			//			BUILDER,
			//			MINER,
			//			FLOATER,
			//			BOMBER
			
			if (lemmy.getSpecials().contains(Specialty.BOMBER)){
				painter.setBrush(QColor.darkYellow);
			}else if(lemmy.getType() == Type.BUILDER){
				painter.setBrush(QColor.darkMagenta);
			}else if(lemmy.getType() == Type.STOPPER){
				painter.setBrush(QColor.darkCyan);
			}else{
				painter.setBrush(QColor.green);
			}
			
			painter.drawRect(lemmy.getX()*tileSize, lemmy.getY()*tileSize, tileSize, tileSize);

			switch(lemmy.getType()){
			case WALKER:
				if(lemmy.getDirection() == Direction.RIGHT) 
					painter.drawText(lemmy.getX()*tileSize + 2, lemmy.getY()*tileSize, "R");
				else
					painter.drawText(lemmy.getX()*tileSize + 2, lemmy.getY()*tileSize, "L");
				break;
			case FALLER:
				painter.drawText(lemmy.getX()*tileSize + 2, lemmy.getY()*tileSize, "F");
				break;
			case CLIMBER:
				painter.drawText(lemmy.getX()*tileSize + 2, lemmy.getY()*tileSize, "C");
				break;
			case MINER:
				painter.drawText(lemmy.getX()*tileSize + 2, lemmy.getY()*tileSize, "M");
				break;	
			default:
				break;
			}

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
		}else{
			System.out.println("click lemmings !! ");
			// lemming selection
			if (eng.isThereLemming(coordX, coordY)){
				this.selectedLemming = eng.getLemmingAtPosition(coordX, coordY);
				pauseSignal.emit(true); 
			}else{
				this.selectedLemming = null;
				pauseSignal.emit(false);
			}

		}
	}
	public boolean isPaused(){
		return this.paused;
	}

}