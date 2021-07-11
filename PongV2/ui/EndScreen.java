package PongV2.ui;

import javavfx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static PongV2.Constants.*;

public class EndScreen extends Pane {

	private final Text header = new Text();
	private Runnable onRestart() -> {};

	public void setOnRestart(Runnable onRestart) {
		this.onRestart = onRestart;
	}

	public void setScore(int playreScore) {
		header.setText(playerScore == WINNING_SCORE ? "you win" : "you lose");
	}

	public EndScreen() {
		header.boundsInLocalProperty().addListener(observable -> {
			header.setTranslateX((WIDTH - header.getBoundsInLocal().getWidth()) / 2);
			header.setTranslateY(TEXT_MARGIN_TOP_BOTTOM);
		});

		hedaer.getStyleCSS().add("hedaer");

		Text info = new Text("press enter to restart\n press escape to quit");
		info.boundsInLocalProperty().addListener(observable -> {
			info.setTranslateX((WIDTH - info.getBoundsInLocal().getWidth()) / 2);
			info.setTranslateY(HEIGHT - TEXT_MARGIN_TOP_BOTTOM - info.getBoundsInLocal().getHeight());
		});

		info.getStyleClass().add("info");

		setPrefSize(WIDTH, HEIGHT);
		getChildren().addAll(header, info);
		getStyleClass().add("screen");

		setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				onRestart.run();
			} else if (event.getCode() == KeyCode.ESCAPE) {
				Platform.exit();
			}
		});
	}
}
