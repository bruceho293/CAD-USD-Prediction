import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BackPropagationNeuronNetwork {
	Layer inputLayer, outputLayer;
	List<Layer> hiddenLayers;
	Neuron bias;
	double learningRate;
	double CError;
	double errorAcceptance;
	
	
	public BackPropagationNeuronNetwork() {
		this.bias = new Neuron(0, 1);
		this.inputLayer = new Layer();
		this.outputLayer = new Layer();
		this.hiddenLayers = new ArrayList<Layer>();
		
		this.inputLayer.addNeuron(this.bias);
	}
	
	public void setLearningRate(double lr) {
		this.learningRate = lr;
	}
	
	public double getError() {
		return 0.5 * this.CError;
	}
	
	public void resetError() {
		this.CError = 0;
	}
	
	public void setExpectedError(double e) {
		this.errorAcceptance = e;
	}
	
	public double getExpectedError() {
		return this.errorAcceptance;
	}
	
	// Adding the input Layer with the size "size".
	public void addInputLayer(int size) {
		for(int i = 0; i < size; i ++) {
			Neuron neuron = new Neuron(0, 0);
			inputLayer.addNeuron(neuron);
		}
	}

	// Adding the output Layer with the size "size".
	public void addOutputLayer(int size) {
		for(int i = 0; i < size; i ++) {
			Neuron neuron = new Neuron(0, 0);
			outputLayer.addNeuron(neuron);
		}
	}
	
	// Adding the hidden Layer with the size "size".
	public void addHiddenLayer(int size) {
		Layer layer = new Layer();
		for (int i = 0; i < size; i ++)
			layer.addNeuron(new Neuron(0, 0));
		hiddenLayers.add(layer);
	}
	
	// Adding a list of inputs to the input Layer.
	// Each input will be stored in one Neuron.
	public void addInputNeurons(List<Double> inputs) {
		if (inputs.size() == inputLayer.size()) {
			for (int i = 0; i < inputs.size(); i ++) {
				inputLayer.getNeuron(i).output = inputs.get(i);
			}
		}
	}
	
	// Returns a string representation of all weights in the network.
	public String getWeights() {
		String text = "";
		for(int i = 0; i < total().size() - 1; i ++){
			text += total().get(i).getWeights().toString() + "\n";
		}
		return text;
	}
	
	// Return a list of all Layers in the network.
	private List<Layer> total(){
		List<Layer> totalLayers = new ArrayList<Layer>();
		totalLayers.add(inputLayer);
		totalLayers.addAll(hiddenLayers);
		totalLayers.add(outputLayer);
		return totalLayers;
	}
	
	/** Each bottom layer will store the neuron in the upper layer
	 * 	together with the associated weight.		
	*/
	public void assigningWeight() {
		List<Layer> totalLayers = total();
		int counter = 1;
		while (counter < totalLayers.size()) {
			Layer bottom = totalLayers.get(counter - 1);
			Layer top = totalLayers.get(counter);
			for (int i = 0; i < bottom.size(); i ++) {
				for (int j = 0; j < top.size(); j ++) {
					Random r = new Random();
					double weight = r.nextDouble();
					bottom.getNeuron(i).addingWeight(weight, top.getNeuron(j));
				}
			}
			counter ++;
		}
	}
	
	// Forward Backpropagation
	public void forwardPropogation() {
		List<Layer> totalLayers = total();
		int counter = 1;
		while (counter < totalLayers.size()){
			Layer current = totalLayers.get(counter);
			Layer lower = totalLayers.get(counter - 1);
			for (int i = 0; i < current.size(); i ++) {
				// Compute the net
				double net = 0;
				for(int j = 0; j < lower.size(); j ++) {
					net += lower.getNeuron(j).output * lower.getNeuron(j).getWeight(current.getNeuron(i));
				}
				current.getNeuron(i).input = net;
				current.getNeuron(i).callActivationFunction();
			}
			counter ++;
		}
	}
	
	//Calculate the error in the output Layer.
	public void calculateError(double[] expectedOutput) {
		if (expectedOutput.length == outputLayer.size()) {
			for (int i = 0; i < outputLayer.size(); i++) {
				Neuron no = outputLayer.getNeuron(i);
				double output = no.output;
				double input = no.input;
				no.error = (expectedOutput[i] - output) * Helper.derivative_sigmoidFunct(input);
				CError += Math.pow(expectedOutput[i] - output,  2.0);
				adjustWeight(no, total().get(total().size() - 2));
			}
		} else
			System.out.println("Error");
	}
	
	// Propagate back the errors throughout the entire network.
	public void errorBackPropagation() {
		List<Layer> totalLayers = total();		
		int counter = totalLayers.size() - 2;
		while (counter >= 0) {
			Layer current = totalLayers.get(counter);
			Layer top_current = totalLayers.get(counter + 1);
			for (int i = 0; i < current.size(); i ++) {
				
				double error = 0;
				Neuron cn = current.getNeuron(i);
				
				for(int j = 0; j < top_current.size(); j++) {
					error += top_current.getNeuron(j).error * cn.getWeight(top_current.getNeuron(j));
				}
				error = error * Helper.derivative_sigmoidFunct(cn.input);
				cn.error = error;
				
				if (counter >= 1)
					adjustWeight(cn, totalLayers.get(counter - 1));
			
			}
			counter --;
		}
	}
	
	// Adjust the associated weight of the Neuron "current" in each Neuron
	// in the Layer "bottom".
	public void adjustWeight(Neuron current, Layer bottom) {
		for(int i = 0; i < bottom.size(); i ++) {
			Neuron n = bottom.getNeuron(i);
			double weight = n.getWeight(current);
			weight = weight + learningRate * current.error * n.output;
			if (weight != n.getWeight(current))
				n.addingWeight(weight, current);
		}
	}
	


	
}
