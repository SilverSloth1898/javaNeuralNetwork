package nueralNet;

public class NueralNet {

	int layers = 4; //Sets number of layers
	int[] nodes = {3, 4, 4, 3}; //Sets size of layers
	float[][] activations = new float[layers][]; //This creates the node activation array
	float[][][] weights;
	
	public void setup() { //Setup needs to be called to actually make the network
		
		//The following section sets up the neural network, creating all nodes and weight arrays. 
		//Node arrays are organized by row and then node number. 
		//Weight arrays are organized by weight row, starting node number, and ending node number, 
		//storing the weight of the "synapse" / neuron connection.
		activations = new float[layers][]; //This creates the node activation array
		for(int i = 0; i < layers; i++) { //Makes the second dimension for individual nodes		
			activations[i] = new float[nodes[i]]; //Makes the second dimension for nodes					
		}
		weights = new float[layers - 1][][]; //Weights are organized by weight layer, starting node, and ending node
		for(int i = 0; i < layers - 1; i++) { //Goes through every weight group
			weights[i] = new float[nodes[i]][]; //Makes the second dimension for starting node
			for(int j = 0; j < nodes[i]; j++) { //Goes through every starting node
				weights[i][j] = new float[nodes[i+1]]; //Makes the third dimension for ending node
			}
		}
		
	}
	
	public float[] think(float[] inputs) { //This is a ton of for loops. Check this a ton to make sure its right.
		for(int i = 0; i < nodes[0]; i++) { //Goes through every node in the first layer
			activations[0][i] = inputs[i];
		}
		for(int i = 1; i < layers - 2; i++) { //Goes through every middle layer
			for(int j = 0; j < nodes[i]; j++) { //Goes through every node in the layer
				for(int k = 0; k < nodes[i-1]; k++) { //Goes through every weight starting node, we already know the weight ending node
					activations[i][j] += (activations[i-1][k] * weights[i-1][k][j]);
					//The above line adds the an input based on it's activation and the connection weight
				}
				activations[i][j] = capped(activations[i][j]); //Caps the node's activation
			}
		for(int j = 0; j < nodes[layers-1]; j++) { //Goes through every node in the last layer
			for(int k = 0; k < nodes[layers-2]; k++) { //Goes through every weight starting node, we already know the weight ending node
				activations[layers-1][j] += (activations[layers-2][k] * weights[layers-2][k][j]);
				//The above line adds the an input based on it's activation and the connection weight
			}
			activations[layers-1][j] = capped(activations[layers-1][j]); //Caps the node's activation
		}
	}
		
		return(activations[layers-1]);
	}
	
	public float capped(float input) { //Activation function. Linear activation capped at -1 and 1. Not entirely sure if this works.
		if (input > 1) { //This function could be worth checking in the case of a broken program
			return((float) (1));
		} else if (input < -1) {
			return((float) (-1));
		} else {
			return(input);
		}
	}

}
