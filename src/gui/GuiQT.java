package gui;


import com.trolltech.qt.QThread;
import com.trolltech.qt.core.Qt.ConnectionType;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

import impl.GameEngImpl;
import impl.LevelImpl;
import services.GameEngService;
import services.LevelService;

public class GuiQT extends QMainWindow{

	private GameEngImpl gameEng;
	private LevelService level;
	private Grid w;

	public Signal0 repaintSig = new Signal0();
	public Signal0 pauseSig = new Signal0();
	public QThread gameThread;
	public boolean paused = false;

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
		playBtn.clicked.connect(this, "play()");


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
		this.repaintSig.connect(this.w, "repaint()");

		w.bindEngine(gameEng);

		mainWidget.show();
	}

	public class GameRunnable implements Runnable{
		GameEngService gameEng;
		Grid g;
		Signal0 repaintsig;
		boolean paused = false;

		public GameRunnable(GameEngService s, Grid g, Signal0 repaintsig) {
			repaintSig.connect(g, "repaint()");
			this.gameEng = s;
			this.g = g;
			this.repaintsig = repaintsig;

		}

		@Override
		public void run() {
			System.out.println("GAME");
			while(!gameEng.gameOver() ){
				if (paused){
					try {
						System.out.println("before wait");
						synchronized (Thread.currentThread()) {
							Thread.currentThread().wait();
						}
						System.out.println("notify?");
						paused = false;
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				gameEng.nextTurn();
				this.repaintsig.emit();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}    
			}
			System.out.println("end run");
		}

		public void pause() throws InterruptedException{
			this.paused = !this.paused;
		}
	}

	public void play(){
		this.level.goPlay(this.w.entranceX, this.w.entranceY, this.w.exitX, this.w.exitY);
	}

	public void transform() throws InterruptedException{
		System.out.println("TRANSFORM");
		if (!paused){
			this.pauseSig.emit();
		}else{
//			this.pauseSig.emit();
			System.out.println("before notify");
			synchronized (this.gameThread) {
				this.gameThread.notify();
			}

		}
		this.paused = !this.paused;
		System.out.println("end transform");
	}

	public void game(){
		if (this.gameThread != null) this.gameThread.start();
		else {
			GameRunnable r = new GameRunnable(this.gameEng, this.w, this.repaintSig);
			this.gameThread = new QThread(r);
			this.gameThread.setDaemon(true);
			this.gameThread.start();
			this.pauseSig.connect(r, "pause()", ConnectionType.QueuedConnection);
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
