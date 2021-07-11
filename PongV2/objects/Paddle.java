package PongV2.objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Paddle extends GameObject {
	
	private final double speed;
	private final IntegerProperty score = new SimpleIntegerProperty(0);

	public Paddle(double speed) {
		this.speed = speed;
	}

	public enum Movement {
		UP, DOWN, NONE;
	}

	private Movement movement = Movement.NONE;

	public Movement getMovement() {
		return movement;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}


	public int getScore() {
		return score.get();
	}

	public void setScore(int score) {
		this.score.set(score);
	}

	public IntegerProperty scoreProperty() {
		return score;
	}

	// Updating Paddle
	public void update(double deltaTime) {

		if (movement == Movement.DOWN) {
			setY(getY() + speed * deltaTime);
		} else if (movement == Movement.UP) {
			setY(getY() - speed * deltaTime);
		}
	}
}
