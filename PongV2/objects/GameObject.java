package PongV2.objects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class GameObject {

	private final DoubleProperty x = new SimpleDoubleProperty(0);
	private final DoubleProperty y = new SimpleDoubleProperty(0);

	public double getX() {
		return x.get();
	}

	public void setX(double x) {
		this.x.set(x);
	}

	public DoubleProperty xProperty() {
		return x;
	}

	public double getY() {
		return y.get();
	}

	public void setY(double y) {
		this.y.set(y);
	}

	public DoubleProperty yProperty() {
		return y;
	}

	public abstract void update(double deltaTime);
}
