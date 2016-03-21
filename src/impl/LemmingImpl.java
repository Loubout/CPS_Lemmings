package impl;

import enumeration.Direction;
import enumeration.Status;
import enumeration.Type;
import services.GameEngService;
import services.LemmingService;
import services.RequireGameEngineService;

public class LemmingImpl implements RequireGameEngineService, LemmingService{

	private GameEngService eng;
	private Direction dir;
	private Type type;
	private Status status;
	private int num;
	private int x;
	private int y;
	
	
	

	@Override
	public void bindEngine(GameEngService eng) {
		this.eng = eng;
		
	}

	@Override
	public Direction getDirection() {
		return dir;
	}

	@Override
	public int getNumber() {
		return num;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Status getStatus() {
		return status;
		
	}

	@Override
	public int getx() {
		return x;
	}

	@Override
	public int getY() {	
		return y;
	}

	@Override
	public int getFallTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GameEngService getGameEng() {
		return eng;
	}

	@Override
	public void init(int num) {
		this.x = this.eng.getLevel().getEntranceX();
		this.y = this.eng.getLevel().getEntranceY();
		this.num = num;
		this.dir = Direction.RIGHT;
		this.type = Type.WALKER; // pour implem minimale
		
	}

	@Override
	public void step() {
		this.x = this.x + 1; // marcher vers a droite yo
		
	}


	

}
