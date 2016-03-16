package services;

public interface LevelService {

	public int getWidth();
	public int getHeigt();
	public Nature getNature(int x, int y);
	public boolean editing();
	
	//PRE !editing()
	public int getEntranceX();
	
	//PRE !editing()
	public int getEntranceY();
	
	//PRE !editing()
	public int getExitX();
	
	//PRE !editing()
	public int getExitY();
	
	
	//PRE w > 0 ^ h > 0
	//POST !editing()
	//		getHeight() == h
	//		getWidth() == w
	//		\forall (x, y) x = 0 || x = width -1 || y = 0 || y = height - 1, getNature(l, x, y) == Nature.METAL
    // 		\forall (x, y) x != 0 ^ x != width -1 ^ y != 0 ^ y != height - 1, getNature(l, x, y) == Nature.EMPTY
	public void init(int w, int h);
	
	
	//PRE editing()
	//POST	!editing()
	//		  \forall (i, j) 0 < i < getWidth() ^ 0 < j < getHeight(), getNature(i, j) == getNature(i, j)@pre	
	public void goPlay();
	
	//PRE !editing()
	//	  getNature(x, y) == Nature.DIRT && 0 < x && x > getWidth() && 0 < y && y < getHeight()
	//POST	getNature(x, y) == Nature.EMPTY
	//		\forall (i, j) (i != x || j != y) ^ 0 < i < getWidth() ^ 0 < j < getHeight(), getNature(i, j) == getNature(i, j)@pre
	public void remove(int x, int y);
	
	
	//PRE !editing()
	//	  getNature(x, y) == Nature.EMPTY && 0 < x && x > getWidth() && 0 < y && y < getHeight()
	//POST	getNature(x, y) == Nature.DIRT
	//		\forall (i, j) (i != x || j != y) ^ 0 < i < getWidth() ^ 0 < j < getHeight(), getNature(i, j) == getNature(i, j)@pre
	public void build();
	
	
	
	
	
}
