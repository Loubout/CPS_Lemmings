package contract;

import java.util.Random;

import decorator.LevelDecorator;
import enumeration.Nature;
import services.LevelService;

public class LevelContract extends LevelDecorator{

	public LevelContract(LevelService delegate) {
		super(delegate);
		// TODO Auto-generated constructor stub
	}
	
	
	public void checkInvariant(){
		for(int i = 0; i < super.getWidth(); i++){ 
			if (super.getNature(i, 0) != Nature.METAL || super.getNature(i, super.getHeight() - 1 ) != Nature.METAL)
				throw new InvariantError("BORDERS SHOULD BE MADE OF HEAVY.METAL");
		}
		for(int j = 0; j < super.getHeight(); j++){
			if (super.getNature(0, j) != Nature.METAL || super.getNature(super.getWidth() - 1,j) != Nature.METAL)
				throw new InvariantError("BORDERS SHOULD BE MADE OF HEAVY.METAL");
		}
		
	}
	
	

	@Override
	public int getWidth() {
		return super.getWidth();
	}

	@Override
	public int getHeight() {
		return super.getHeight();
	}

	@Override
	public Nature getNature(int x, int y) {
		return super.getNature(x, y);
	}

	@Override
	public boolean editing() {
		return super.editing();
	}

	@Override
	public int getEntranceX() {
		if(super.editing()) throw new InvariantError("Entrance is not initialized in mode editing");
		return super.getEntranceX();
	}

	@Override
	public int getEntranceY() {
		if(super.editing()) throw new InvariantError("Entrance is not initialized in mode editing");
		return super.getEntranceY();
	}

	@Override
	public int getExitX() {
		if(super.editing()) throw new InvariantError("Exit is not initialized in mode editing");
		return super.getExitX();
	}

	@Override
	public int getExitY() {
		if(super.editing()) throw new InvariantError("Exit is not initialized in mode editing");
		return super.getExitY();
	}

	@Override
	public void init(int w, int h) {
		
		if(w<4 || h<5) throw new PreconditionError("Width should be greater than 4 and height greater than 5");
		super.init(w, h);
		
		checkInvariant();
		
		if(!super.editing()) throw new PostconditionError("Level should be initialized in editing mode");
		if(super.getHeight() != h) throw new PostconditionError("Wrong initialization of Height");
		if(super.getWidth() != w) throw new PostconditionError("Wrong initialization of Width");
		
		for(int i=1; i<w-2; i++){
			for(int j=1; j<h-2; j++){
				if(super.getNature(i, j)!=Nature.EMPTY) throw new PostconditionError("Inside cells should be empty after level initialization");
			}
		}
	}

	@Override
	public void setNature(int x, int y, Nature nat) {
		//PRE 	!editing()
		//		0 < y < getHeight() - 1
		//		0 < x < getWidth() - 1
		//POST	getNature(setNature(x,y,n)) == n
		//		\forall (i, j) (i != x || j != y) ^ 0 < i < width ^ 0 < j < height, getNature(setNature(x ,y ,n), i, j) == getNature(l, i, j)
		System.out.println("SET NATURE CONTRACT");
		
		int[] cellNatureX_atpre = new int[10];
		int[] cellNatureY_atpre = new int[10];
		Nature[] cellNature_atpre = new Nature[10];
		int i = 0;
		Random r = new Random();
		while (i < 10){
			int xr = r.nextInt(super.getWidth()-1);
			int yr = r.nextInt(super.getHeight()-1);
			if (xr == x || yr == y) continue;
			cellNatureX_atpre[i] = xr;
			cellNatureY_atpre[i] = yr;
			cellNature_atpre[i] = super.getNature(xr, yr);
			i++;
		}
		
		
		if(!super.editing()) throw new PreconditionError("Level should be in editing mode");
		if(x<0 || x>super.getWidth()-2) throw new PreconditionError("x should be beetween 0 and width");
		if(y<0 || y>super.getHeight()-2) throw new PreconditionError("y should be beetween 0 and height");
		
		
		
		checkInvariant();
		super.setNature(x, y, nat);
		checkInvariant();
		
		if(super.getNature(x, y)!=nat)  throw new PostconditionError("Nature of the cell (x,y) should be as expected");
		
		for (int k = 0; k < 10; k++){
			if (super.getNature(cellNatureX_atpre[k], cellNatureY_atpre[k]) != cellNature_atpre[k])
				throw new PostconditionError("Nature of others cells should not be impacted by setNature op");
		}
		
	}

