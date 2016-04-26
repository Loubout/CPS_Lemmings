package services;

import enumeration.Nature;

public interface LevelService {

	public int getWidth();
	public int getHeight();
	public Nature getNature(int x, int y);
	public boolean editing();
	
	/*	INVARIANTS
	 *  \forall (x, y) x = 0 || x = width -1 || y = 0 || y = height - 1, getNature(l, x, y) == Nature.METAL	
	 */
	
	//PRE !editing()
	public int getEntranceX();
	
	//PRE !editing()
	public int getEntranceY();
	
	//PRE !editing()
	public int getExitX();
	
	//PRE !editing()
	public int getExitY();
	
	
	//PRE w >= 4 ^ h >= 5
	//POST !editing()
	//		getHeight() == h
	//		getWidth() == w
	//		\forall (x, y) x = 0 || x = width -1 || y = 0 || y = height - 1, getNature(l, x, y) == Nature.METAL
    // 		\forall (x, y) x != 0 ^ x != width -1 ^ y != 0 ^ y != height - 1, getNature(l, x, y) == Nature.EMPTY
	public void init(int w, int h);
	
	//PRE 	editing()
	//		0 < y < getHeight() - 1
	//		0 < x < getWidth() - 1
	//POST	getNature(setNature(x,y,n)) == n
	//		\forall (i, j) (i != x || j != y) ^ 0 < i < width ^ 0 < j < height, getNature(setNature(x ,y ,n), i, j) == getNature(l, i, j)
	public void setNature(int x, int y, Nature nat);
	
	//PRE editing()
	//POST	!editing()
	//		\forall (i, j) 0 < i < getWidth() ^ 0 < j < getHeight(), getNature(i, j) == getNature(i, j)@pre	
	//		getNature(getEntranceX(), getEntranceY() == EMPTY 
    //		getNature(getEntranceX(), getEntranceY() - 1) == Nature.EMPTY
    //		getNature(getEntranceX(), getEntranceY() + 1) == Nature.EMPTY
    //		getNature(getExitX(), getExitY() == Nature.EMPTY 
    //		getNature(getExitX(), getExitY() - 1) == Nature.EMPTY
    //		getNature(getExitX(), getExitY() + 1) == Nature.METAL
	public void goPlay(int x1, int y1, int x2, int y2);
	
	//PRE !editing()
	//	  getNature(x, y) == Nature.DIRT && 0 < x && x > getWidth() && 0 < y && y < getHeight()
	//POST	getNature(x, y) == Nature.EMPTY
	//		\forall (i, j) (i != x || j != y) ^ 0 < i < getWidth() ^ 0 < j < getHeight(), getNature(i, j) == getNature(i, j)@pre
	public void remove(int x, int y);
	
	
	//PRE !editing()
	//	  getNature(x, y) == Nature.EMPTY && 0 < x && x > getWidth() && 0 < y && y < getHeight()
	//POST	getNature(x, y) == Nature.DIRT
	//		\forall (i, j) (i != x || j != y) ^ 0 < i < getWidth() ^ 0 < j < getHeight(), getNature(i, j) == getNature(i, j)@pre
	public void build(int x, int y);
	
	
	
	
	
}
