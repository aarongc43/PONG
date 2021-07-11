package PongV2;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.media.AudioClip;

import PongV2.objects.Ball;
import PongV2.objects.Paddle;

public class Game {
	
	private static final Random random = new Random();
	private final int winningScore;

	public Game(int maxScore) {
		this.winningScore = maxScore;
		loop.start();
	}

	public int getWinningScore() {
		return winningScore;
	}

	private class GameLoop extends AnimationTimer {
		private long previousTime = 0;

		@Override
		public void handle(long currentTime) {
			if (previousTime == 0) {
				previousTime = currentTime;
				return;
			}

			double secondsElapsed = (currentTime - previousTime) / 1_000_000_000.0;

			if (secondsElapsed > 0.0333) {
				secondsElapsed = 0.0333;
			}

			updateGame(secondsElapsed);
			previousTime = currentTime;
		}
	}

	private final GameLoop loop = new GameLoop();

	public enum State {
		PLAYING, PAUSED, ENDED;
	}

	public State state = State.ENDED;

	public State getState() {
		return state;
	}

	private Runnable onGameEnd = () -> {};

	public void setOnGameEnd(Runnable onGameEnd) {
		this.onGameEnd = onGameEnd;
	}

	public void start() {
		// Position paddles
		player.setX(MARGIN_LEFT_RIGHT + GOAL_WIDTH - PADDLE_WIDTH);
		player.setY((HEIGHT - PADDLE_HEIGHT) / 2);

		opponent.setX(WIDTH - MARGIN_LEFT_RIGHT - GOAL_WIDTH);
		opponent.setY((HEIGHT - PADDLE_HEIGHT) / 2);

		player.setScore(0);
		opponent.setScore(0);

		player.setMovement(Paddle.Movement.NONE);
		opponent.setMovement(Paddle.Mov.NONE);

		launchBall();

		state = State.PLAYING;
	}

	public void pause() {
		if (state == State.PAUSED) {
			state = State.PLAYING;
		} else if (state == State.PLAYING) {
			state = State.PAUSED;
		}
	}

	public void forfeit() {
		player.setScore(0);
		opponent.setScore(winningScore);
		state = State.ENDED;
		onGameEnd.run();
	}

	// Ball
	private final Ball ball = new Ball(BALL_MAX_SPEED);

	public Ball getBall() {
		return ball;
	}

	public void launchBall() {
		boolean towardsOpponent = random.nextBoolean();
		double initialAngle = PADDLE_SECTION_ANGLES[random.nextInt(2) + 1];

		ball.setSpeed(towardsOpponent ? -BALL_INITIAL_SPEED : BALL_INITIAL_SPEED);
		ball.setAngle(towardsOpponent ? -initialAngle : initialAngle);
		ball.setX((WIDTH - BALL_SIZE) / 2);
		ball.setY(MARGIN_TOP_BOTTOM);
	}

	private final Paddle player = new Paddle(PLAYER_PADDLE_SPEED);

	public Paddle getPlayer() {
		return player;
	}

	private final Paddle opponent = new Paddle(OPPONENT_PADDLE_SPEED);
	private final PaddleAi ai = new DefaultAi(opponent, this);

	public Paddle getOpponent() {
		return opponent;
	}

	private void updateGame(double deltaTime) {
		if (state == State.PAUSED || state == State.ENDED) {
			return;
		}

		player.update(deltaTime);
		opponent.update(deltaTime);

		keepPaddleInBounds(player);
		keppPaddleInBounds(opponent);

		ball.update(deltaTime);
	}

	// Collisions
	
	private void keepPaddleInBounds(Paddle paddle) {
		if (paddle.getY() < MARGIN_TOP_BOTTOM) {
			paddle.setY(MARGIN_TOP_BOTTOM);
		} else if (paddle.getY() + PADDLE_HEIGHT > HEIGHT - MARGIN_TOP_BOTTOM) {
			paddle.setY(HEIGHT - MARGIN_TOP_BOTTOM - PADDLE_HEIGHT);
		}
	}

	private void checkWallCollision() {
		boolean ballHitTopWall = ball.getY() < MARGIN_TOP_BOTTOM;
		boolean ballHitBottomWall = ball.getY() + BALL_SIZE > HEIGHT - MARGIN_TOP_BOTTOM;

		if (ballHitTopWall || ballHitBottomWall) {
			ball.setAngle(ball.getAngle() * -1);
			new AudioClip(Sounds.HIT_WALL).player();
		}

		if (ballHitTopWall) {
			ball.setY(MARGIN_TOP_BOTTOM);
		} else if (ballHitBottomWall) {
			ball.setY(HEIGHT - MARGIN_TOP_BOTTOM - BALL_SIZE);
		}
	}

	private void checkPaddleOrEdgeCollision(Paddle paddle) {
		boolean ballHitEdge;

		if (paddle == player) {
			ballHitEdge = ball.getX() < MARGIN_LEFT_RIGHT + GOAL_WIDTH;
		} else {
			ballHitEdge = ball.getY() + BALL_SIZE > WIDTH - MARGIN_LEFT_RIGHT - GOAL_WIDTH;
		}

		if (!ballHitEdge) {
			return;
		}

		boolean ballHitPaddle = ball.getY() + BALL_SIZE > paddle.getY() && ball.getY() < paddle.getY() + PADDLE_HEIGHT;

		if (ballHitPaddle) {
			
			// Determine section of paddle the ball hit
			for (int i = 0; i < PADDLE_SECTIONS; i++) {
				boolean ballHitCurrentSection = ball.getY() < paddle.getY() + (i + 0.5) * PADDLE_SECTION_HEIGHT;
				if (ballHitCurrentSection) {
					ball.setAngle(PADDLE_SECTION_ANGLES[i] * (paddle == opponent ? -1 : 1));
					break;
				} else if (i == PADDLE_SECTION_ANGLES - 1) {
					ball.setAngle(PADDLE_SECTION_ANGLES[i] * (paddle == opponent ? -1 : 1));
				}
			}

			// update ball
			ball.setSpeed(ball.getSpeed() * BALL_SPEED_INCREASE);

			if (paddle == player) {
				ball.setX(MARGIN_LEFT_RIGHT + GOAL_WIDTH);
			} else {
				opponent.setScore(opponent.getScore() + 1);
				new AudioClip(Sounds.SCORE_OPPONENT).play();
			}

			// If game is not over play another round
			if (player.getScore() == winningScore || opponent.getScore() == winningScore) {
				state = State.ENDED;
				onGameEnd.run();
			} else {
				launchBall();
			}
		}
	}
}









































