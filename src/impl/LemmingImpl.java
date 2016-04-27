package impl;

import java.util.HashSet;
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
	private int nbSteps;
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
	//	//------------------TEMPORARY----------------------//
	//	public LemmingImpl lemmyStopper(){
	//		LemmingImpl stopper = new LemmingImpl();
	//		stopper.setType(Type.STOPPER);
	//		stopper.setX(20);
	//		stopper.setY(18);
	//		
	//		return stopper;
	//	}
	//	
	//	public void setX(int x){
	//		this.x = x;
	//	}
	//	public void setY(int y){
	//		this.y = y;
	//	}
	////---------------------------------------------------//
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

		//Provisoir pour test
		this.specials = new HashSet<Specialty>();
//		specials.add(Specialty.BOMBER);

		this.nbSteps = 0;
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
		switch (this.type) {

		case FALLER:
			if(specials.contains(Specialty.FLOATER)){
				if (!eng.isObstacle(x , y + 1)){
					if(this.getFallTime() % 2 == 0) 
						this.y ++;	

					this.incrementFallTime();
				}else{
					this.setType(Type.WALKER);
				}
				
			}
			
			if(specials.contains(Specialty.BOMBER)){
				this.activateBomber();
			}
			
			
			else{ 
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
//						this.setType(Type.MINER);//TEMPORARY
					}
				}
			}	
			this.incrementNbStep();
			break;

		case WALKER:
			if(specials.contains(Specialty.BOMBER)){
				this.activateBomber();
			}
			if(this.dir == Direction.RIGHT && specials.contains(Specialty.CLIMBER) 
			&& (eng.isObstacle(x+1, y) && eng.isObstacle(x+1, y+1))
			&& (eng.getLevel().getNature(x, y - 1) == Nature.EMPTY && eng.getLevel().getNature(x, y - 2) == Nature.EMPTY)){
				this.y --;
				if(eng.isObstacle(x+1, y+1) && eng.getLevel().getNature(x+1, y)==Nature.EMPTY) x++;
				this.incrementNbStep();
				break;
			}else if(this.dir == Direction.LEFT && specials.contains(Specialty.CLIMBER) 
					&& (eng.isObstacle(x-1, y) && eng.isObstacle(x-1, y+1))
					&& (eng.getLevel().getNature(x, y - 1) == Nature.EMPTY && eng.getLevel().getNature(x, y - 2) == Nature.EMPTY)){
				this.y --;
				if(eng.isObstacle(x-1, y+1) && eng.getLevel().getNature(x-1, y)==Nature.EMPTY) x--;
				this.incrementNbStep();
				break;

			}else{
				if (eng.getLevel().getNature(x, y + 1) == Nature.EMPTY){ // turn into faller
					this.setType(Type.FALLER);
				}else if (this.dir == Direction.RIGHT){
					if(eng.isObstacle(x+1, y-1) ){
						this.dir = Direction.LEFT;
					}else if ((eng.isObstacle(x+1, y) && eng.isObstacle(x+1, y-2))){
						this.dir = Direction.LEFT;
					}else if (eng.isObstacle(x+1, y) && !eng.isObstacle(x+1, y-1) && !eng.isObstacle(x+1, y-2)){
						this.y = this.y - 1;
						this.x = this.x + 1;
					}else if (!eng.isObstacle(x+1, y) && !eng.isObstacle(x+1, y-1)){

						this.x = this.x + 1;	
					}
				}else{ // LEFT DIRECTION CASE
					if(eng.isObstacle(x-1, y-1)){
						this.dir = Direction.RIGHT;
					}else if((eng.isObstacle(x-1, y) && eng.isObstacle(x-1, y-2))){
						this.dir = Direction.RIGHT;
					}else if (eng.isObstacle(x-1, y) && !eng.isObstacle(x-1, y-1) && !eng.isObstacle(x-1, y-2)){
						this.x = this.x - 1;
						this.y = this.y - 1;
					}else if (!eng.isObstacle(x-1, y) && !eng.isObstacle(x-1, y-1)){
						this.x = x - 1;
					}
				}
			}
			this.incrementNbStep();
			break;

		case DIGGER:
			if(specials.contains(Specialty.BOMBER)){
				this.activateBomber();
			}
			if(eng.getLevel().getNature(x, y + 1) == Nature.EMPTY){
				this.setType(Type.FALLER);
			}else if(eng.getLevel().getNature(x, y + 1) == Nature.METAL){
				this.setType(Type.WALKER);
			}else if(eng.getLevel().getNature(x, y + 1) == Nature.DIRT){
				this.getGameEng().getLevel().remove(x, y + 1);//down
				this.y = this.y + 1;
				if(eng.getLevel().getNature(x - 1, y + 1) == Nature.DIRT){//down left
					this.getGameEng().getLevel().remove(x - 1, y + 1);
				} 
				if(eng.getLevel().getNature(x + 1, y + 1) == Nature.DIRT){//down right
					this.getGameEng().getLevel().remove(x + 1, y + 1);
				} 
			}
			this.incrementNbStep();
			break;

		case STOPPER:
			//do nothing
			if(specials.contains(Specialty.BOMBER)){
				this.activateBomber();
			}
			this.incrementNbStep();
			break;

		case BASHER:
			if(specials.contains(Specialty.BOMBER)){
				this.activateBomber();
			}
			if(!eng.isObstacle(x, y+1)){
				this.setType(Type.FALLER);
			}else if(this.dir == Direction.RIGHT){
				if(eng.getLevel().getNature(x+1, y)==Nature.METAL
						|| eng.getLevel().getNature(x+1, y-1)==Nature.METAL
						|| eng.getLevel().getNature(x+1, y-2)==Nature.METAL){
					this.setType(Type.WALKER);
				}else{
					if(nbBash > 20){
						this.setType(Type.WALKER);
					}else{
						this.getGameEng().getLevel().remove(x + 1, y );
						this.getGameEng().getLevel().remove(x + 1, y - 1);
						this.getGameEng().getLevel().remove(x + 1, y - 2);
						this.nbBash++;
						this.x++;
					}
				}
			}else if(this.dir == Direction.LEFT){
				if(eng.getLevel().getNature(x-1, y)==Nature.METAL
						|| eng.getLevel().getNature(x-1, y-1)==Nature.METAL
						|| eng.getLevel().getNature(x-1, y-2)==Nature.METAL){
					this.setType(Type.WALKER);
				}else{
					if(nbBash > 20){
						this.setType(Type.WALKER);
					}else{
						this.getGameEng().getLevel().remove(x - 1, y );
						this.getGameEng().getLevel().remove(x - 1, y - 1);
						this.getGameEng().getLevel().remove(x - 1, y - 2);
						this.nbBash++;
						this.x--;
					}
				}
			}

			this.incrementNbStep();
			break;
		case BUILDER:
			if(specials.contains(Specialty.BOMBER)){
				this.activateBomber();
			}
			if(!eng.isObstacle(x, y+1)){
				this.setType(Type.FALLER);
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
								this.setType(Type.WALKER);
							}else
								this.setType(Type.WALKER);

						}else{
							if(this.nbBuild < 12){
								eng.getLevel().setNature(x+1, y, Nature.DIRT);
								eng.getLevel().setNature(x+2, y, Nature.DIRT);
								eng.getLevel().setNature(x+3, y, Nature.DIRT);
								this.nbBuild += 3;
								this.x += 2;
								this.y--;
							}else 
								this.setType(Type.WALKER);
						}
					}else{
						this.setType(Type.WALKER);
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
								this.setType(Type.WALKER);
							}else
								this.setType(Type.WALKER);

						}else{
							if(this.nbBuild < 12){
								eng.getLevel().setNature(x-1, y, Nature.DIRT);
								eng.getLevel().setNature(x-2, y, Nature.DIRT);
								eng.getLevel().setNature(x-3, y, Nature.DIRT);
								this.nbBuild += 3;
								this.x -= 2;
								this.y--;
							}else 
								this.setType(Type.WALKER);
						}
					}else{
						this.setType(Type.WALKER);
					}
				}
			}

			this.incrementNbStep();
			break;
			
		case MINER:
			if(specials.contains(Specialty.BOMBER)){
				this.activateBomber();
			}
			if(!eng.isObstacle(x, y+1)){
				this.setType(Type.FALLER);
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
				}
			}

			this.incrementNbStep();
			break;
		default:
			break;

		}
	}
	
	private void activateBomber(){
		if(this.getNbStep() == 5){
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
	}

	@Override
	public Set<Specialty> getSpecials() {
		return this.specials;
	}

	@Override
	public int getNbStep() {
		return nbSteps;
	}
	private void incrementNbStep(){
		this.nbSteps ++;
	}
}