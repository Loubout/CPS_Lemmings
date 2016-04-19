package services;

import enumeration.Direction;
import enumeration.Status;
import enumeration.Type;

public interface LemmingService {
	/*Observations*/
	public Direction getDirection();
	public int getNumber();
	public Type getType();
	public Status getStatus();
	public int getX();
	public int getY();
	//PRE	getType = Type.FALLER
	public int getFallTime();
	public GameEngService getGameEng();

	/*	INVARIANTS
	 * 	getGameEng().getLevel().getNature(getX(),getY()-1) = Nature.EMPTY
	 */

	/*Constructors*/
	//POST	getType() = type
	//		getNumber() = num
	//		getDirection = Direction.RIGHT
	public void init(int num);

	/*Operators*/
	//PRE	getStatus() = Status.LIVING
	//POST	
	//		if getType() == Type.WALKER then : 
	//			if getGameEng().isObstacle(getX(),getY() + 1)@pre == FALSE then :
	//				getType() == Type.FALLER && getFallTime() == 0
	//			else if getDirection() == Direction.RIGHT && getGameEng().isObstacle (getX() + 1,getY() - 1) then :
	//				getDirection(step()) == Direction.LEFT
	//			else if getDirection() == Direction.LEFT ^ getGameEng().isObstacle (getX() - 1,getY() - 1) then :
	//	   			getDirection(step()) == Direction.RIGHT
	//			else if getDirection() == Direction.RIGHT ^ getGameEng().isObstacle (getX() + 1,getY()) ^ getGameEng().isObstacle (getX() + 1,getY() - 2) then :
	//	    		getDirection(step()) == Direction.LEFT
	//			else if getDirection() == Direction.LEFT ^ getGameEng().isObstacle (getX() - 1,getY()) ^ getGameEng().isObstacle (getX() - 1,getY() - 2) then :
	//   			getDirection(step()) == Direction.RIGHT
	//			else if getDirection() == Direction.RIGHT ^ getGameEng().isObstacle (getX() + 1,getY()) ^ getGameEng().isObstacle (getX() + 1,getY() - 1) == FALSE ^ getGameEng().isObstacle (getX() + 1,getY() - 2) == FALSE  then :
	//  			getY(step()) == getY() - 1 ^  getX(step()) == getX() +1
	//			else if getDirection() == Direction.LEFT ^ getGameEng().isObstacle (getX() - 1,getY()) ^ getGameEng().isObstacle (getX() - 1,getY() - 1) == FALSE ^ getGameEng().isObstacle (getX() - 1,getY() - 2) == FALSE   then :
	//  	 		getY(step()) == getY() - 1 ^  getX(step()) == getX() +1
	//			else if getDirection() == Direction.RIGHT ^ getGameEng().isObstacle (getX() + 1,getY()) == FALSE ^ getGameEng().isObstacle (getX() + 1,getY() - 1) == FALSE then
	// 		 		getDirection(step())  == Direction.RIGHT ^ getX(step()) == getX() + 1
	//			else if getDirection() == Direction.LEFT ^ getGameEng().isObstacle (getX() - 1,getY()) == FALSE ^ getGameEng().isObstacle (getX() - 1,getY() - 1) == FALSE then
	//        		getDirection(step())  == Direction.LEFT ^ getX(step()) == getX() - 1
	// 			else if getType() == Type.FALLER then :
	//    			if GameEng : isObstacle(getX(), getY() + 1) ^ getFallTime() < 8 then:
	//        			getType(step()) == WALKER  
	//    			else if GameEng : isObstacle(getX(), getY() + 1) ^ getFallTime() >= 8 then:
	//        			getStatus(step()) == Status.DEAD
	//    			else 
	//        			getY(step()) == getY() + 1 ^ getFallTime(step()) == getFallTime() + 1
	//	 	else 
	//        	if getType(l) == DIGGER then :
	//            	if Level : getNature(getX(l), getY(l) + 1) == EMPTY then :
	//                	getType(step(l)) == FALLER 
	//            	if Level : getNature(getX(l), getY(l) + 1) == METAL then :
	//                	getType(step(l)) == WALKER
	//            	if Level : getNature(getX(l), getY(l) + 1) == DIRT then :
	//                	Level : remove(getX(l), getY(l) + 1) 
	//                	if getNature(getX(l) - 1, getY(l) + 1) == DIRT then :
	//                    	Level : remove(getX(l) - 1, getY(l) + 1)
	//                	if getNature(getX(l) + 1, getY(l) + 1) == DIRT then :
	//                    	Level : remove(getX(l) + 1, getY(l) + 1)
	public void step();
	public void bindEngine(GameEngService eng);


}
