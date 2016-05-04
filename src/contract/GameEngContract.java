package contract;

import java.util.Set;

import decorator.GameEngDecorator;
import enumeration.Status;
import services.GameEngService;
import services.LemmingService;
import services.LevelService;
import services.RequireLevelService;

public class GameEngContract extends GameEngDecorator implements RequireLevelService {

	
	public GameEngContract(GameEngService delegate) {
		super(delegate);
	}
	
	
	public void checkInvariant(){
		/*	INVARIANTS
		*	nbSpawned() <= getSizeColony()
		*	nbTours() >= 0
		*	nbActifs() =min= getLemmingsNum().size()
	    *   lemmingExist(n) =min= n \belongsto getLemmingsNum()
	    *   gameOver() =min= (nbSpawned() == getSizeColony()) && (nbActive() == 0)
		*/
		
		if (super.nbSpawned() > super.getSizeColony()) throw new InvariantError("Invariant Error : nbSpawned > getSizeColony()");
		
		if (super.getNbTurn() < 0) throw new InvariantError("Invariant Error : nbTours < 0");
		System.out.println("GameEng contract nb active : "+ super.nbActive());
		System.out.println("GameEng contract nums size " + super.getLemmingsNum().size());
		if (super.nbActive() != getLemmingsNum().size()) throw new InvariantError("Invariant Error : nbActive should be equal to |getLummingsNum()|");
		
		
		// sup bro lemming exist
//		Random rd = new Random();
//		for(int i = 0; i < 5; i++){
//			int randLemming = rd.nextInt(super.nbActive());
//			if (isActive(randLemming) != getLemmingsNum().
//		}
		
		if (super.gameOver() != (super.nbSpawned() == super.getSizeColony() && nbActive() == 0)) throw new InvariantError("Invariant Error : Game should be over"); 	

	}
	

	@Override
	public void init(int size, int speed) {
		if(size<0) throw new PreconditionError("Size should be greater than 0");
		if(speed<0) throw new PreconditionError("Speed should be greater than 0");

		super.init(size, speed);

		if(size != super.getSizeColony()) 
			throw new PostconditionError("the getter of size colony should be the same as the size in parameter");
		if(speed != super.getSpawnSpeed()) 
			throw new PostconditionError("the getter of spawn speed should be the same as the speed in parameter");
		if(super.score()[0] != 0 || super.score()[1] != 0) 
			throw new PostconditionError("The score should be (0,0) after initialization");
		if(super.gameOver()) 
			throw new PostconditionError("Game shouldn't be over after initialization");
		if(super.getNbTurn() != 0) 
			throw new PostconditionError("the number of turn should be 0 after initialization");
		if(super.nbSpawned() != 0) 
			throw new PostconditionError("the number of spawned lemmings should be 0 after initialization");
		if(super.nbSaved() != 0) 
			throw new PostconditionError("the number of saved lemmings should be 0 after initialization");
		if(super.nbSpawned() != 0) 
			throw new PostconditionError("the number of active lemmings should be 0 after initialization");
		if(!super.getLemmingsNum().isEmpty())
			throw new PostconditionError("Lemmings list should be empty after initialization");

	}
	



	@Override
	public void nextTurn() {
		if(super.gameOver()) throw new PreconditionError("Game should not be over ");
		
		checkInvariant();
		super.nextTurn();
		checkInvariant();
//		if (super.getNbTours() % super.getSpawnSpeed())
		
		//POST	if ( (getSpawnedSpeed(e) - timeSinceLastSpawn()) > 0) 
		//			timeSinceLastSpawn(e) = timeSinceLastSpawn() + 1
		//		else 
		//			timeSinceLastSpawn(e, nextTurn(e)) = 0 
	    //			nbSpawned(e, (nextTurn(e)) = nbSpawned(e) + 1
	    //			getLemming(e, nbSpawned() + 1) = Lemming : init(FALLER, nbSpawned() +1)
		//
		//		\forAll i in getLemmingsNum(nextTurn(l)), getLemming(nextTurn(e), i) = Lemming : step(getLemming(e, i))
		//POST A REECRIRE 
		
		
		
	}
	
	@Override
	public int getSizeColony() {
		// TODO Auto-generated method stub
		return super.getSizeColony();
	}

	@Override
	public int getSpawnSpeed() {
		// TODO Auto-generated method stub
		return super.getSpawnSpeed();
	}

	
	@Override
	public boolean isObstacle(int x, int y) {
		//PRE	0 < x < getLevel().getWidth()
		// 		0 < y < getLevel().getHeight()
		if(x<0 || x>super.getLevel().getWidth() || y<0 || y>super.getLevel().getHeight()) throw new PreconditionError("isObstacle: Index out of bounds");
		return super.isObstacle(x, y);
	}

	@Override
	public boolean gameOver() {
		return super.gameOver();
	}

	@Override
	public LevelService getLevel() {
		// TODO Auto-generated method stub
		return super.getLevel();
	}

	@Override
	public int getNbTurn() {
		// TODO Auto-generated method stub
		return super.getNbTurn();
	}

	@Override
	public Double[] score() {
		if(!super.gameOver()) throw new PreconditionError("You cannot access the score if the game isn't over");
		return super.score();
	}

	@Override
	public int nbSpawned() {
		// TODO Auto-generated method stub
		return super.nbSpawned();
	}

	@Override
	public int nbSaved() {
		// TODO Auto-generated method stub
		return super.nbSaved();
	}

	@Override
	public int nbActive() {
		// TODO Auto-generated method stub
		return super.nbActive();
	}

	@Override
	public Set<Integer> getLemmingsNum() {
		// TODO Auto-generated method stub
		return super.getLemmingsNum();
	}

	@Override
	public boolean lemmingExist(int i) {
		// TODO Auto-generated method stub
		return super.lemmingExist(i);
	}

	@Override
	public LemmingService getLemming(int i) {
		// TODO Auto-generated method stub
		return super.getLemming(i);
	}

	@Override
	public boolean isActive(int i) {
		return super.isActive(i);
	}

}
