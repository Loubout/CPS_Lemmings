package decorator;

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

	public int getNbTurn() {
		return delegate.getNbTurn();
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


	public void init(int size, int speed) {
		delegate.init(size, speed);
	}

	public void bindLevel(LevelService level) {
		delegate.bindLevel(level);
	}

	public void nextTurn() {
		delegate.nextTurn();
	}

	@Override
	public boolean isThereLemming(int x, int y) {
		return delegate.isThereLemming(x, y);
	}

	@Override
	public LemmingService getLemmingAtPosition(int x, int y) {
		return delegate.getLemmingAtPosition(x, y);
	}

	@Override
	public boolean isDirt(int x, int y) {
		return delegate.isDirt(x, y);
	}

	@Override
	public boolean isMetal(int x, int y) {
		return delegate.isMetal(x, y);
	}
}
