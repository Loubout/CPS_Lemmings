package contract;

import services.GameEngService;
import services.LemmingService;
import decorators.LemmingDecorator;
import enumeration.Direction;
import enumeration.Nature;
import enumeration.Status;
import enumeration.Type;

public class LemmingContract extends LemmingDecorator{

	public LemmingContract(LemmingService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}

	
	public void checkInvariant(){
		/*	INVARIANTS
		 * 	getGameEng().getLevel().getNature(getX(),getY()-1) = Nature.EMPTY
		 */

		if (super.getGameEng().getLevel().getNature(getx(), getY() - 1) != Nature.EMPTY) throw new InvariantError("Invariant error : cell above lemming should be EMPTY");
		
	}
	
	
	@Override
	public Direction getDirection() {
		// TODO Auto-generated method stub
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
	public int getx() {
		// TODO Auto-generated method stub
		return super.getx();
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
	public void init(Type type, int num) {
		// TODO Auto-generated method stub
		super.init(type, num);
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		super.step();
	}

	@Override
	public void bindEngine(GameEngService eng) {
		// TODO Auto-generated method stub
		super.bindEngine(eng);
	}

	@Override
	public void init(int num) {
		// TODO Auto-generated method stub
		super.init(num);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

}
