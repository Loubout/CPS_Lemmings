package services;

import java.util.Map;
import java.util.Set;

public interface PlayerService {
	
	
	public Set<Class> getAvailableClasses();
	
	
	public Map<Class, Integer> getJetons();
	
	
	public int getNbJetonsFor(Class c);
	
	
	public void init();
}
