package test;

import impl.GameEngImpl;
import impl.LevelImpl;

import java.util.HashMap;

import services.GameEngService;
import services.LevelService;
import services.PlayerService;

import com.trolltech.qt.gui.QApplication;

import contract.GameEngContract;
import contract.LevelContract;
import contract.PlayerContract;
import enumeration.Specialty;
import gui.GuiQT;

public class LemmingsTestGUISansContrat {
	public static void main(String[] args) throws InterruptedException {

		// this one has no decorator
		PlayerService player = new PlayerContract();

		LevelService level = new LevelImpl();
		
		GameEngService eng = new GameEngImpl();
		

		// INIT PHASE

		// hashmap for init
		HashMap<Specialty, Integer> chips =  new HashMap<Specialty, Integer>();		
		chips.put(Specialty.CLIMBER, 5);
		chips.put(Specialty.DIGGER, 5);
		chips.put(Specialty.STOPPER, 5);
		chips.put(Specialty.BASHER, 5);
		chips.put(Specialty.BUILDER, 5);
		chips.put(Specialty.MINER, 5);
		chips.put(Specialty.FLOATER, 5);
		chips.put(Specialty.BOMBER, 5);

		player.init(chips);
		level.init(60, 40);
		eng.init(10, 2);

		// END INIT PHASE
		
		// BINDING PHASE

		player.bindEngine(eng);
		eng.bindLevel(level);


		QApplication.initialize(args);
		GuiQT gui = new GuiQT();
		gui.bindEngine(eng);
		gui.bindLevel(level);
		gui.bindPlayer(player);

		gui.show();
		gui.resize(1024, 1980);
		QApplication.execStatic();
		QApplication.shutdown();
	}
}


