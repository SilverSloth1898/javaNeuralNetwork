package nueralNet;

import processing.core.PApplet;

public class NetMain {

	public static void main(String[] args) {
		
		float[][] walls = { //This section is used to make the walls of the race course. Yes its tedious to make walls. No i'm not making it better.
				{0, 0, 880, 0},
				{0, 0, 0, 880},
				{0, 880, 880, 880},
				{880, 0, 880, 880},
				{440, 440, 880, 440},
				{220, 220, 220, 660},
				{220, 220, 660, 220},
				{220, 660, 660, 660}
		};
		float[] outputs = new float[2];
		
		NueralNet net = new NueralNet();
		NetCar car = new NetCar();
		net.setup();
		net.randomizeWeights();
		NetDraw draw = new NetDraw(net, walls, car);
		
		String[] processingArgs = {"nueralNet.NetDraw"};
		PApplet.runSketch(processingArgs, draw);
		
		boolean isRunning = true;
		
		float[] inputs = {1, -1, 0, 1, -1, 0, 1, -1, 0, 1};
		
		while(isRunning) { //Main program loop
			outputs = net.think(inputs);
			car.updatePhysics(outputs);
			net.mutateWeights();  //DEBUGGING CODE     IF NOT RUNNING CORRECTLY CHECK THIS   IT WILL RUIN EVERYTHING /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
			draw.redraw();

		}
				
	}
	
	public float[] carView(float[] inputs, float[][] walls, int rays) { //inputs = carx, cary, carr   walls = wall coordinates   rays = number of vision rays
		
		float carX = inputs[0], carY = inputs[1], carR = inputs[2];
		
		float[][] rayEnds = new float[rays][];
		
		for(int i = 0; i < rays; i++) { //Make the second dimension of the rayEnds array
			rayEnds[i] = new float[rays];
		}
		
		for(int i = 0; i < rays; i++) {
			rayEnds[i][0] = carX + (float) 1500 * (float)Math.cos(carR - (Math.PI / 2) + (i/rays));
//			rayEnds
		}
		
		//Need to do the math to find the end coordinates of every vision ray
		
		return null;
		
	}
	
	public float[] lineIntersection(int[] points) { //Function for checking the coordinates of the intersections of two lines. this was written by chatgpt
		//i'm not gonna pretend to understand it, you just need to input line 1's coordinates and line 2's coordinates and it spits out the intersection coords
		
		float x1 = points[0], y1 = points[1], x2 = points[2], y2 = points[3], x3 = points[4], y3 = points[5], x4 = points[6], y4 = points[7];
		float denom = ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
		if(denom == 0) {
			return null;
		} else {
			float t = (((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denom);
			float u = (((x1 - x3) * (y1 - y2) - (y1 - y3) * (x1 - x2)) / denom);
			
		    if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
		        float intersectX = x1 + t * (x2 - x1);
		        float intersectY = y1 + t * (y2 - y1);
		        return new float[]{intersectX, intersectY};
		    }
		}
		return null;
	}

}
