package testJunit;

import static org.junit.Assert.assertTrue;
import impl.LevelImpl;

import org.junit.Test;

import services.LevelService;
import contract.LevelContract;
import contract.PreconditionError;
import enumeration.Nature;

public class TestLevel {
	//Tests sur les preconditions de chaque operateur
	
	@Test 
	public final void testInitSuccess(){
		//test positif d'une initialisation
		LevelService delegate = new LevelImpl();
		LevelContract level = new LevelContract(delegate);
		int height = 10;
		int width = 10;
		level.init(width, height);
		assertTrue("height < 0 or width < 0", height>0 && width>0);
	}
	
	@Test (expected = PreconditionError.class)
	public final void testInitFail(){
		//test negatif d'une initialisation, renvoie une exception de la classe PreconditionError
		int height = 4;
		int width = 3;
		LevelService delegate = new LevelImpl();
		LevelContract level = new LevelContract(delegate);
		level.init(width, height);
		assertTrue("height < 0 or width < 0", height<5 || width<4);
	}
	
	@Test
	public final void testSetNatureSuccess(){
		//test positif de modification d'une case
		LevelService delegate = new LevelImpl();
		LevelContract level = new LevelContract(delegate);
		int height = 10, width = 10;
		int x = 5, y = 5; 
		level.init(width, height);
		level.setNature(x, y, Nature.DIRT);
		assertTrue("x < 0 or y < 0", x>0 || y>0);
	}
	
	@Test (expected = PreconditionError.class)
	public final void testSetNatureFail(){
		//test negatif de modification d'une case, renvoie une exception de la classe PreconditionError
		LevelService delegate = new LevelImpl();
		LevelContract level = new LevelContract(delegate);
		int height = 10, width = 10;
		int x = -5, y = 5; 
		level.init(width, height);
		level.setNature(x, y, Nature.DIRT);
		assertTrue("x < 0 or y < 0", x>0 || y>0);
	}
	
	@Test
	public final void testGoPlaySuccess(){
		LevelService delegate = new LevelImpl();
		LevelContract level = new LevelContract(delegate);
		int height = 10;
		int width = 10;
		int entranceX = 5, entranceY=5;
		int exitX = 8, exitY = 8;
		level.init(width, height);
		level.goPlay(entranceX, entranceY, exitX, exitY);
		assertTrue("entrance or exit out of bounds", entranceX>0 || entranceY>0 || exitX>0 || exitY>0);
	}
	
	@Test (expected = PreconditionError.class)
	public final void testGoPlayFail(){
		LevelService delegate = new LevelImpl();
		LevelContract level = new LevelContract(delegate);
		int height = 10;
		int width = 10;
		int entranceX = -5, entranceY=5;
		int exitX = 8, exitY = 8;
		level.init(width, height);
		level.goPlay(entranceX, entranceY, exitX, exitY);
		assertTrue("entrance or exit out of bounds", entranceX>0 || entranceY>0 || exitX>0 || exitY>0);
	}
	@Test
	public final void testRemoveSuccess(){
		LevelService delegate = new LevelImpl();
		LevelContract level = new LevelContract(delegate);
		int height = 10;
		int width = 10;
		int x=5, y=5;
		int entranceX = 6, entranceY=6;
		int exitX = 8, exitY = 8;
		level.init(width, height);
		level.setNature(x, y, Nature.DIRT);
		level.goPlay(entranceX, entranceY, exitX, exitY);
		level.remove(x, y);
		assertTrue("x or y out of bounds", x>0 || y>0);
	}
	
	
	@Test (expected = PreconditionError.class)
	public final void testRemoveFail(){
		LevelService delegate = new LevelImpl();
		LevelContract level = new LevelContract(delegate);
		int height = 10;
		int width = 10;
		int x=-5, y=5;
		int entranceX = 5, entranceY=5;
		int exitX = 8, exitY = 8;
		level.init(width, height);
		level.setNature(x, y, Nature.DIRT);
		level.goPlay(entranceX, entranceY, exitX, exitY);
		level.remove(x, y);
		assertTrue("x or y out of bounds", x>0 || y>0);
	}
	
	@Test
	public final void testBuildSuccess(){
		LevelService delegate = new LevelImpl();
		LevelContract level = new LevelContract(delegate);
		int height = 10;
		int width = 10;
		int x=5, y=5;
		int entranceX = 6, entranceY=6;
		int exitX = 8, exitY = 8;
		level.init(width, height);
		level.goPlay(entranceX, entranceY, exitX, exitY);
		level.build(x, y);
		assertTrue("x or y out of bounds", x>0 || y>0);
	}
	
	
	@Test (expected = PreconditionError.class)
	public final void testBuildFail(){
		LevelService delegate = new LevelImpl();
		LevelContract level = new LevelContract(delegate);
		int height = 10;
		int width = 10;
		int x=0, y=0;
		int entranceX = 5, entranceY=5;
		int exitX = 8, exitY = 8;
		level.init(width, height);
		level.goPlay(entranceX, entranceY, exitX, exitY);
		level.build(x, y);
		assertTrue("x or y out of bounds", x>0 || y>0);
	}
}
