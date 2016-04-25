package gui;

import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPaintEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QWidget;

import enumeration.Nature;
import services.GameEngService;
import services.LemmingService;

public class Grid extends QWidget{

	private QWidget parent;
	private GameEngService eng;
	private int tileSize = 16;
	
	boolean placeEntrance = false;
	boolean placeExit = false;
	
	int selectedLemming;
	
	public Grid(QWidget parent){
		super(parent);
		this.parent= parent;
		this.setGeometry(0, 0, 2000, 2000);
	}
	
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
		
		if (!eng.getLevel().editing()){
			painter.setBrush(QColor.black); // entrance
			painter.drawRect(eng.getLevel().getEntranceX()*tileSize, eng.getLevel().getEntranceY()*tileSize, tileSize, tileSize);
			painter.setBrush(QColor.red); // sortie
			painter.drawRect(eng.getLevel().getExitX()*tileSize, eng.getLevel().getExitY()*tileSize, tileSize, tileSize);
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
		int coordX = e.x() / tileSize;
		int coordY = e.y() / tileSize;
//		int coordY = (tileSize * eng.getLevel().getHeight() - e.y()) / tileSize;
		System.out.println("result : " + coordX + "," + coordY);
		e.accept();
		
		
		
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
		this.repaint();
	}
	
	
	
}