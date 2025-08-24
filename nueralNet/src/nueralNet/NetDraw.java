package nueralNet;

import processing.core.*;

public class NetDraw extends PApplet{

	private NueralNet net;
	private int[][] walls;
	private NetCar car;
	
	public NetDraw(NueralNet Net, int[][] wallArray, NetCar Car) {
		this.net = Net; //To access data from the network, do net.(insert variable name)
		this.walls = wallArray;
		this.car = Car;
	}
	
	public void settings() {
		size(1520, 880);
		noLoop();
	}
	
	public void setup() {
		frameRate(Float.MAX_VALUE);
	} //Variables have to be initialized outside of functions in the class
	
	public void draw() {
		background(150);
		for(int i = 0; i < walls.length; i++) { //Draw track walls
			stroke(0);
			strokeWeight(20);
			line(walls[i][0], walls[i][1], walls[i][2], walls[i][3]);
		}
		fill(220);
		noStroke();
		rectMode(CORNER);
		rect(height, 0, width - height, height); //Makes the box for the network visualization
		for(int i = 0; i < net.nodes.length-1; i++) { //The following section draws the synapses of the net
			for(int j = 0; j < net.nodes[i]; j++) {
				for(int k = 0; k < net.nodes[i+1]; k++) {
					strokeWeight(abs(net.weights[i][j][k]) * height / 100);
					if(net.weights[i][j][k] <= 0) {
						stroke(255, 0, 0);
					} else {
						stroke(0, 255, 0);
					}
					//yes, i know this line is a mess, and no i'm not going to fix it
					line(height + ((width-height) / (net.nodes.length + 1)) * (i+1), (height / (net.nodes[i] + 1)) * (j+1), 
						 height + ((width-height) / (net.nodes.length + 1)) * (i+2), (height / (net.nodes[i+1] + 1)) * (k+1));
				}
			}
		}
		ellipseMode(CENTER); //The following section draws the neurons in the display
		strokeWeight(4); 
		stroke(0);
		for(int i = 0; i < net.nodes.length; i++) { //Goes through every network layer
			for(int j = 0; j < net.nodes[i]; j++) { //Goes through every node activation in the layer
				fill((net.activations[i][j] + 1) * 127);
//				fill(120);
				ellipse(height + ((width-height) / (net.nodes.length + 1)) * (i + 1), (height / (net.nodes[i] + 1)) * (j + 1), height / 17, height / 17);
			}
		}
		rectMode(CENTER); //The following section draws the car
		noStroke();
		fill(220, 0, 0);
		pushMatrix();
		translate(car.carX, car.carY);
		rotate(car.carR);
		rect(0, 0, 30, 60);
		popMatrix();
	}

}