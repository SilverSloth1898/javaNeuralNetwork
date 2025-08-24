package nueralNet;

import processing.core.PApplet;

public class NetMain {

	public static void main(String[] args) {
		
		int[][] walls = { //This section is used to make the walls of the race course. Yes its tedious to make walls. No i'm not making it better.
				{0, 0, 880, 0},
				{0, 0, 0, 880},
				{0, 880, 880, 880},
				{880, 0, 880, 880},
				{440, 440, 880, 440},
				{220, 220, 220, 660},
				{220, 220, 660, 220},
				{220, 660, 660, 660}
		};
		
		NueralNet net = new NueralNet();
		net.setup();
		net.randomizeWeights();
		NetDraw draw = new NetDraw(net, walls);
		
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
