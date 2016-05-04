package services;

import java.util.Set;

import enumeration.Direction;
import enumeration.Specialty;
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
	public Set<Specialty> getSpecials();
	public boolean hasSpecial(Specialty sp);
	public int getBombCounter();
	public int getNbBash();
	public int getNbBuild();
	

	/*	INVARIANTS
	 * 	getGameEng().getLevel().getNature(getX(),getY()-1) = Nature.EMPTY
	 */

	/*Constructors*/
	//POST	getType() = type
	//		getNumber() = num
	//		getDirection = Direction.RIGHT
	// 		getStatus = Status.LIVING
	public void init(int num);

	/*Operators*/

	// PRE getStatus == Status.LIVING
	// 	   !getSpecials().contains(sp)
	// + compatibilité entre les special
	public void transform(Specialty sp);


	//PRE	getStatus() = Status.LIVING
	//POST	
	//	
	//		if BOMBER \belongs getSpecials(l) ^ getBombCounter(l) == 5  then :
	//        getStatus(step(l)) == DEAD
	//    	if BOMBER \belongs getSpecials(l) ^ getBombCounter(l) != 5 then : 
	//        getBombCounter(step(l)) == getBombCounter(l) + 1
	//    	if STOPPER \belongs getSpecials(l) ^ getType(l) != STOPPER then:
	//        getType(l) = STOPPER
	//    	if BUILDER \belongs getSpecials(l) ^ getType(l) != BUILDER then:
	//        getType(l) = BUILDER
	//    	if DIGGER \belongs getSpecials(l) ^ getType(l) != DIGGER then:
	//        getType(l) = DIGGER
	//    	if CLIMBER \belongs getSpecials(l) ^ getType(l) != CLIMBER then:
	//        getType(l) = CLIMBER
	//    	if FLOATER \belongs getSpecials(l) ^ getType(l) != FLOATER then:
	//        getType(l) = FLOATER
	//    	if BASHER \belongs getSpecials(l) ^ getType(l) != BASHER then:
	//        getType(l) = BASHER
	//   	if MINER \belongs getSpecials(l) ^ getType(l) != MINER then:
	//        getType(l) = MINER
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
	// 			
	//		else if getType() == Type.FALLER then :
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
	//	 	 else 
	//         if getType(l) == CLIMBER then :
	//             if getDirection(l) == RIGHT:
	//                 if GameEng : isObstacle(getX(l)+1, getY(l)) ^ 
	//                    GameEng : isObstacle(getX(l)+1, getY(l)-1) ^
	//                    ! GameEng: isObstacle(getX(l), getY(l)-1) ^ 
	//                    ! GameEng: isObstacle(getX(l), getY(l)-2) then :
	//                     getY(step(l)) == getY(l) - 1
	//             else if getDirection(l) == LEFT:
	//                 if GameEng : isObstacle(getX(l)-1, getY(l)) ^ 
	//                    GameEng : isObstacle(getX(l)-1, getY(l)-1) ^
	//                    ! GameEng: isObstacle(getX(l), getY(l)-1) ^ 
	//                    ! GameEng: isObstacle(getX(l), getY(l)-2) then :
	//                     getY(step(l)) == getY(l) - 1
	//             else 
	//                 getType(step(l)) == WALKER
	//		else
	//     	  if getType(l) == STOPPER then :
	//     		getY(step(l)) == getY(l) ^
	//     		getX(step(l)) == getX(l)
	//     	else 
	//     	  if getType(l) == BASHER:
	//     		if not GameEng : isObstacle(getX(l), getY(l) + 1) :
	//     			getType(step(l)) == FALLER
	//     		else if getDirection(l) == RIGHT:
	//     			if not (GameEng : getNature(getX(l) + 1, getY(l) == METAL)
	//     			   	^ GameEng : getNature(getX(l) + 1, getY(l) - 1 == METAL)
	//     			   	^ GameEng : getNature(getX(l) + 1, getY(l) - 2 == METAL)) :
	//     			   	getX(step(l)) == getX(l) + 1
	//     			   	^ GameEng : getNature(getX(step(l)), getY(step(l)) == EMPTY)
	//     			   	^ GameEng : getNature(getX(step(l), getY(step(l) - 1) == EMPTY)
	//     			   	^ GameEng : getNature(getX(step(l)), getY(step(l) - 2 ) == EMPTY))
	//     			   	^ getNbBash(l) = getNbBash(l) + 1 
	//     			else :
	//     				getType(step(l)) == WALKER
	//     		else 
	//     			if not (GameEng : getNature(getX(l) - 1, getY(l) == METAL)
	//     			   	^ GameEng : getNature(getX(l) - 1, getY(l) - 1 == METAL)
	//     			   	^ GameEng : getNature(getX(l) - 1, getY(l) - 2 == METAL)) :
	//     			   	getX(step(l)) == getX(l) - 1
	//     			   	^ GameEng : getNature(getX(step(l)), getY(step(l)) == EMPTY)
	//     			   	^ GameEng : getNature(getX(step(l), getY(step(l) - 1) == EMPTY)
	//     			   	^ GameEng : getNature(getX(step(l)), getY(step(l) - 2 ) == EMPTY))
	//     			   	^ getNbBash(l) = getNbBash(l) + 1  
	//     			else :
	//     				getType(step(l)) == WALKER
	//     	else
	//     	  if getType(l) == BUILDER:
	//     		if not GameEng : isObstacle(getX(l), getY(l) + 1) :
	//                getType(step(l)) == FALLER
	//            else if getDirection(l) == RIGHT:
	//                if GameEng : getNbTurn % 3 == 0 :
	//                    if not (GameEng : isObstacle(getX(l)+1, getY(l))
	//                            ^ GameEng : isObstacle(getX(l)+2, getY(l))
	//                            ^ GameEng : isObstacle(getX(l)+3, getY(l)):
	//                            if getNbBuid < 12:
	//                                GameEng : getNature(getX(l)+1, getY(l)) == DIRT
	//                                ^ GameEng : getNature(getX(l)+2, getY(l)) == DIRT
	//                                ^ GameEng : getNature(getX(l)+3, getY(l)) == DIRT
	//                                ^ getX(l) =  getX(l) + 2
	//                                ^ getY(l) =  getY(l) - 1
	//                    else 
	//                        getType(l) = WALKER
	//            else if getDirection(l) == LEFT:
	//                     if GameEng : getNbTurn % 3 == 0 :
	//                        if not (GameEng : isObstacle(getX(l)-1, getY(l))
	//                                ^ GameEng : isObstacle(getX(l)-2, getY(l))
	//                                ^ GameEng : isObstacle(getX(l)-3, getY(l)):
	//                                if getNbBuid < 12:
	//                                    GameEng : getNature(getX(l)-1, getY(l)) == DIRT
	//                                    ^ GameEng : getNature(getX(l)-2, getY(l)) == DIRT
	//                                    ^ GameEng : getNature(getX(l)-3, getY(l)) == DIRT
	//                                    ^ getX(l) =  getX(l) - 2
	//                                    ^ getY(l) =  getY(l) - 1
	//                    else 
	//                        getType(l) = WALKER
	//    	else 
	//        if getType(l) == MINER:
	//            if not GameEng : isObstacle(getX(l), getY(l) + 1) :		
	//     	      getType(step(l)) == FALLER
	//     	    else if getDirection(l) == RIGHT:
	//     	      if (GameEng : getNature(getX(l)+1, getY(l)-1) == DIRT
	//     	          ^ GameEng : getNature(getX(l)+1, getY(l)-2) == DIRT
	//                  ^ GameEng : getNature(getX(l)+1, getY(l)-3) == DIRT):
	//                  GameEng : getNature(getX(l)+1, getY(l)-1) == EMPTY
	//                  ^ GameEng : getNature(getX(l)+1, getY(l)-2) == EMPTY
	//                  ^ GameEng : getNature(getX(l)+1, getY(l)-3) == EMPTY
	//                  ^ getX(l) = getX(l) + 1
	//                  ^ getY(l) = getY(l) - 1
	//              else 
	//                getType(l) == WALKER
	//           else if getDirection(l) == LEFT:
	//              if (GameEng : getNature(getX(l)-1, getY(l)-1) == DIRT
	//                  ^ GameEng : getNature(getX(l)-1, getY(l)-2) == DIRT
	//                  ^ GameEng : getNature(getX(l)-1, getY(l)-3) == DIRT):
	//                  GameEng : getNature(getX(l)-1, getY(l)-1) == EMPTY
	//                  ^ GameEng : getNature(getX(l)-1, getY(l)-2) == EMPTY
	//                  ^ GameEng : getNature(getX(l)-1, getY(l)-3) == EMPTY
	//                  ^ getX(l) = getX(l) - 1
	//                  ^ getY(l) = getY(l) + 1
	//              else 
	//                getType(l) == WALKER
	public void step();
	public void bindEngine(GameEngService eng);


}
