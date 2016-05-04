package contract;


import java.util.HashMap;
import java.util.Set;

import decorator.GameEngDecorator;
import enumeration.Type;
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

		int nbSpawned_pre = super.nbSpawned();
		int nbTurn_pre = super.getNbTurn();

		HashMap<Integer, Type> typeMap_pre = new HashMap<Integer, Type>();


		for (int i : getLemmingsNum()){
			LemmingService lemmy = getLemming(i);
			typeMap_pre.put(i, lemmy.getType());
		}

		checkInvariant();

		super.nextTurn();

		checkInvariant();


		//POST	

		//			\forAll i in getLemmingsNum(nextTurn(l)), getLemming(nextTurn(e), i) = Lemming : step(getLemming(e, i))
		//POST A REECRIRE 
		for (int i : getLemmingsNum()){
			LemmingService lemmy = getLemming(i);
			if(typeMap_pre.get(i) == Type.DIGGER){
				if (isObstacle(lemmy.getX(), lemmy.getY()) 
						|| !(!isDirt(lemmy.getX()-1, lemmy.getY()) || !isObstacle(lemmy.getX()-1, lemmy.getY()))
						|| !(!isDirt(lemmy.getX()+1, lemmy.getY()) || !isObstacle(lemmy.getX()+1, lemmy.getY()))){
					throw new PostconditionError("Cells should have been digged");
				}
			}
			if(typeMap_pre.get(i) == Type.BUILDER){
				System.out.println("BUILDER CONTRACT");
				if(nbTurn_pre % 3 == 0 && lemmy.getNbBuild() <= 12 && !(isDirt(lemmy.getX() - 1 , lemmy.getY() + 1) 
						&& isDirt(lemmy.getX() , lemmy.getY() + 1) 
						&& isDirt(lemmy.getX() + 1 , lemmy.getY() + 1))){
					throw new PostconditionError("three cells row under the lemming should have been built into dirt");
				}
			}

			if ((typeMap_pre.get(i) == Type.BASHER)){
				System.out.println("BASHER CONTRACT");
				if(isObstacle(lemmy.getX(), lemmy.getY() - 1) 
						|| isObstacle(lemmy.getX(), lemmy.getY() - 2) 
						|| isObstacle(lemmy.getX(), lemmy.getY() - 3)){
					throw new PostconditionError("three cells column at lemming position should have bashed");
				}
			}
		}

		if (nbTurn_pre % super.getSpawnSpeed() == 0 && nbSpawned_pre < super.getSizeColony() 
				&& !(super.nbSpawned() == nbSpawned_pre +1 && lemmingExist(super.nbSpawned()))){	
			throw new PostconditionError("A new lemming should have spawned at turn : " + super.getNbTurn());
		}

		if ( (nbTurn_pre % super.getSpawnSpeed() != 0 || nbSpawned_pre >= super.getSizeColony())
				&& !(super.nbSpawned() == nbSpawned_pre)){
			throw new PostconditionError("Incorrect change in nbSpawned at turn : " + super.getNbTurn());
		}
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
