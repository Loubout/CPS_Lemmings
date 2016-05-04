package test;

import gui.GuiQT;
import impl.GameEngImpl;
import impl.LevelImpl;

import java.util.HashMap;

import com.trolltech.qt.gui.QApplication;

import contract.GameEngContract;
import contract.LevelContract;
import contract.PlayerContract;
import enumeration.Specialty;
import services.GameEngService;
import services.LevelService;
import services.PlayerService;

public class LemmingsTestGUIAvecContrat {

	public static void main(String[] args) throws InterruptedException {

		// this one has no decorator
		PlayerService player = new PlayerContract();

		LevelService level = new LevelImpl();
		LevelContract contract_level = new LevelContract(level);

		GameEngService eng = new GameEngImpl();
		GameEngContract contract_eng = new GameEngContract(eng);

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
		contract_level.init(60, 40);
		contract_eng.init(1, 1);

		// END INIT PHASE
		
		// BINDING PHASE

		player.bindEngine(contract_eng);
		contract_eng.bindLevel(contract_level);



		QApplication.initialize(args);
		GuiQT gui = new GuiQT();
		gui.bindEngine(contract_eng);
		gui.bindLevel(contract_level);
		gui.bindPlayer(player);
		gui.show();
		gui.resize(1024, 1980);
		QApplication.execStatic();
		QApplication.shutdown();
	}
}