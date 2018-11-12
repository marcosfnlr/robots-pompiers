package simulation;
import simulation.carte.Case;

public class PairDoubleCase{
	private Double key;
	private Case value;

	public PairDoubleCase(Double key, Case value){
		this.key = key;
		this.value = value;
	}

	public Double getKey(){
		return this.key;
	}

	public Case getValue(){
		return this.value;
	}
}