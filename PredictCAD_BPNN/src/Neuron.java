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
	
	public void addingWeight(double w, Neuron n) {
		this.weight_neuron.put(n, w);
	}
	
	public void adjustWeight(double w, Neuron n) {
		this.weight_neuron.replace(n, w);
	}
	
	public double getWeight(Neuron n) {
		return this.weight_neuron.get(n);
	}
	
	public void callActivationFunction() {
		this.output = Helper.sigmoidFunct(this.input);
	}
}
