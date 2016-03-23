package contract;

import java.util.Random;
import java.util.Set;

import services.GameEngService;
import services.LemmingService;
import services.LevelService;
import decorators.GameEngDecorator;
import enumeration.Status;

public class GameEngContract extends GameEngDecorator {

	
	public GameEngContract(GameEngService delegate) {
		super(delegate);
	}
	
	
	public boolean checkInvariant(){
		/*	INVARIANTS
		*	nbSpawned() <= getSizeColony()
		*	nbTours() >= 0
		*	nbActifs() =min= getLemmingsNum().size()
	    *   lemmingExist(n) =min= n \belongsto getLemmingsNum()
	    *   gameOver() =min= (nbSpawned() == getSizeColony()) && (nbActive() == 0)
		*/
		
		if (super.nbSpawned() > super.getSizeColony()) throw new InvariantError("Invariant Error : nbSpawned > getSizeColony()");
		
		if (super.getNbTours() < 0) throw new InvariantError("Invariant Error : nbTours < 0");
		
		if (super.nbActive() != getLemmingsNum().size()) throw new InvariantError("Invariant Error : nbActive should be equal to |getLummingsNum()|");
		
		
		// sup bro lemming exist
//		Random rd = new Random();
//		for(int i = 0; i < 5; i++){
//			int randLemming = rd.nextInt(super.nbActive());
//			if (isActive(randLemming) != getLemmingsNum().
//		}
		
		if (super.gameOver() != (super.nbSpawned() == super.getSizeColony() && nbActive() == 0)) throw new InvariantError("Invariant Error : Game should be over"); 
		
		
		return false;
		
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
		// TODO Auto-generated method stub
		return super.isObstacle(x, y);
	}

	@Override
	public boolean gameOver() {
		// TODO Auto-generated method stub
		return super.gameOver();
	}

	@Override
	public LevelService getLevel() {
		// TODO Auto-generated method stub
		return super.getLevel();
	}

	@Override
	public int getNbTours() {
		// TODO Auto-generated method stub
		return super.getNbTours();
	}

	@Override
	public Double[] score() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return super.isActive(i);
	}

	@Override
	public Status getStatus(int i) {
		// TODO Auto-generated method stub
		return super.getStatus(i);
	}

	@Override
	public void init(int size, int speed) {
		// TODO Auto-generated method stub
		super.init(size, speed);
	}

	@Override
	public void bindLevel(LevelService level) {
		// TODO Auto-generated method stub
		super.bindLevel(level);
	}

	@Override
	public void nextTurn() {
		// TODO Auto-generated method stub
		super.nextTurn();
	}

}
