import java.util.List;
import java.util.ArrayList;

public class Layer {
	List<Neuron> layer;
	
	public Layer() {
		this.layer = new ArrayList<Neuron>();
	}
	
	// Add a Neuron to this Layer.
	public void addNeuron(Neuron n) {
		this.layer.add(n);
	}
	
	// Return the number of Neurons that this Layer has.
	public int size() {
		return this.layer.size();
	}
	
	// Return a list of weights associated with each existing Neuron in this Layer.
	public List<Double> getWeights(){
		List<Double> result = new ArrayList<Double>();
		for (Neuron n: layer) {
			for(Double w : n.weight_neuron.values()) {
				result.add(w);
			}
		}
		return result;
	}
	
	// Return a specific Neuron in this Layer.
	public Neuron getNeuron(int index) {
		return layer.get(index);
	}
}
