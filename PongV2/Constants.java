package PongV2;

public class Constants {

	private Constants() {}

	// Game size
	public static final double WIDTH = 1280;
	public static final double HEIGHT = 720;

	//Edges of the court/window
	public static final double MARGIN_TOP_BOTTOM = 5;
	public static final double MARGIN_LEFT_RIGHT = 5;

	// goal area
	public static final double GOAL_WIDTH = 20;

	public static final double TEXT_MARGIN_TOP_BOTTOM = 30;

	//size of the area between the player and opponent
	public static final double SCORE_SPACING = 80;

	// Ball
	public static final double BALL_SIZE = 15;
	public static final double BALL_INITIAL_SPEED = -200;
	public static final double BALL_SPEED_INCREASE = -1.25;
	public static final double BALL_MAX_SPEED = 600;

	// Paddle
	public static final double PLAYER_PADDLE_SPEED = 300;
	public static final double OPPONENT_PADDLE_SPEED = 300;
	public static final double PADDLE_WIDTH = 15;
	public static final int PADDLE_SECTIONS = 8;
	public static final double PADDLE_SECTION_HEIGHT = BALL_SIZE;
	public static final double PADDLE_HEIGHT = PADDLE_SECTIONS * PADDLE_SECTION_HEIGHT;
	
	// The paddle sections have angles related to them
	public static final double[] PADDLE_SECTION_ANGLES = new double[] {
		degreesToRadians(-60),
		degreesToRadians(-40),
		degreesToRadians(-20),
		degreesToRadians(0),
		degreesToRadians(0),
		degreesToRadians(20),
		degreesToRadians(40),
		degreesToRadians(60)
	};

	public static final int WINNING_SCORE = 10;

	public static double degreesToRadians (double degrees) {
		return degrees * Math.PI / 180;
	}
}
