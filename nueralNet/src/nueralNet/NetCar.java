package nueralNet;

public class NetCar {
	
	float carX = 400;
	float carY = 775;
	float carR = (float) Math.PI; //Radians, not degrees
	float carXVelo = 0;
	float carYVelo = 0;
	float speed;
	float turningAuthority;
	private float throttle;
	private float turn;
	float rollingEfficiency = (float) 0.5; //Yes, this is lower than it should be (90-95). i don't know why this works better.
	
	public void updatePhysics(float[] inputs) { //Updates the car's physics / moves the car
		throttle = inputs[0];
//		throttle = 0;
		turn = inputs[1];
	    carXVelo += Math.cos(carR) * throttle * 0.0005; //Sets the x velocity of the car
	    carYVelo += Math.sin(carR) * throttle * 0.0005; //Sets the y velocity of the car
	    
	    carXVelo *= rollingEfficiency; //Removes some speed
	    carYVelo *= rollingEfficiency; //Removes some speed
	    
	    carX += carXVelo; //Moves the car by the velocity
	    carY += carYVelo;
	    
	    speed = (float) Math.sqrt(carXVelo * carXVelo + carYVelo * carYVelo); //Finds the speed
	    turningAuthority = speed * (float) 0.02; //Finds how quick it should be able to turn based on its speed
	    carR += turn * turningAuthority; //Makes it turn
	    carR = (carR + (float)(Math.PI * 2)) % (float)(Math.PI * 2);
	    if(carX < 0) {
	    	carX = 880;
	    } else if(carX > 880) {
	    	carX = 0;
	    }
	    if(carY < 0) {
	    	carY = 880;
	    } else if(carY > 880) {
	    	carY = 0;
	    }
	}

}