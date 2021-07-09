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

    private boolean gameStarted = false;

    public void start(Stage stage) throws Exception {

        Image icon = new Image("pong_logo.jpeg");
        stage.setTitle("PONG");
        stage.getIcons().add(icon);

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Animation
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(e -> {
            keyPressed(e);
        });

        stage.setScene(new Scene(new StackPane(canvas)));
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

        gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    public void keyPressed (KeyEvent e) {
        if (e.getCode() == KeyCode.SPACE) {
            gameStarted = true;
        }

        if (e.getCode() == KeyCode.W) {
            playerOneYPos += -50;
        }

        if (e.getCode() == KeyCode.S) {
            playerOneYPos += 50;
        }

        if (e.getCode() == KeyCode.UP) {
            playerTwoYPos += -50;
        }

        if (e.getCode() == KeyCode.DOWN) {
            playerTwoYPos += 50;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}



































