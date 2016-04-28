package gui;


import com.trolltech.qt.QSignalEmitter;
import com.trolltech.qt.QThread;
import com.trolltech.qt.core.QSignalMapper;
import com.trolltech.qt.core.Qt.ConnectionType;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QButtonGroup;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

import enumeration.Specialty;
import impl.GameEngImpl;
import impl.LevelImpl;
import impl.PlayerImpl;
import services.GameEngService;
import services.LemmingService;
import services.LevelService;
import services.PlayerService;

public class GuiQT extends QMainWindow{

	private GameEngImpl gameEng;
	private LevelService level;
	private PlayerImpl player;
	private Grid w;

	public Signal0 repaintSig = new Signal0();
	public Signal1<Boolean> pauseSig = new Signal1<Boolean>();
	public GameRunnable r;
	public QThread gameThread;
	public boolean paused = false;

	public GuiQT() throws InterruptedException{

		QWidget mainWidget = new QWidget();
		mainWidget.resize(1600, 1600);

		//		DisplayImpl display = new DisplayImpl();
		gameEng = new GameEngImpl();
		level = new LevelImpl();
		player = new PlayerImpl();
	
		player.bindEngine(gameEng);
		
		level.init(60, 10);
		gameEng.init(10, 2);
		gameEng.bindLevel(level);

		w = new Grid(mainWidget, pauseSig);
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

		// class pick buttons
		QButtonGroup specialsButtonGroup = new QButtonGroup();
		//  FLOATER
		//  DIGGER,
		//	STOPPER,
		//	BASHER,
		//	BUILDER,
		//	MINER
		QPushButton floaterButton = new QPushButton(mainWidget);
		QPushButton diggerButton = new QPushButton(mainWidget);
		QPushButton stopperButton = new QPushButton(mainWidget);
		QPushButton basherButton = new QPushButton(mainWidget);
		QPushButton builderButton = new QPushButton(mainWidget);
		QPushButton minerButton = new QPushButton(mainWidget);

		specialsButtonGroup.addButton(floaterButton);
		specialsButtonGroup.addButton(diggerButton);
		specialsButtonGroup.addButton(stopperButton);
		specialsButtonGroup.addButton(basherButton);
		specialsButtonGroup.addButton(builderButton);
		specialsButtonGroup.addButton(minerButton);

		floaterButton.setText("Floater");
		diggerButton.setText("Digger");
		stopperButton.setText("Stopper");
		basherButton.setText("Basher");
		builderButton.setText("Builder");
		minerButton.setText("Miner");

		QSignalMapper classMapper = new QSignalMapper(this);
		classMapper.setMapping(floaterButton, "floater");
		classMapper.setMapping(diggerButton, "digger");
		classMapper.setMapping(stopperButton, "stopper");
		classMapper.setMapping(basherButton, "basher");
		classMapper.setMapping(builderButton, "builder");
		classMapper.setMapping(minerButton, "miner");

		classMapper.mappedString.connect(this, "setLemmingClass(String)");


		floaterButton.clicked.connect(classMapper, "map()");
		diggerButton.clicked.connect(classMapper, "map()");
		stopperButton.clicked.connect(classMapper, "map()");
		basherButton.clicked.connect(classMapper, "map()");
		builderButton.clicked.connect(classMapper, "map()");
		minerButton.clicked.connect(classMapper, "map()");


		QHBoxLayout specialsButtonLayout = new QHBoxLayout();

		specialsButtonLayout.addWidget(floaterButton);
		specialsButtonLayout.addWidget(diggerButton);
		specialsButtonLayout.addWidget(stopperButton);
		specialsButtonLayout.addWidget(basherButton);
		specialsButtonLayout.addWidget(builderButton);
		specialsButtonLayout.addWidget(minerButton);



		vlayout.addLayout(specialsButtonLayout);
		mainWidget.setLayout(vlayout);
		this.setCentralWidget(mainWidget);
		this.repaintSig.connect(this.w, "repaint()");

		w.bindEngine(gameEng);

		mainWidget.show();
	}

	public void setLemmingClass(String name){
		System.out.println(name);
		LemmingService lemmy = this.w.selectedLemming;
		switch (name){
		case "floater":
			player.transformLemming(lemmy, Specialty.FLOATER);
			break;
		case "digger":
			player.transformLemming(lemmy, Specialty.DIGGER);
			break;
		case "stopper":
			player.transformLemming(lemmy, Specialty.STOPPER);
			break;
		case "basher":
			player.transformLemming(lemmy, Specialty.BASHER);
			break;
		case "builder":
			player.transformLemming(lemmy, Specialty.BUILDER);
			break;
		case "miner":
			player.transformLemming(lemmy, Specialty.MINER);
			break;
		}
		pauseSig.emit(false);

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
						//						System.out.println("notify?");
						paused = false;
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				gameEng.nextTurn();
				this.repaintsig.emit();
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}    
			}
			System.out.println("end run");
		}

		public void pause(boolean p) throws InterruptedException{
			this.paused = p;
		}
	}

	public void play(){
		this.level.goPlay(this.w.entranceX, this.w.entranceY, this.w.exitX, this.w.exitY);
	}

	public void transform() throws InterruptedException{
		System.out.println("TRANSFORM");
		if (!paused){
			this.pauseSig.emit(true);
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
		this.r = new GameRunnable(this.gameEng, this.w, this.repaintSig);
		this.gameThread = new QThread(r);
		this.gameThread.setDaemon(true);
		this.gameThread.start();
		this.pauseSig.connect(this, "pause(boolean)");//, ConnectionType.QueuedConnection);
	}
	
	public void pause(boolean p) throws InterruptedException{
		System.out.println("PAUSE");
		if(p) this.r.pause(true);
		else{
			synchronized (this.gameThread) {
				this.r.pause(false);
				this.gameThread.notify();
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
