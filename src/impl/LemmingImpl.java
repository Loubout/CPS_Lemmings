package impl;

import java.util.HashSet;
import java.util.Set;

import enumeration.Direction;
import enumeration.Nature;
import enumeration.Specialty;
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
	private int fallTime;
	private Set<Specialty> specials;
	private int bombCounter;
	private int nbBash = 0;
	private int nbBuild = 0;


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
		this.status = Status.LIVING;
		this.dir = Direction.RIGHT;
		this.type = Type.FALLER;

		this.specials = new HashSet<Specialty>();

		this.bombCounter = 0;
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
	
	/**
	 * 
	 */
	@Override
	public void step() {
		// if the lemming was recently given a type
		if (this.specials.contains(Specialty.STOPPER)){
			this.setType(Type.STOPPER);
		}else if (this.specials.contains(Specialty.BUILDER)){
			this.setType(Type.BUILDER);
		}else if(this.specials.contains(Specialty.DIGGER)){
			this.setType(Type.DIGGER);
		}else if(this.specials.contains(Specialty.CLIMBER) && this.type == Type.WALKER ){
			this.setType(Type.CLIMBER);
		}else if(this.specials.contains(Specialty.FLOATER)){
			this.setType(Type.FLOATER);
		}else if(this.specials.contains(Specialty.BASHER)){
			this.setType(Type.BASHER);
		}else if(this.specials.contains(Specialty.MINER)){
			this.setType(Type.MINER);
		}else if(this.specials.contains(Specialty.BOMBER)){
			this.activateBomber();
		}

		switch (this.type) {

		case FALLER:
				this.fall();
			break;

		case FLOATER:
			this.goFloat();
			break;
			
		case WALKER:
				this.walk();
			break;

		case CLIMBER:
			this.climb();
			break;
			
		case DIGGER:
				this.dig();
			break;

		case STOPPER:
			break;

		case BASHER:
				this.bash();
			break;
			
		case BUILDER:
				this.build();
			break;
			
		case MINER:
				this.mine();
			break;
		
		default:
			break;

		}
	}
	
	private void fall(){
		if (!eng.isObstacle(x , y + 1)){
			this.y = y + 1; //tomber d'une case
			this.incrementFallTime();
		} else if (eng.isObstacle(x, y + 1)){
			if (this.getFallTime() > 7){ // DIE
				System.out.println("DEATH FALL");
				this.setStatus(Status.DEAD);
			}else{ // SURVIVES

				this.setType(Type.WALKER);
				if (specials.contains(Specialty.CLIMBER)){ // avoiding climber going up again
					if(this.dir == Direction.RIGHT && eng.isObstacle(x+1, y))
						this.dir = Direction.LEFT;
					else if(this.dir == Direction.LEFT && eng.isObstacle(x-1, y))
						this.dir = Direction.RIGHT;
				}
				this.resetFallTime();
			}
		}
	}
	
	private void goFloat(){
		if (!eng.isObstacle(x , y + 1)){
			if(this.getFallTime() % 2 == 0) 
				this.y ++;	

			this.incrementFallTime();
		}else{
			this.setType(Type.WALKER);
			this.walk();
		}	
	}

	private void walk(){
		if (eng.getLevel().getNature(x, y + 1) == Nature.EMPTY){ // turn into faller
			this.setType(Type.FALLER);
			this.fall();
		}else if (this.dir == Direction.RIGHT){
			if(eng.isObstacle(x+1, y-1) ){
				this.dir = Direction.LEFT;
			}else if ((eng.isObstacle(x+1, y) && eng.isObstacle(x+1, y-2))){
				this.dir = Direction.LEFT;
			}else if (eng.isObstacle(x+1, y) && !eng.isObstacle(x+1, y-1) && !eng.isObstacle(x+1, y-2)){
				this.y--;
				this.x++;
			}else if (!eng.isObstacle(x+1, y) && !eng.isObstacle(x+1, y-1)){

				this.x++;	
			}
		}else{ // LEFT DIRECTION CASE
			if(eng.isObstacle(x-1, y-1)){
				this.dir = Direction.RIGHT;
			}else if((eng.isObstacle(x-1, y) && eng.isObstacle(x-1, y-2))){
				this.dir = Direction.RIGHT;
			}else if (eng.isObstacle(x-1, y) && !eng.isObstacle(x-1, y-1) && !eng.isObstacle(x-1, y-2)){
				this.x--;
				this.y--;
			}else if (!eng.isObstacle(x-1, y) && !eng.isObstacle(x-1, y-1)){
				this.x--;
			}
		}
	}
	
	private void climb(){
		if(this.dir == Direction.RIGHT 
				&& (eng.isObstacle(x+1, y) && eng.isObstacle(x+1, y+1))
				&& (eng.getLevel().getNature(x, y - 1) == Nature.EMPTY 
				&& eng.getLevel().getNature(x, y - 2) == Nature.EMPTY)){
			this.y --;
			
			if(eng.isObstacle(x+1, y+1) 
					&& eng.getLevel().getNature(x+1, y)==Nature.EMPTY 
					&& eng.getLevel().getNature(x+1, y-1)==Nature.EMPTY) 
				x++;
			
		}else if(this.dir == Direction.LEFT
				&&(eng.isObstacle(x-1, y) && eng.isObstacle(x-1, y+1))
				&& (eng.getLevel().getNature(x, y - 1) == Nature.EMPTY 
				&& eng.getLevel().getNature(x, y - 2) == Nature.EMPTY)){
			this.y --;
			
			if(eng.isObstacle(x-1, y+1) 
					&& eng.getLevel().getNature(x-1, y)==Nature.EMPTY 
					&& eng.getLevel().getNature(x-1, y-1)==Nature.EMPTY) 
				x--;
		
		}else{
			this.setType(Type.WALKER);
			this.walk(); //if can't climb just walk
		}

	}
	
	private void dig(){
		if(eng.getLevel().getNature(x, y + 1) == Nature.EMPTY){
			this.specials.remove(Specialty.DIGGER);//if fall not a digger anymore
			this.setType(Type.FALLER);
			this.fall();
		}else if(eng.getLevel().getNature(x, y + 1) == Nature.DIRT){
			this.getGameEng().getLevel().remove(x, y + 1);//down
			if(eng.getLevel().getNature(x - 1, y + 1) == Nature.DIRT){//down left
				this.getGameEng().getLevel().remove(x - 1, y + 1);
			} 
			if(eng.getLevel().getNature(x + 1, y + 1) == Nature.DIRT){//down right
				this.getGameEng().getLevel().remove(x + 1, y + 1);
			}
			this.y = this.y + 1;
		}else{
			this.specials.remove(Specialty.DIGGER);//if walk not a digger anymore
			this.setType(Type.WALKER);
			this.walk();
		}
	}
	
	private void bash(){
		if(!eng.isObstacle(x, y+1)){
			this.setType(Type.FALLER);
			this.fall();
		}else if(this.dir == Direction.RIGHT){
			if(eng.getLevel().getNature(x+1, y)==Nature.METAL
					|| eng.getLevel().getNature(x+1, y-1)==Nature.METAL
					|| eng.getLevel().getNature(x+1, y-2)==Nature.METAL){
				this.setType(Type.WALKER);
				this.walk();
			}else{
				if(nbBash > 10){
					this.specials.remove(Specialty.BASHER);//if bash limit reach remove the specialty
					this.setType(Type.WALKER);
					this.walk();
				}else{
					if(eng.getLevel().getNature(x+1, y)==Nature.DIRT
							|| eng.getLevel().getNature(x+1, y-1)==Nature.DIRT
							|| eng.getLevel().getNature(x+1, y-2)==Nature.DIRT){
						this.getGameEng().getLevel().remove(x + 1, y );
						this.getGameEng().getLevel().remove(x + 1, y - 1);
						this.getGameEng().getLevel().remove(x + 1, y - 2);
						this.nbBash++;
					}
					this.x++;
				}
			}
		}else if(this.dir == Direction.LEFT){
			if(eng.getLevel().getNature(x-1, y)==Nature.METAL
					|| eng.getLevel().getNature(x-1, y-1)==Nature.METAL
					|| eng.getLevel().getNature(x-1, y-2)==Nature.METAL){
				this.setType(Type.WALKER);
				this.walk();
			}else{
				if(nbBash > 20){
					this.specials.remove(Specialty.BASHER);//if bash limit reach remove the specialty
					this.setType(Type.WALKER);
					this.walk();
				}else{
					if(eng.getLevel().getNature(x-1, y)==Nature.DIRT
							|| eng.getLevel().getNature(x-1, y-1)==Nature.DIRT
							|| eng.getLevel().getNature(x-1, y-2)==Nature.DIRT){
						this.getGameEng().getLevel().remove(x - 1, y );
						this.getGameEng().getLevel().remove(x - 1, y - 1);
						this.getGameEng().getLevel().remove(x - 1, y - 2);
						this.nbBash++;
					}
					this.x--;
				}
			}
		}
	}
	
	private void build(){
		if(!eng.isObstacle(x, y+1)){
			this.setType(Type.FALLER);
			this.fall();
		}else if(this.dir == Direction.RIGHT){

			if(eng.getNbTours()%3 == 0){
				if(eng.getLevel().getNature(x+1, y)==Nature.EMPTY
						&& eng.getLevel().getNature(x+2, y)==Nature.EMPTY
						&& eng.getLevel().getNature(x+3, y)==Nature.EMPTY
						&& !eng.isObstacle(x+1, y-1) 	//obstacle en face 
						&& !eng.isObstacle(x+1, y-2)){ //obstacle en haut a droite

					if(eng.isObstacle(x+2, y-1)){
						//cas ou il y a un obstacle où il est
						//sensé etre apres construction,
						//on contruit quand meme mais il devient WALKER apres
						if(this.nbBuild < 12){
							eng.getLevel().setNature(x+1, y, Nature.DIRT);
							eng.getLevel().setNature(x+2, y, Nature.DIRT);
							eng.getLevel().setNature(x+3, y, Nature.DIRT);
							this.nbBuild += 3;
							this.specials.remove(Specialty.BUILDER);
							this.setType(Type.WALKER);
							this.walk();
						}else{
							this.specials.remove(Specialty.BUILDER); //if build limit reach remove specialty
							this.setType(Type.WALKER);
							this.walk();
						}
					}else{
						if(this.nbBuild < 12){
							eng.getLevel().setNature(x+1, y, Nature.DIRT);
							eng.getLevel().setNature(x+2, y, Nature.DIRT);
							eng.getLevel().setNature(x+3, y, Nature.DIRT);
							this.nbBuild += 3;
							this.x += 2;
							this.y--;
						}else{ 
							this.specials.remove(Specialty.BUILDER);//if build limit reach remove specialty
							this.setType(Type.WALKER);
							this.walk();
						}
					}
				}else{
					this.specials.remove(Specialty.BUILDER); //if build limit reach remove specialty
					this.setType(Type.WALKER);
					this.walk();
				}
			}
		}else if(this.dir == Direction.LEFT){

			if(eng.getNbTours()%3 == 0){
				if(eng.getLevel().getNature(x-1, y)==Nature.EMPTY
						&& eng.getLevel().getNature(x-2, y)==Nature.EMPTY
						&& eng.getLevel().getNature(x-3, y)==Nature.EMPTY
						&& !eng.isObstacle(x-1, y-1) 	//obstacle en face 
						&& !eng.isObstacle(x-1, y-2)){ //obstacle en haut a droite

					if(eng.isObstacle(x-2, y-1)){
						//cas ou il y a un obstacle où il est
						//sensé etre apres construction,
						//on contruit quand meme mais il devient WALKER apres
						if(this.nbBuild < 12){
							eng.getLevel().setNature(x-1, y, Nature.DIRT);
							eng.getLevel().setNature(x-2, y, Nature.DIRT);
							eng.getLevel().setNature(x-3, y, Nature.DIRT);
							this.nbBuild += 3;
							this.specials.remove(Specialty.BUILDER); //if build limit reach remove specialty
							this.setType(Type.WALKER);
							this.walk();
						}else{
							this.specials.remove(Specialty.BUILDER); //if build limit reach remove specialty
							this.setType(Type.WALKER);
							this.walk();
						}
					}else{
						if(this.nbBuild < 12){
							eng.getLevel().setNature(x-1, y, Nature.DIRT);
							eng.getLevel().setNature(x-2, y, Nature.DIRT);
							eng.getLevel().setNature(x-3, y, Nature.DIRT);
							this.nbBuild += 3;
							this.x -= 2;
							this.y--;
						}else {
							this.specials.remove(Specialty.BUILDER); //if build limit reach remove specialty
							this.setType(Type.WALKER);
							this.walk();
						}
					}
				}else{
					this.specials.remove(Specialty.BUILDER); //if build limit reach remove specialty
					this.setType(Type.WALKER);
					this.walk();
				}
			}
		}
	}
	
	private void mine(){
		if(!eng.isObstacle(x, y+1)){
			this.setType(Type.FALLER);
			this.fall();
		}else if(this.dir == Direction.RIGHT){
			if(eng.getLevel().getNature(x+1, y-1)==Nature.DIRT
					&& eng.getLevel().getNature(x+1, y-2)==Nature.DIRT
					&& eng.getLevel().getNature(x+1, y-3)==Nature.DIRT){
				this.getGameEng().getLevel().remove(x + 1, y - 1);
				this.getGameEng().getLevel().remove(x + 1, y - 2);
				this.getGameEng().getLevel().remove(x + 1, y - 3);
				this.x++;
				this.y--;
			}else{
				this.setType(Type.WALKER);
				this.walk();
			}
		}else if(this.dir == Direction.LEFT){
			if(eng.getLevel().getNature(x-1, y-1)==Nature.DIRT
					&& eng.getLevel().getNature(x-1, y-2)==Nature.DIRT
					&& eng.getLevel().getNature(x-1, y-3)==Nature.DIRT){
				this.getGameEng().getLevel().remove(x - 1, y - 1);
				this.getGameEng().getLevel().remove(x - 1, y - 2);
				this.getGameEng().getLevel().remove(x - 1, y - 3);
				this.x--;
				this.y--;
			}else{
				this.setType(Type.WALKER);
				this.walk();
			}
		}
	}
	
	private void activateBomber(){		
		if(this.getBombCounter() == 5){
			if(eng.getLevel().getNature(x, y+1) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x, y+1);
			if(eng.getLevel().getNature(x-1, y+1) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x-1, y+1);
			if(eng.getLevel().getNature(x-1, y) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x-1, y);
			if(eng.getLevel().getNature(x-2, y) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x-2, y);
			if(eng.getLevel().getNature(x-1, y-1) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x-1, y-1);
			if(eng.getLevel().getNature(x-2, y-1) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x-2, y-1);
			if(eng.getLevel().getNature(x-1, y-2) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x-1, y-2);
			if(eng.getLevel().getNature(x, y-2) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x, y-2);
			if(eng.getLevel().getNature(x+1, y-2) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x+1, y-2);
			if(eng.getLevel().getNature(x+1, y-1) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x+1, y-1);
			if(eng.getLevel().getNature(x+2, y-1) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x+2, y-1);
			if(eng.getLevel().getNature(x+1, y) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x+1, y);
			if(eng.getLevel().getNature(x+2, y) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x+2, y);
			if(eng.getLevel().getNature(x+1, y+1) == Nature.DIRT) 
				this.getGameEng().getLevel().remove(x+1, y+1);
			
			this.setStatus(Status.DEAD);
		}
		this.incrementBombCounter();
	}

	@Override
	public Set<Specialty> getSpecials() {
		return this.specials;
	}

	@Override
	public int getBombCounter() {
		return bombCounter;
	}
	private void incrementBombCounter(){
		this.bombCounter ++;
	}

	@Override
	public void transform(Specialty sp) {
		this.specials.add(sp);
	}
}