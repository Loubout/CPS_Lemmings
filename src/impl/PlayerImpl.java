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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Specialty, Integer> getJetons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNbJetonsFor(Specialty c) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void bindEngine(GameEngService eng) {
		this.eng = eng;
	}

	@Override
	public void init() {
		chips = new HashMap<Specialty, Integer>();
		
	}
	
		
	/// unlimited transform for now
	@Override
	public void transformLemming(LemmingService lemmy, Specialty sp) {
		lemmy.transform(sp);	
	}

	
}
