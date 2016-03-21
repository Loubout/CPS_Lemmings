package contract;

import services.LevelService;
import decorators.LevelDecorator;
import enumeration.Nature;

public class LevelContract extends LevelDecorator{

	public LevelContract(LevelService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean checkInvariant(){
		return false;
		
	}
	
	

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return super.getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return super.getHeight();
	}

	@Override
	public Nature getNature(int x, int y) {
		// TODO Auto-generated method stub
		return super.getNature(x, y);
	}

	@Override
	public boolean editing() {
		// TODO Auto-generated method stub
		return super.editing();
	}

	@Override
	public int getEntranceX() {
		// TODO Auto-generated method stub
		return super.getEntranceX();
	}

	@Override
	public int getEntranceY() {
		// TODO Auto-generated method stub
		return super.getEntranceY();
	}

	@Override
	public int getExitX() {
		// TODO Auto-generated method stub
		return super.getExitX();
	}

	@Override
	public int getExitY() {
		// TODO Auto-generated method stub
		return super.getExitY();
	}

	@Override
	public void init(int w, int h) {
		// TODO Auto-generated method stub
		super.init(w, h);
	}

	@Override
	public void setNature(int x, int y, Nature nat) {
		// TODO Auto-generated method stub
		super.setNature(x, y, nat);
	}

	@Override
	public void goPlay() {
		// TODO Auto-generated method stub
		super.goPlay();
	}

	@Override
	public void remove(int x, int y) {
		// TODO Auto-generated method stub
		super.remove(x, y);
	}

	@Override
	public void build(int x, int y) {
		// TODO Auto-generated method stub
		super.build(x, y);
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
