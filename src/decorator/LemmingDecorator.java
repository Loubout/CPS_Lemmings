package decorator;

import java.util.Set;

import enumeration.Direction;
import enumeration.Specialty;
import enumeration.Status;
import enumeration.Type;
import services.GameEngService;
import services.LemmingService;

public class LemmingDecorator implements LemmingService {
	
	private LemmingService delegate;
	
	public LemmingDecorator(LemmingService delegate) {
		this.delegate = delegate;
	}

	public Direction getDirection() {
		return delegate.getDirection();
	}

	public int getNumber() {
		return delegate.getNumber();
	}

	public Type getType() {
		return delegate.getType();
	}

	public Status getStatus() {
		return delegate.getStatus();
	}

	public int getX() {
		return delegate.getX();
	}

	public int getY() {
		return delegate.getY();
	}

	public int getFallTime() {
		return delegate.getFallTime();
	}

	public GameEngService getGameEng() {
		return delegate.getGameEng();
	}

	public void init(Type type, int num) {
		delegate.init(num);
	}

	public void step() {
		delegate.step();
	}

	
	public void bindEngine(GameEngService eng) {
		delegate.bindEngine(eng);
	}

	@Override
	public void init(int num) {
		delegate.init(num);
	}

	@Override
	public Set<Specialty> getSpecials() {
		return delegate.getSpecials();
	}

	@Override
	public int getNbStep() {
		return delegate.getNbStep();
	}

}
