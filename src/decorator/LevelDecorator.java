package decorator;

import enumeration.Nature;
import services.LevelService;

public class LevelDecorator implements LevelService{

	private LevelService delegate;

	public LevelDecorator(LevelService delegate) {
		
		this.delegate = delegate;
	}

	
	public int getWidth() {
		return delegate.getWidth();
	}

	public int getHeight() {
		return delegate.getHeight();
	}

	public Nature getNature(int x, int y) {
		return delegate.getNature(x, y);
	}

	public boolean editing() {
		return delegate.editing();
	}

	public int getEntranceX() {
		return delegate.getEntranceX();
	}

	public int getEntranceY() {
		return delegate.getEntranceY();
	}

	public int getExitX() {
		return delegate.getExitX();
	}

	public int getExitY() {
		return delegate.getExitY();
	}

	public void init(int w, int h) {
		delegate.init(w, h);
	}

	public void setNature(int x, int y, Nature nat) {
		delegate.setNature(x, y, nat);
	}

	public void goPlay() {
		delegate.goPlay();
	}

	public void remove(int x, int y) {
		delegate.remove(x, y);
	}

	public void build(int x, int y) {
		delegate.build(x, y);
	}
}
