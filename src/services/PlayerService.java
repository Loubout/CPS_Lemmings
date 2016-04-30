package services;

import java.util.Map;
import java.util.Set;

import enumeration.Specialty;

public interface PlayerService {
	
	public Set<Specialty> getAvailableClasses();
	
	public Map<Specialty, Integer> getJetons();
	
	
	public int getNbJetonsFor(Specialty c);
	
	public void init();
	
	// OPERATION
	// PRE lemmy.getStatus() == Status.LIVING
	public void transformLemming(LemmingService lemmy, Specialty sp);
}
