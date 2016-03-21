package impl;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import enumeration.Nature;
import enumeration.Status;
import services.GameEngService;
import services.LemmingService;
import services.LevelService;
import services.RequireLevelService;

public class GameEngImpl implements RequireLevelService, GameEngService {

	private LevelService level;
	private ArrayList<LemmingService> lemmings;
	private int sizeColony;
	private int spawnSpeed;
	private int nbTours;
	private boolean gameOver;
	
	public GameEngImpl(){}
	
	
	@Override
	public void bindLevel(LevelService l) {
		this.level = l;
	}

	@Override
	public int getSizeColony() {
		return sizeColony;
	}

	@Override
	public int getSpawnSpeed() {
		return spawnSpeed;
	}

	@Override
	public int getTimeSinceLastSpawn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isObstacle(int x, int y) {
		return (this.level.getNature(x, y) == Nature.DIRT || this.level.getNature(x, y) == Nature.METAL);
	}

	@Override
	public boolean gameOver() {
		return gameOver;
	}

	@Override
	public LevelService getLevel() {
		return level;
	}

	@Override
	public int nbTours() {
		return this.nbTours;
	}

	@Override
	public Double[] score() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nbSpawned() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int nbSaved() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int nbActive() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Integer> getLemmingsNum() {
		Set<Integer> lemNums = new TreeSet<Integer>();
		for (LemmingService lem: this.lemmings){
			lemNums.add(lem.getNumber());
		}
		return lemNums;
	}

	@Override
	public boolean lemmingExist(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LemmingService getLemming(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActive(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Status getStatus(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(int size, int speed) {
		this.sizeColony = size;
		this.spawnSpeed = speed;
		
	}

	@Override
	public void nextTurn() {
		// TODO Auto-generated method stub
		
	}

}
