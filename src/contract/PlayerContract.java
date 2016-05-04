package contract;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import services.GameEngService;
import services.LemmingService;
import enumeration.Specialty;
import enumeration.Status;
import impl.PlayerImpl;

public class PlayerContract extends PlayerImpl{
	
	public PlayerContract(){
		super();
	}
	
	public void checkInvariant(){
		for (int val : super.getJetons().values()){
			if (val < 0) throw new InvariantError("Number of chips should always stay positive");
		}
		
	}
	
	@Override
	public Set<Specialty> getAvailableClasses() {
		return super.getAvailableClasses();
	}

	@Override
	public Map<Specialty, Integer> getJetons() {
		return super.getJetons();
	}

	@Override
	public int getNbJetonsFor(Specialty c) {
		return super.getNbJetonsFor(c);
	}
	
	@Override
	public void bindEngine(GameEngService eng) {
		super.bindEngine(eng);
	}

	@Override
	public void init(HashMap<Specialty, Integer> chipsMap) {
		// PRE
		for (int val : chipsMap.values()){
			if (val < 0) throw new PreconditionError("negative number of chips");
		}
		super.init(chipsMap);
		
		// POST
		for (Specialty key : this.getJetons().keySet()){
			if (this.getNbJetonsFor(key) != chipsMap.get(key)) throw new PostconditionError("Number of chips is not equal to the one specified in init");
		}
	
	}
		
	// unlimited transform for now
	@Override
	public void transformLemming(LemmingService lemmy, Specialty sp) {
		// pre
		
		int chips_pre = this.getNbJetonsFor(sp); 
		if (lemmy.getStatus() != Status.LIVING) throw new PreconditionError("Can't transform a dead lemming");

		if (super.getNbJetonsFor(sp) < 1) throw new PreconditionError("not enough chips to transform lemming into " + sp.toString());
	
		this.checkInvariant();
		
		super.transformLemming(lemmy, sp);
		
		this.checkInvariant();
		if (super.getNbJetonsFor(sp) != chips_pre - 1) throw new PostconditionError("Number of chips should decrease after transform");
		if (!lemmy.getSpecials().contains(sp)) throw new PostconditionError("Transformation didn't work");
	}
}
