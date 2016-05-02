package impl;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import enumeration.Specialty;
import services.GameEngService;
import services.LemmingService;
import services.PlayerService;
import services.RequireGameEngineService;

public class PlayerImpl implements PlayerService, RequireGameEngineService{
	private GameEngService eng;
	private HashMap<Specialty, Integer> chips;
	
	@Override
	public Set<Specialty> getAvailableClasses() {
		return chips.keySet();
	}

	@Override
	public Map<Specialty, Integer> getJetons() {
		return chips;
	}

	@Override
	public int getNbJetonsFor(Specialty c) {
		return chips.get(c);
	}
	
	@Override
	public void bindEngine(GameEngService eng) {
		this.eng = eng;
	}

	@Override
	public void init(HashMap<Specialty, Integer> chipsMap) {
		this.chips = chipsMap;
	}
		
	// unlimited transform for now
	@Override
	public void transformLemming(LemmingService lemmy, Specialty sp) {
		lemmy.transform(sp);	
	}

	@Override
	public GameEngService getEngine() {
		return eng;
	}
}