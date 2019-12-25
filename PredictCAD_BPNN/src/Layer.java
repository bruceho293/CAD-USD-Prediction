import java.util.List;
import java.util.ArrayList;

public class Layer {
	List<Neuron> layer;
	
	public Layer() {
		this.layer = new ArrayList<Neuron>();
	}
	
	public void addNeuron(Neuron n) {
		this.layer.add(n);
	}
	
	public int size() {
		return this.layer.size();
	}
	
	public List<Double> getWeights(){
		List<Double> result = new ArrayList<Double>();
		for (Neuron n: layer) {
			for(Double w : n.weight_neuron.values()) {
				result.add(w);
			}
		}
		return result;
	}
	
	
	public Neuron getNeuron(int index) {
		return layer.get(index);
	}
}
