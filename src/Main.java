import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;

public class Main extends Application {

    // Constants
    private static final int width =  1000;
    private static final int height = 1000;
    private static final double BALL_RADIUS = 15;

    private int PLAYER_WIDTH = 15;
    private int PLAYER_HEIGHT = 100;

    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private double ballXPos = width  / 2;
    private double ballYPos = height / 2;

    private double playerOneXPos = 15; 
    private double playerOneYPos = height / 2;
    private double playerTwoXPos = width - (PLAYER_WIDTH + 15);
    private double playerTwoYPos = height / 2; 

    private int scoreP1;
    private int scoreP2;

    private boolean gameStarted = false;

    public void start(Stage stage) throws Exception {

        Group root = new Group();
        Scene theScene = new Scene(root);
        Canvas canvas = new Canvas(width, height);
        stage.setScene(new Scene(new StackPane(canvas)));

        Image icon = new Image("pong_logo.jpeg");
        stage.setTitle("PONG");
        stage.getIcons().add(icon);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Animation
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(16), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        canvas.setFocusTraversable(true);

        canvas.setOnKeyPressed(e -> {
            keyPressed(e);
        });

        canvas.setOnKeyReleased(e -> {
            keyPressed(e);
        });

        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Aerologica", 42));

        if (gameStarted) {
            // set ball movement
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;
            gc.fillOval(ballXPos, ballYPos, BALL_RADIUS, BALL_RADIUS);

        } else {

            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("PRESS SPACE\n TO START!", width / 2, height / 2);

            ballXPos = width  / 2;
            ballYPos = height / 2;
            ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
        }

        // Keeps the ball in the court
        if (ballYPos > height || ballYPos < 0) {
            ballYSpeed *=-1;
        }

        // TODO: playerTwo gets a point
        if (ballXPos < playerOneXPos + (PLAYER_WIDTH - 15)) {
            scoreP2++;
            gameStarted = false;
        }

        // playerOne gets a point
        if (ballXPos > playerTwoXPos + (PLAYER_WIDTH + 15)) {
            scoreP1++;
            gameStarted = false;
        }

        // increase speed after the ball hits the player
        if (((ballXPos + BALL_RADIUS > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT)
                || ((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
            ballYSpeed += 1 * Math.cos(ballYSpeed);
            ballXSpeed += 1 * Math.sin(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
                }

        // TODO: display score
        gc.fillText(scoreP1 + "\t" + scoreP2, width / 2, 100);

        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public void keyPressed (KeyEvent e) {
        ArrayList<String> input = new ArrayList<>();

        String code = e.getCode().toString();

        if (!input.contains(code)) {
            input.add(code);
            if (input.get(0).equals("SPACE")) {
                gameStarted = true;
            }
        }

        if (!input.contains(code)) {
            input.add(code);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}



































