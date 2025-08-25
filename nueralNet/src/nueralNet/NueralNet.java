package nueralNet;

public class NueralNet {

	int[] nodes = {10, 8, 6, 2}; //Sets size of layers
	float[][] activations = new float[nodes.length][]; //This creates the node activation array
	float[][][] weights;
	
	public void setup() { //Setup needs to be called to actually make the network
		
		//The following section sets up the neural network, creating all nodes and weight arrays. 
		//Node arrays are organized by row and then node number. 
		//Weight arrays are organized by weight row, starting node number, and ending node number, 
		//storing the weight of the "synapse" / neuron connection.
		activations = new float[nodes.length][]; //This creates the node activation array
		for(int i = 0; i < nodes.length; i++) { //Makes the second dimension for individual nodes		
			activations[i] = new float[nodes[i]]; //Makes the second dimension for nodes	
		}
		weights = new float[nodes.length - 1][][]; //Weights are organized by weight layer, starting node, and ending node
		for(int i = 0; i < nodes.length - 1; i++) { //Goes through every weight group
		    weights[i] = new float[nodes[i]][nodes[i+1]]; //Makes the second dimension for starting node
		}
		
	}
	
	public float[] think(float[] inputs) { //This is a ton of for loops. Check this a ton to make sure its right.
		for(int i = 0; i < nodes[0]; i++) { //Goes through every node in the first layer
			activations[0][i] = inputs[i]; //Sets first layer to the inputs
		}
		for(int i = 1; i < nodes.length ; i++) { //Goes through every middle layer
			for(int j = 0; j < nodes[i]; j++) { //Goes through every node in the layer
				activations[i][j] = 0;
				for(int k = 0; k < nodes[i-1]; k++) { //Goes through every weight starting node, we already know the weight ending node
					activations[i][j] += (activations[i-1][k] * weights[i-1][k][j]); //Adds the connected weight's output
					activations[i][j] = capped(activations[i][j]); //Caps the node's activation
				}
			}
		}
		return(activations[nodes.length-1]);
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
	
	public void randomizeWeights() { //Starts the program off with entirely random weights
		for(int i = 0; i < nodes.length - 1; i++) { //Goes through every starting node layer
			for(int j = 0; j < nodes[i]; j++) { //Goes through every starting node
				for(int k = 0; k < nodes[i+1]; k++) { //Goes through every ending node
					weights[i][j][k] = (float)(Math.random() * 2 - 1);
				}
			}
		}
	}
	
	public void mutateWeights() { //Should not be used that much, just kind of keeps on changing around the weights
		for(int i = 0; i < nodes.length - 1; i++) { //Goes through every starting node layer
			for(int j = 0; j < nodes[i]; j++) { //Goes through every starting node
				for(int k = 0; k < nodes[i+1]; k++) { //Goes through every ending node
					weights[i][j][k] += (float)((Math.random() * 2 - 1) / 5000);
					weights[i][j][k] = capped(weights[i][j][k]);
				}
			}
		}
	}
	
}