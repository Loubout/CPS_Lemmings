package decorators;

import java.util.Set;

import enumeration.Status;
import services.GameEngService;
import services.LemmingService;
import services.LevelService;
import services.RequireLevelService;

public class GameEngDecorator implements GameEngService, RequireLevelService{
	
	
	public GameEngDecorator(GameEngService delegate) {
		this.delegate = delegate;
	}

	private GameEngService delegate;

	
	public int getSizeColony() {
		return delegate.getSizeColony();
	}

	public int getSpawnSpeed() {
		return delegate.getSpawnSpeed();
	}


	public boolean isObstacle(int x, int y) {
		return delegate.isObstacle(x, y);
	}

	public boolean gameOver() {
		return delegate.gameOver();
	}

	public LevelService getLevel() {
		return delegate.getLevel();
	}

	public int getNbTours() {
		return delegate.getNbTours();
	}

	public Double[] score() {
		return delegate.score();
	}

	public int nbSpawned() {
		return delegate.nbSpawned();
	}

	public int nbSaved() {
		return delegate.nbSaved();
	}

	public int nbActive() {
		return delegate.nbActive();
	}

	public Set<Integer> getLemmingsNum() {
		return delegate.getLemmingsNum();
	}

	public boolean lemmingExist(int i) {
		return delegate.lemmingExist(i);
	}

	public LemmingService getLemming(int i) {
		return delegate.getLemming(i);
	}

	public boolean isActive(int i) {
		return delegate.isActive(i);
	}

	public Status getStatus(int i) {
		return delegate.getStatus(i);
	}

	public void init(int size, int speed) {
		delegate.init(size, speed);
	}

	public void bindLevel(LevelService level) {
		((RequireLevelService) delegate).bindLevel(level);
	}

	public void nextTurn() {
		delegate.nextTurn();
	}
}
