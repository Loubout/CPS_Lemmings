package services;

import java.util.Set;

import enumeration.Status;

public interface GameEngService extends RequireLevelService {
	
	/*Observators*/
	public int getSizeColony();
	public int getSpawnSpeed();
	//PRE	0 < x < getLevel().getWidth()
	// 		0 < y < getLevel().getHeight()
	public boolean isObstacle(int x, int y);
	public boolean gameOver();
	public LevelService getLevel();
	public int getNbTours();
	//PRE	gameover()
	public Double[] score();
	public int nbSpawned();
	public int nbSaved();
	public int nbActive();
	public Set<Integer> getLemmingsNum();
	public boolean lemmingExist(int i);
	//PRE 0 < x && x < getLevel().getWidth() && 0 < y && y < getLevel().getHeight()
	public boolean isThereLemming(int x, int y);
	//PRE isThereLemming(x, y)
	public LemmingService getLemmingAtPosition(int x, int y);
	//PRE	lemmingExist(i)
	public LemmingService getLemming(int i);
	//PRE 1 < i < nbActive()
	public boolean isActive(int i);
	//PRE 1 < i < nbSpawned()
	public Status getStatus(int i);
	
	/*	INVARIANTS
	*	nbSpawned() <= getSizeColony()
	*	nbTours() >= 0
	*	nbActifs() =min= getLemmingsNum().size()
    *   lemmingExist(n) =min= n \belongsto getLemmingsNum()
    *   gameOver() =min= (nbSpawned() == getSizeColony()) && (nbActive() == 0)
	*/
	
	/*Constructors*/	
	//PRE	size  > 0
	//		speed > 0
	//POST	getSizeColony() = size
	//		getSpawnedSpeed() = speed
	//		score() = 0
	//		gameOver = false
	//		nbTours = 0
	//		nbSpawned() = 0
	//		nbSaved() = 0
	//		nbActive() = 0
	//		getAllLemmings() = null
	public void init(int size, int speed);
	
	/*Operators*/	
		
	// A REECRIRE BRAH
	//PRE	!gameOver()
	//POST	if ( (getSpawnedSpeed(e) - timeSinceLastSpawn()) > 0) 
	//			timeSinceLastSpawn(e) = timeSinceLastSpawn() + 1
	//		else 
	//			timeSinceLastSpawn(e, nextTurn(e)) = 0 
    //			nbSpawned(e, (nextTurn(e)) = nbSpawned(e) + 1
    //			getLemming(e, nbSpawned() + 1) = Lemming : init(FALLER, nbSpawned() +1)
	//
	//		\forAll i in getLemmingsNum(nextTurn(l)), getLemming(nextTurn(e), i) = Lemming : step(getLemming(e, i))
	public void nextTurn();
}
