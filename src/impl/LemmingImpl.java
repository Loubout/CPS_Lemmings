package impl;

import java.util.Set;

import services.GameEngService;
import services.LemmingService;
import services.RequireGameEngineService;
import enumeration.Direction;
import enumeration.Nature;
import enumeration.Specialty;
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
	private Set<Specialty> specials;


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
//		System.out.println(eng.getLevel());
		this.x = this.eng.getLevel().getEntranceX();
		this.y = this.eng.getLevel().getEntranceY();
		this.num = num;
		this.status = Status.LIVING;
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
				if (eng.getLevel().getNature(x + 1, y) == Nature.EMPTY){//cas simple aucun obstacle
					this.x = this.x + 1; // marcher vers la droite
				}else if(eng.isObstacle(x+1, y) && 
						(eng.getLevel().getNature(x + 1, y-1) == Nature.EMPTY 
						&& eng.getLevel().getNature(x + 1, y-2) == Nature.EMPTY)){ //cas ou il y a un obstacle à sa droite
					this.x = x + 1;
					this.y = y - 1 ;
				}else
					this.dir = Direction.LEFT;
			}else if (this.dir == Direction.LEFT){
				if (eng.getLevel().getNature(x - 1, y) == Nature.EMPTY){
					this.x = this.x - 1; // marcher vers la droite 
				}
				else if (eng.isObstacle(x - 1, y) && 
						(eng.getLevel().getNature(x - 1, y-1) == Nature.EMPTY 
						&& eng.getLevel().getNature(x - 1, y-2) == Nature.EMPTY)){ //cas ou il y a un obstacle à sa gauche{
					this.x = x - 1;
					this.y = y - 1 ;
				}else
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
		case CLIMBER:
			if(eng.isObstacle(x+1, y) && eng.isObstacle(x+1, y+1)){
				climb();
			}else{
				this.setType(Type.WALKER);
			}
		}
	}
	
	private void climb(){
		this.y = y - 1;
	}

	@Override
	public Set<Specialty> getSpecials() {
		return this.specials;
	}
}
