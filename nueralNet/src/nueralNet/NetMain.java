package nueralNet;

import processing.core.PApplet;

public class NetMain {

	public static void main(String[] args) {
		NueralNet net = new NueralNet();
		net.setup();
		net.randomizeWeights();
		NetDraw draw = new NetDraw(net);
		
		String[] processingArgs = {"nueralNet.NetDraw"};
		PApplet.runSketch(processingArgs, draw);
		
		boolean isRunning = true;
		
		int input1 = 1;
		int input2 = -1;
		int input3 = 1;
		float[] inputs = {input1, input2, input3};
		
		while(isRunning) {
			float[] test = net.think(inputs);
			net.mutateWeights();  //DEBUGGING CODE     IF NOT RUNNING CORRECTLY CHECK THIS   IT WILL RUIN EVERYTHING /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
			draw.redraw();


		}
		

	}

}
