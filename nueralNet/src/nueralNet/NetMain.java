package nueralNet;

import processing.core.PApplet;

public class NetMain {
	
	static boolean shouldKill = false;

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
		net.randomizeWeights(); //If importing weights, disable this command to not get rid of all data
		NetDraw draw = new NetDraw(net, walls, car);
		
		String[] processingArgs = {"nueralNet.NetDraw"};
		PApplet.runSketch(processingArgs, draw);
		
		boolean isRunning = true;
		
		float[] inputs = new float[3];
		
		while(isRunning) { //Main program loop
			inputs[0] = car.carX;
			inputs[1] = car.carY;
			inputs[2] = car.carR;
			outputs = net.think(carView(inputs, walls, net.nodes[0], (float) car.speed * 200));
			car.updatePhysics(outputs);
			net.mutateWeights();  //DEBUGGING CODE     IF NOT RUNNING CORRECTLY CHECK THIS   IT WILL RUIN EVERYTHING /\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
			draw.redraw();
			if(shouldKill) {
				car.respawn();
				shouldKill = false;
			}

		}
				
	}
	
	//The following section is the way the car sees stuff. It shoots out 'rays' of vision and the car gets told how far each ray goes.
	public static float[] carView(float[] inputs, float[][] walls, int rays, float speed) { //inputs = carx, cary, carr   walls = wall coordinates   rays = number of vision rays
		
		float carX = inputs[0], carY = inputs[1], carR = inputs[2];
		
		float[][] rayEnds = new float[rays][];
		
		for(int i = 0; i < rays; i++) { //Make the second dimension of the rayEnds array
			rayEnds[i] = new float[2];
		}
		
		for(int i = 0; i < rays; i++) { //Find coordinates of the end points of vision rays
			rayEnds[i][0] = carX + (float) 1500 * (float)Math.cos(carR - (0.5 * Math.PI) + ((float) i/(rays - 1)) * Math.PI);
			rayEnds[i][1] = carY + (float) 1500 * (float)Math.sin(carR - (0.5 * Math.PI) + ((float) i/(rays - 1)) * Math.PI);
		}
		
		float[] lowestValue = new float[rays]; //Initializes 
		for(int i = 0; i < rays - 1; i++) {
			lowestValue[i] = 1500;
		}
			
		//Find shortest length of line intersections
		for(int i = 0; i < rays - 1; i++) { //Go through every vision ray
			for(int j = 0; j < walls.length; j++) { //Go through every wall
				if(lineIntersection((float) inputs[0], (float) inputs[1],  //Check if there is an intersection
						rayEnds[i][0], rayEnds[i][1], walls[j][0], walls[j][1], walls[j][2], walls[j][3]) != null) {
					float[] intersectCoords = lineIntersection((float) inputs[0], (float) inputs[1], //Assign intersection
						rayEnds[i][0], rayEnds[i][1], walls[j][0], walls[j][1], walls[j][2], walls[j][3]);
					float intersectDist = (float) Math.sqrt(Math.pow(intersectCoords[0] - carX, 2) + Math.pow(intersectCoords[1] - carY, 2));
					if(intersectDist < lowestValue[i]) { //Sees if it is a new shortest distance
						lowestValue[i] = intersectDist;
//						System.out.println(lowestValue[i]);
						if (lowestValue[i] < 20) { //Kill the car if it hits a wall
							shouldKill = true;
						}
					}
				}
			}
		}
		
		//The following section normalizes the outputs and was written by chatgpt
		float min = Float.MAX_VALUE; 
		float max = Float.MIN_VALUE;
	    for (float v : lowestValue) {
	        if (v < min) min = v;
	        if (v > max) max = v;
	    }
	    
	    float[] normalized = new float[lowestValue.length];
	    for (int i = 0; i < lowestValue.length; i++) {
	        if (max == min) {
	            normalized[i] = 0.5f; // Avoid division by zero (all values equal)
	        } else {
	            normalized[i] = (lowestValue[i] - min) / (max - min);
	        }
	    }
	    
	    normalized[normalized.length - 1] = speed;

	    return normalized;
						
	}
	
	public static float[] lineIntersection(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) { 
		//Function for checking the coordinates of the intersections of two lines. this was written by chatgpt
		//i'm not gonna pretend to understand it, you just need to input line 1's coordinates 
		//and line 2's coordinates and it spits out the intersection coords
		
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