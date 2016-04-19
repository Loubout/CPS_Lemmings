package impl;

import java.util.Set;

import enumeration.Direction;
import services.DisplayService;
import services.GameEngService;
import services.LemmingService;
import services.LevelService;
import services.RequireGameEngineService;

public class DisplayImpl implements RequireGameEngineService, DisplayService {

	private GameEngService eng;

	public DisplayImpl() {}

	public void init(){}


	@Override
	public String getASCIIDisplay() {
		LevelService level = eng.getLevel();
		Set<Integer> lemmings = eng.getLemmingsNum();
		String display = "";

		for (int j = 0; j < level.getHeight(); j++){
			for (int i = 0; i < level.getWidth(); i++){

				if (i == eng.getLevel().getEntranceX() && j == eng.getLevel().getEntranceY()){
					display += "E";
					continue;
				}

				if (i == eng.getLevel().getExitX() && j == eng.getLevel().getExitY()) {
					display += "S";
					continue;
				}

				boolean drawLemming = false;
				for(Integer lemIndex: lemmings){
					LemmingService lem = eng.getLemming(lemIndex);
					if (i == lem.getX() && j == lem.getY()){
						if (lem.getDirection() == Direction.LEFT) display += "L";
						if (lem.getDirection() == Direction.RIGHT) display += "R";
						drawLemming = true;
						break;
					}
				}
				
				if (drawLemming) continue;

				switch (level.getNature(i, j)){
				case DIRT:
					display += "D";
					break;
				case EMPTY:
					display += " ";
					break;
				case METAL:
					display += "M";
					break;
				}
			}
			display += "\n";
		}

		return display;

	}

	
	@Override
	public void bindEngine(GameEngService eng) {
		this.eng = eng;

	}

	@Override
	public void displayLevel() {
		System.out.println(getASCIIDisplay());
		
	}

}
