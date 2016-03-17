package services;

import java.util.Set;

import enumeration.Status;

public interface GameEngService {
	
	/*Observators*/
	public int getSizeColony();
	public int getSpawnSpeed();
	public int getTimeSinceLastSpawn();
	//PRE	0 < x < getLevel().getWidth()
	// 		0 < y < getLevel().getHeight()
	public boolean isObstacle(int x, int y);
	public boolean gameOver();
	public LevelService getLevel();
	public int nbTours();
	//PRE	gameover()
	public Double[] score();
	public int nbSpawned();
	public int nbSaved();
	public int nbActive();
	public Set<Integer> getLemmingsNum();
	public boolean lemmingExist(int i);
	//PRE	lemmingExist(i)
	public LemmingService getLemminng(int i);
	//PRE 1 < i < nbActive()
	public boolean isAvtive(int i);
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
	public void bindLevel(LevelService level);
	//PRE	!gameOver()
	//POST	if ( (getSpawnedSpeed() - timeSinceLastSpawn()) > 0) 
	//			timeSinceLastSpawn() = timeSinceLastSpawn() + 1
	//		else 
	//			timeSinceLastSpawn(nextTurn(e)) = 0 
    //			nbSpawned((nextTurn(e)) = nbSpawned(e) + 1
    //			getLemming(nbSpawned() + 1) = Lemming : init(FALLER, nbSpawned() +1)	
	public void nextTurn();
}
