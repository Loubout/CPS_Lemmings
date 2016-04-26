package gui;

import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.QThread;
import com.trolltech.qt.core.Qt.LayoutDirection;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QButtonGroup;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLayoutItemInterface;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTapGesture;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

import impl.DisplayImpl;
import impl.GameEngImpl;
import impl.LevelImpl;
import services.GameEngService;
import services.LevelService;

public class GuiQT extends QMainWindow{
	
	private GameEngImpl gameEng;
	private LevelService level;
	private Grid w;
	private boolean paused= false;
	public GuiQT() throws InterruptedException{
		
		QWidget mainWidget = new QWidget();
		mainWidget.resize(1600, 1600);
		
//		DisplayImpl display = new DisplayImpl();
		gameEng = new GameEngImpl();
		level = new LevelImpl();		

		level.init(60, 10);
		gameEng.init(1, 2);
		gameEng.bindLevel(level);

		w = new Grid(mainWidget);
		w.resize(1024, 1024);
		
		QPushButton playBtn = new QPushButton(mainWidget);
		playBtn.setText("ready");
		playBtn.clicked.connect(this.level, "goPlay()");
			
		QPushButton launchBtn = new QPushButton(mainWidget);
		launchBtn.setText("GAME");
		launchBtn.clicked.connect(this, "game()");
		
		QPushButton transformBtn = new QPushButton(mainWidget);
		transformBtn.setText("transform");
		transformBtn.clicked.connect(this, "transform()");
		
		// main layout
		QVBoxLayout vlayout = new QVBoxLayout();
		vlayout.addWidget(w);
		
		// buttons line
		QHBoxLayout hlayout = new QHBoxLayout();
		hlayout.addWidget(playBtn);
		hlayout.addWidget(launchBtn);
		hlayout.addWidget(transformBtn);
		
		vlayout.addLayout(hlayout);
		
		mainWidget.setLayout(vlayout);
		
		this.setCentralWidget(mainWidget);
		
		w.bindEngine(gameEng);
		
		mainWidget.show();
		
	}
	
	public class GameRunnable implements Runnable{
		GameEngService gameEng;
		Grid g;
		QSignalEmitter.Signal0 repaintSig = new Signal0();
		
		public GameRunnable(GameEngService s, Grid g) {
			repaintSig.connect(g, "repaint()");
			this.gameEng = s;
			this.g = g;
		}
		@Override
		public void run() {
			while(gameEng.getLevel().editing());
			System.out.println("GAME");
			while(!gameEng.gameOver()){
				if (paused)
					try {
						wait();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				gameEng.nextTurn();
				repaintSig.emit();
//				g.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}    
			}
		}	
	}
	
	public void transform(){
		System.out.println("TRANSFORM");
		this.paused = !this.paused;
	}
	
	public void game(){
//		GameRunnable r = new GameRunnable(this.gameEng, this.w);
//		QThread th = new QThread(r);
//		th.start();
		while(!gameEng.gameOver()){
			this.gameEng.nextTurn();
			this.w.repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		QApplication.initialize(args);
		GuiQT app = new GuiQT();
		
		app.show();
		
		app.resize(1024, 1980);
		
		QApplication.execStatic();
		QApplication.shutdown();
	}
}
