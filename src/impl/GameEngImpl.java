package impl;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import enumeration.Nature;
import enumeration.Status;
import enumeration.Type;
import services.GameEngService;
import services.LemmingService;
import services.LevelService;
import services.RequireLevelService;

public class GameEngImpl implements RequireLevelService, GameEngService {

	protected LevelService level;
	protected ArrayList<LemmingService> lemmings;
	protected int sizeColony;
	protected int spawnSpeed;
	protected int nbSpawned;
	protected int nbSaved;
	protected int nbTours;
	protected boolean gameOver;

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
	public boolean isObstacle(int x, int y) {
		boolean lemmyStopper = false;
		for(LemmingService lemmy : this.lemmings){
			lemmyStopper = (lemmy.getType() == Type.STOPPER && ((lemmy.getX()== x && lemmy.getY() == y)||(lemmy.getX() == x && lemmy.getY() == y+1)));
			break;
		}
		return (this.level.getNature(x, y) == Nature.DIRT || this.level.getNature(x, y) == Nature.METAL || lemmyStopper);
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
	public int getNbTurn() {
		return this.nbTours;
	}

	@Override
	public Double[] score() {
		Double score[] = new Double[2];
		if (nbSpawned != 0) score[0] = (double) ((nbSaved/nbSpawned)*100);
		else score[0] = 0.;

		score[1] = (double) nbTours;
		return score;
	}

	@Override
	public int nbSpawned() {
		return nbSpawned;
	}

	@Override
	public int nbSaved() {
		return nbSaved;
	}

	@Override
	public int nbActive() {
		int cpt = 0;
		for (LemmingService lemmy: this.lemmings){
			if (lemmy.getStatus() == Status.LIVING) cpt ++;
		}
		return cpt;
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
		for (LemmingService lem: this.lemmings){
			if (lem.getNumber() == i) return true; 
		}
		return false;
	}

	@Override
	public LemmingService getLemming(int i) {
		for (LemmingService lemmy : lemmings){
			if (lemmy.getNumber() == i) return lemmy;
		}
		return null;
	}

	@Override
	public boolean isActive(int i) {
		
		return this.lemmings.get(i).getStatus() == Status.LIVING;
	}
	
	@Override
	public void init(int size, int speed) {
		this.sizeColony = size;
		this.spawnSpeed = speed;
		this.gameOver = false;
		this.nbTours = 0;
		this.lemmings = new ArrayList<LemmingService>();
		//lemmings.add(new LemmingImpl().lemmyStopper()); //pour tester le stopper
	}
	

	@Override
	public void nextTurn() {

		Set<Integer> nums = getLemmingsNum();
		for (Integer i : nums){
			LemmingService lemmy = 	getLemming(i);
			lemmy.step(); // INDEX IN ARRAYLIST NOT THE SAME AS LEMMING ID
			if (lemmy.getX() == getLevel().getExitX() && lemmy.getY() == getLevel().getExitY()){
				lemmings.remove(lemmy);
				nbSaved += 1;
			}
		}

		if (getNbTurn() % spawnSpeed == 0 && nbSpawned() < sizeColony){
			LemmingService lemmy = new LemmingImpl();
			lemmy.bindEngine(this);
			lemmy.init(nbSpawned() + 1);
			lemmings.add(lemmy);
			this.nbSpawned += 1;
		} 
		nbTours += 1;
		
		// remove dead lemmings
		for (Integer i : nums){
			LemmingService lemmy = 	getLemming(i);
			if (lemmy != null && lemmy.getStatus() == Status.DEAD){
				this.lemmings.remove(lemmy);
			}
		}
		
		// test for game over
		if (nbSpawned() == sizeColony && nbActive() == 0){
			this.gameOver = true;
			System.out.println("GAME OVER");
		}
	}

	@Override
	public boolean isThereLemming(int x, int y) {
		for (int num : getLemmingsNum()){
			LemmingService lemmy = getLemming(num);
			if (lemmy.getX() == x && lemmy.getY() == y) return true;
		}
		return false;
	}


	@Override
	public LemmingService getLemmingAtPosition(int x, int y) {
		for (int num : getLemmingsNum()){
			LemmingService lemmy = getLemming(num);
			if (lemmy.getX() == x && lemmy.getY() == y) return lemmy;
		}
		return null;
	}


	@Override
	public boolean isDirt(int x, int y) {
		return getLevel().getNature(x, y) == Nature.DIRT;
	}


	@Override
	public boolean isMetal(int x, int y) {
		return getLevel().getNature(x, y) == Nature.METAL;
	}
}