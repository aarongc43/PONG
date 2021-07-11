package PongV2.objects;

public class Ball extends GameObject {
	
	private final double maxSpeed;
	private double speed = 0;

	public Ball(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	//determine ball angle
	private double angle = 0;

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		if (speed >= 0) {
			this.speed = Math.min(speed, maxSpeed);
		} else {
			this.speed = Math.max(speed, -maxSpeed);
		}
	}

	//update ball
	public void update(double deltaTime) {
		double distanceTraveled = speed * deltaTime;
		double deltaX = distanceTraveled * Math.cos(angle);
		double deltaY = distanceTraveled * Math.sin(angle);
		
		setX(getX() + deltaX);
		setY(getY() + deltaY);
	}

}
