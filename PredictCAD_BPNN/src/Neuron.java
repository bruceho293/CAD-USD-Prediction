import java.util.Hashtable;

public class Neuron {
	
	double input;
	double output;
	Hashtable<Neuron, Double> weight_neuron;
	double error;
	
	public Neuron(double i, double o) {
		this.input = i;
		this.output = o;
		this.weight_neuron = new Hashtable<Neuron, Double>();
	}
	
	// Adding the weight with the according Neuron in the higher Layer.
	public void addingWeight(double w, Neuron n) {
		this.weight_neuron.put(n, w);
	}
	
	// Adjust the weight of an existing Neuron.
	public void adjustWeight(double w, Neuron n) {
		this.weight_neuron.replace(n, w);
	}
	
	// Return the weight that connect this Neuron with the Neuron "n".
	public double getWeight(Neuron n) {
		return this.weight_neuron.get(n);
	}
	
	// Execute the activation function
	public void callActivationFunction() {
		this.output = Helper.sigmoidFunct(this.input);
	}
}
