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
import javafx.util.Duration;

public class Pong extends Application {
    
    private static final int width = 1000;
    private static final int height = 1000;
    private static final int PLAYER_HEIGHT = 100;
    private static final int PLAYER_WIDTH = 15;
    private static final double BALL_RADIUS = 15;
    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    private double playerOneYPos = (height / 2) - PLAYER_HEIGHT;
    private double playerTwoYPos = (height / 2) - PLAYER_HEIGHT;
    private double ballXPos = width  / 2;
    private double ballYPos = height / 2;
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private boolean gameStarted;
    private int playerOneXPos = 15;
    private double playerTwoXPos = width - PLAYER_WIDTH;

    public void start(Stage stage) throws Exception {
        Image icon = new Image("pong_logo.jpeg");
        stage.setTitle("PONG");
        stage.getIcons().add(icon);

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Animation
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }

    private void run(GraphicsContext gc) {
        // set graphics
        // set background color
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        // set text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Faster One", 70));

        if (gameStarted) {
            // set ball movement
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;
            
            //ai();
            if (ballXPos < width - width / 4) {
                playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
            } else {
                playerTwoYPos = ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2
                    ?playerTwoYPos += 1:playerTwoYPos - 1;
            }

            //draw ball
            gc.fillOval(ballXPos, ballYPos, BALL_RADIUS, BALL_RADIUS);

        } else {
            // set start text
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Press Space\n To Start!", width / 2, height / 2);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}









































































