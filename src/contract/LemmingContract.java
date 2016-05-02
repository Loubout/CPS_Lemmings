package contract;

import decorator.LemmingDecorator;
import enumeration.Direction;
import enumeration.Nature;
import enumeration.Status;
import enumeration.Type;
import services.GameEngService;
import services.LemmingService;

public class LemmingContract extends LemmingDecorator{

	public LemmingContract(LemmingService delegate) {
		super(delegate);
	}

	public void checkInvariant(){
		/*	INVARIANTS
		 * 	getGameEng().getLevel().getNature(getX(),getY()-1) = Nature.EMPTY
		 */
		if (super.getGameEng().getLevel().getNature(getX(), getY() - 1) != Nature.EMPTY) throw new InvariantError("Invariant error : cell above lemming should be EMPTY");
		
	}
	
	@Override
	public void init(Type type, int num) {
		// no pre cond
		super.init(type, num);
		
		if (super.getType() != type) throw new PostconditionError("type should be t");
		if (super.getType() != type) throw new PostconditionError("lemmings should spawn with direction == right");
		if (super.getNumber() != num) throw new PostconditionError("number at initialization is wrong");
		if(super.getBombCounter() != 0) throw new PostconditionError("number of lemming steps should be 0 after init");
		
	}
	
	@Override
	public void step() {
		this.checkInvariant();
		if (super.getStatus() != Status.LIVING) throw new PreconditionError("calling step on dead or saved lemming");
		
		// snap shot of level
		Type pre_type = super.getType();
		
		int pre_x = super.getX();
		int pre_y = super.getY();
		int pre_fallltime = super.getFallTime();
		Nature pre_below= super.getGameEng().getLevel().getNature(super.getX(), super.getY() + 1);
		Nature pre_right = super.getGameEng().getLevel().getNature(super.getX()+1, super.getY());
		Nature pre_left = super.getGameEng().getLevel().getNature(super.getX()-1, super.getY());
		Nature pre_above = super.getGameEng().getLevel().getNature(super.getX(), super.getY() - 1);
		Nature pre_aboveleft = super.getGameEng().getLevel().getNature(super.getX()-1, super.getY() - 1);
		Nature pre_aboveleft2 = super.getGameEng().getLevel().getNature(super.getX()-1, super.getY() - 2);
		Nature pre_aboveright = super.getGameEng().getLevel().getNature(super.getX()+1, super.getY() - 1);
		Nature pre_aboveright2 = super.getGameEng().getLevel().getNature(super.getX()+1, super.getY() - 2);
		Nature pre_belowleft = super.getGameEng().getLevel().getNature(super.getX()-1, super.getY() + 1);
		Nature pre_belowright = super.getGameEng().getLevel().getNature(super.getX()+1, super.getY() + 1);
		
		
		Direction pre_dir = super.getDirection();
	
		super.step();	
		this.checkInvariant();
		
		// POST CONDITION
		// SHITSTORM UNLEASHED
		switch (pre_type){
			case WALKER:
				if (pre_below == Nature.EMPTY && super.getType() != Type.FALLER) // FALL
					throw new PostconditionError("lemming should be falling");
				if (pre_dir == Direction.RIGHT && pre_aboveright != Nature.EMPTY && super.getDirection() != Direction.LEFT) // SWITCH DIRECTION
					throw new PostconditionError("lemming walking towards right shoud change direction");
				if (pre_dir == Direction.LEFT && pre_aboveleft != Nature.EMPTY && super.getDirection() != Direction.RIGHT) // SWITCH DIRECTION
					throw new PostconditionError("lemming walking towards left shoud change direction");
				if (pre_dir == Direction.RIGHT && pre_right != Nature.EMPTY && pre_aboveright2 != Nature.EMPTY && super.getDirection() != Direction.LEFT) // SWITCH DIRECTION
					throw new PostconditionError("lemming walking towards right shoud change direction");
				if (pre_dir == Direction.LEFT && pre_left != Nature.EMPTY && pre_aboveleft2 != Nature.EMPTY && super.getDirection() != Direction.RIGHT) // SWITCH DIRECTION
					throw new PostconditionError("lemming walking towards left shoud change direction");
				if (pre_dir == Direction.RIGHT && pre_right != Nature.EMPTY && pre_aboveright == Nature.EMPTY && pre_aboveright2 == Nature.EMPTY && (super.getX() != pre_x + 1  || super.getY() != pre_y - 1)) // GOES UP RIGHT
					throw new PostconditionError("lemming walking towards right should go up");
				if (pre_dir == Direction.LEFT && pre_left != Nature.EMPTY && pre_aboveleft == Nature.EMPTY && pre_aboveleft2 == Nature.EMPTY && (super.getX() != pre_x - 1  || super.getY() != pre_y - 1)) // GOES UP LEFT
					throw new PostconditionError("lemming walking towards left should go up");
				if (pre_dir == Direction.RIGHT && pre_right == Nature.EMPTY && pre_aboveright == Nature.EMPTY && (super.getDirection() != Direction.RIGHT || super.getX() != pre_x + 1))
					throw new PostconditionError("error while walking towards right");
				if (pre_dir == Direction.LEFT && pre_left == Nature.EMPTY && pre_aboveleft == Nature.EMPTY && (super.getDirection() != Direction.LEFT || super.getX() != pre_x -1 ))
					throw new PostconditionError("error while walking towards left");
				break;
			case FALLER:
				if (pre_below != Nature.EMPTY && pre_fallltime < 8 && super.getType() != Type.WALKER)
					throw new PostconditionError("faller should become walker after short fall");
				if (pre_below != Nature.EMPTY && pre_fallltime > 7 && super.getStatus() != Status.DEAD)
					throw new PostconditionError("faller should have died");
				if (pre_below == Nature.EMPTY && super.getFallTime() != pre_fallltime + 1)
					throw new PostconditionError("faller falltime should increase");
				break;
			case DIGGER:
				if(pre_below == Nature.EMPTY && super.getType()!=Type.FALLER) 
					throw new PostconditionError("digger should fall");
				if(pre_below == Nature.METAL && super.getType()!=Type.WALKER) 
					throw new PostconditionError("digger should walk");
				if(pre_below == Nature.DIRT){ // distinguer les cas case dirt ou vide en bas à gauche/droite ? 
					if (super.getGameEng().getLevel().getNature(super.getX(), super.getY() + 1) != Nature.EMPTY && super.getY() == pre_y + 1)
						throw new PostconditionError("cell below digger should be empty and digger should go down");
					if (pre_belowleft != Nature.METAL && super.getGameEng().getLevel().getNature(super.getX()-1, super.getY() + 1) != Nature.EMPTY)
						throw new PostconditionError("non metal cell at bottom left of digger should be emtpied while digging");
					if (pre_belowright != Nature.METAL && super.getGameEng().getLevel().getNature(super.getX()+1, super.getY() + 1) != Nature.EMPTY)
						throw new PostconditionError("non metal cell at bottom right of digger should be emtpied while digging");				
				}
		}	
	}

	@Override
	public Direction getDirection() {
		return super.getDirection();
	}

	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return super.getNumber();
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return super.getType();
	}

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return super.getStatus();
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return super.getX();
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return super.getY();
	}

	@Override
	public int getFallTime() {
		// TODO Auto-generated method stub
		return super.getFallTime();
	}

	@Override
	public GameEngService getGameEng() {
		// TODO Auto-generated method stub
		return super.getGameEng();
	}

	
	@Override
	public void bindEngine(GameEngService eng) {
		// TODO Auto-generated method stub
		super.bindEngine(eng);
	}

	
}