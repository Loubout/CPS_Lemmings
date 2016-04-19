package impl;

import services.GameEngService;
import services.LemmingService;
import services.RequireGameEngineService;
import enumeration.Direction;
import enumeration.Nature;
import enumeration.Status;
import enumeration.Type;

public class LemmingImpl implements RequireGameEngineService, LemmingService{

	private GameEngService eng;
	private Direction dir;
	private Type type;
	private Status status;
	private int num;
	private int x;
	private int y;
	private int fallTime;


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
	public int getX() {
		return x;
	}

	@Override
	public int getY() {	
		return y;
	}

	@Override
	public int getFallTime() {
		return fallTime;
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
		//this.type = Type.WALKER; // pour implem minimale
		this.type = Type.FALLER;
	}

	private void setType(Type t){
		this.type = t;
	}

	private void incrementFallTime(){
		this.fallTime ++;
	}

	private void resetFallTime() {
		this.fallTime = 0;
	}

	private void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public void step() {
		switch (this.type) {
		case FALLER:
			if (!eng.isObstacle(x , y + 1)){
				this.y = y + 1; //tomber d'une case
				this.incrementFallTime();
			} else if (eng.isObstacle(x, y + 1)){
				if (this.getFallTime() > 7){ // DIE
					this.setStatus(Status.DEAD);
				}else{ // SURVIVES
					this.setType(Type.WALKER);
					this.resetFallTime();
				}
			}			
			break;
		case WALKER:
			if (eng.getLevel().getNature(x, y + 1) == Nature.EMPTY){ // turn into faller
				this.setType(Type.FALLER);
			}else if (this.dir == Direction.RIGHT){
				if (eng.getLevel().getNature(x + 1, y) == Nature.EMPTY)
					this.x = this.x + 1; // marcher vers la droite 
				else
					this.dir = Direction.LEFT;
			}else{
				if (eng.getLevel().getNature(x - 1, y) == Nature.EMPTY)
					this.x = this.x - 1; // marcher vers la droite 
				else
					this.dir = Direction.RIGHT;
			}
		case DIGGER:
			if(eng.getLevel().getNature(x, y + 1) == Nature.EMPTY){
				this.setType(Type.FALLER);
			}else if(eng.getLevel().getNature(x, y + 1) == Nature.METAL){
				this.setType(Type.WALKER);
			}else if(eng.getLevel().getNature(x, y + 1) == Nature.DIRT){
				this.y = this.y + 1;
				if(eng.getLevel().getNature(x - 1, y + 1) == Nature.DIRT){//down left
					this.getGameEng().getLevel().remove(x - 1, y + 1);
				} 
				if(eng.getLevel().getNature(x + 1, y + 1) == Nature.DIRT){//down right
					this.getGameEng().getLevel().remove(x + 1, y + 1);
				} 
			}
			break;
		}
	}
}
