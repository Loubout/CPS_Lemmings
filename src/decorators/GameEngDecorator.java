package decorators;

import java.util.Set;

import enumeration.Status;
import services.GameEngService;
import services.LemmingService;
import services.LevelService;

public class GameEngDecorator implements GameEngService {
	
	
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

	public int getTimeSinceLastSpawn() {
		return delegate.getTimeSinceLastSpawn();
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

	public int nbTours() {
		return delegate.nbTours();
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
		delegate.bindLevel(level);
	}

	public void nextTurn() {
		delegate.nextTurn();
	}
}
