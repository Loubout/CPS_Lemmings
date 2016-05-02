package services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import enumeration.Specialty;

public interface PlayerService extends RequireGameEngineService{
	
	public Set<Specialty> getAvailableClasses();
	
	public Map<Specialty, Integer> getJetons();
		
	public int getNbJetonsFor(Specialty c);
	
	public GameEngService getEngine();
	
	// PRE
	// for (Integer i : chipsMap.values() )  i > 0
	public void init(HashMap<Specialty, Integer> chipsMap);
	
	// OPERATION
	// PRE lemmy.getStatus() == Status.LIVING
	// 	   getNbJetonsFor(sp) > 0
	public void transformLemming(LemmingService lemmy, Specialty sp);
}