	@Override
	public void goPlay(int x1, int y1, int x2, int y2) {
		//PRE editing()
		//POST	!editing()
		//		\forall (i, j) 0 < i < getWidth() ^ 0 < j < getHeight(), getNature(i, j) == getNature(i, j)@pre	
		//		getNature(getEntranceX(), getEntranceY()) == Nature.EMPTY 
	    //		getNature(getEntranceX(), getEntranceY() - 1) == Nature.EMPTY
	    //		getNature(getEntranceX(), getEntranceY() + 1) == Nature.EMPTY
	    //		getNature(getExitX(), getExitY() == Nature.EMPTY 
	    //		getNature(getExitX(), getExitY() - 1) == Nature.EMPTY
	    //		getNature(getExitX(), getExitY() + 1) == Nature.METAL
		
		int[] cellNatureX_atpre = new int[10];
		int[] cellNatureY_atpre = new int[10];
		Nature[] cellNature_atpre = new Nature[10];
		int i = 0;
		
		// we only check for 10 random cells
		while (i < 10){
			Random r = new Random();
			int xr = r.nextInt(super.getWidth()-1);
			int yr = r.nextInt(super.getHeight()-1);
			
			cellNatureX_atpre[i] = xr;
			cellNatureY_atpre[i] = yr;
			cellNature_atpre[i] = super.getNature(xr, yr);
			i++;
		}
		
		if(!super.editing()) throw new PreconditionError("We should be in editing before calling goPlay op");
		
		checkInvariant();
		super.goPlay(x1, y1, x2, y2);
		checkInvariant();
		
		for (int k = 0; k < 10; k++){
			if (super.getNature(cellNatureX_atpre[k], cellNatureY_atpre[k]) != cellNature_atpre[k])
				throw new PostconditionError("Nature of others cells should not be impacted by goPlay op");
		}
		
		if(super.getNature(super.getEntranceX(), super.getEntranceY())!=Nature.EMPTY) throw new PostconditionError("Entrance cell should be EMPTY");
		if(super.getNature(super.getEntranceX(), super.getEntranceY()-1)!=Nature.EMPTY) throw new PostconditionError("The cell above the entrance should be EMPTY");
		if(super.getNature(super.getEntranceX(), super.getEntranceY()+1)!=Nature.EMPTY) throw new PostconditionError("The cell below the entrance should be EMPTY");
		
		if(super.getNature(super.getExitX(), super.getExitY())!= Nature.EMPTY) throw new PostconditionError("Exit cell should be EMPTY");
		if(super.getNature(super.getExitX(), super.getExitY()-1)!= Nature.EMPTY) throw new PostconditionError("Cell above exit cell should be EMPTY");
		if(super.getNature(super.getExitX(), super.getExitY()+1)!= Nature.METAL) throw new PostconditionError("Cell below exit cell should be MADE OF TRVE HEAVY METAL");

	}

	@Override
	public void remove(int x, int y) {
		//PRE !editing()
		//	  getNature(x, y) == Nature.DIRT && 0 < x && x > getWidth() && 0 < y && y < getHeight()
		//POST	getNature(x, y) == Nature.EMPTY
		//		\forall (i, j) (i != x || j != y) ^ 0 < i < getWidth() ^ 0 < j < getHeight(), getNature(i, j) == getNature(i, j)@pre
		
		int[] cellNatureX_atpre = new int[10];
		int[] cellNatureY_atpre = new int[10];
		Nature[] cellNature_atpre = new Nature[10];
		int i = 0;
		Random r = new Random();
		while (i < 10){
			int xr = r.nextInt(super.getWidth()-1);
			int yr = r.nextInt(super.getHeight()-1);
			cellNatureX_atpre[i] = xr;
			cellNatureY_atpre[i] = yr;
			cellNature_atpre[i] = super.getNature(xr, yr);
			i++;
		}
		
		if(super.editing()) throw new PreconditionError("game mode should not be in editing mode before remove op");
		if(super.getNature(x, y) != Nature.DIRT) throw new PreconditionError("You can only remove a DIRT cell");
		if(x<0 || x > super.getWidth()) throw new PreconditionError("x should be in the right borders");
		if(y<0 || y > super.getHeight()) throw new PreconditionError("y should be in the right borders");
		
		checkInvariant();
		super.remove(x, y);
		checkInvariant();
		
		if(super.getNature(x, y) != Nature.EMPTY) throw new PostconditionError("After a remove, the cell (x,y) should be EMPTY");
		
		for (int k = 0; k < 10; k++){
			if (super.getNature(cellNatureX_atpre[k], cellNatureY_atpre[k]) != cellNature_atpre[k])
				throw new PostconditionError("Nature of others cells should not be impacted by remove op");
		}
	}

	@Override
	public void build(int x, int y) {
		//PRE !editing()
		//	  getNature(x, y) == Nature.EMPTY && 0 < x && x > getWidth() && 0 < y && y < getHeight()
		//POST	getNature(x, y) == Nature.DIRT
		//		\forall (i, j) (i != x || j != y) ^ 0 < i < getWidth() ^ 0 < j < getHeight(), getNature(i, j) == getNature(i, j)@pre
		
		int[] cellNatureX_atpre = new int[10];
		int[] cellNatureY_atpre = new int[10];
		Nature[] cellNature_atpre = new Nature[10];
		int i = 0;
		Random r = new Random();
		while (i < 10){
			int xr = r.nextInt(super.getWidth()-1);
			int yr = r.nextInt(super.getHeight()-1);
			
			cellNatureX_atpre[i] = xr;
			cellNatureY_atpre[i] = yr;
			cellNature_atpre[i] = super.getNature(xr, yr);
			i++;
		}
		
		if(super.editing()) throw new PreconditionError("game mode should not be in editing mode before remove op");
		if(super.getNature(x, y) != Nature.EMPTY) throw new PreconditionError("You can only build an EMPTY cell");
		if(x<0 || x > super.getWidth()) throw new PreconditionError("x should be in the right borders");
		if(y<0 || y > super.getHeight()) throw new PreconditionError("y should be in the right borders");
		
		checkInvariant();
		super.build(x, y);
		checkInvariant();
		
		if(super.getNature(x, y) != Nature.DIRT) throw new PostconditionError("After a build, the cell (x,y) should be DIRT");
		
		for (int k = 0; k < 10; k++){
			if (super.getNature(cellNatureX_atpre[k], cellNatureY_atpre[k]) != cellNature_atpre[k])
				throw new PostconditionError("Nature of others cells should not be impacted by build op");
		}
	}

	

}
