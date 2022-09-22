import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.geometry.Pos;
import java.util.Random;

public class DessertGame extends Application {

    private int score = 0;

    @Override
    public void start(final Stage stage) {

        // Step 3 & 4
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 640, 480);
        stage.setTitle("Dessert in the Desert JavaFX Game");

        // Step 5
        Label scoreLabel = new Label("Score: 0");
        borderPane.setTop(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.TOP_LEFT);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            Platform.exit();
        });
        borderPane.setBottom(exitButton);
        BorderPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);

        // Step 6
        Pane pane = new Pane();
        borderPane.setCenter(pane);
        BorderPane.setAlignment(pane, Pos.CENTER);

        // TODO: Step 7-10
        Button Dessert = new Button("Dessert");
        Button Desert = new Button("Desert");
        Button Deserta = new Button("Desert");
        Button Desertb = new Button("Desert");
        Button Desertc = new Button("Desert");
        Button Desertd = new Button("Desert");
        Button Deserte = new Button("Desert");
        Button Desertf = new Button("Desert");

        pane.getChildren().addAll(Dessert, Desert, Deserta, Desertb, Desertc, Desertd, Deserte, Desertf);
        Button[] buttons = new Button[] {
                Dessert, Desert, Deserta, Desertb, Desertc, Desertd, Deserte, Deserte
        };

        Random rand = new Random();
        randomizeButtonPositions(rand, buttons);
        exitButton.requestFocus();

        Dessert.setOnAction(event -> {
            score++;
            scoreLabel.setText("Score: " + score);
            exitButton.requestFocus();
            randomizeButtonPositions(rand, buttons);
        });

        Desert.setOnAction(event -> {
            score--;
            scoreLabel.setText("Score: " + score);
            exitButton.requestFocus();
            randomizeButtonPositions(rand, buttons);
        });

        Deserta.setOnAction(event -> {
            score--;
            scoreLabel.setText("Score: " + score);
            exitButton.requestFocus();
            randomizeButtonPositions(rand, buttons);
        });

        Desertb.setOnAction(event -> {
            score--;
            scoreLabel.setText("Score: " + score);
            exitButton.requestFocus();
            randomizeButtonPositions(rand, buttons);
        });

        Desertc.setOnAction(event -> {
            score--;
            scoreLabel.setText("Score: " + score);
            exitButton.requestFocus();
            randomizeButtonPositions(rand, buttons);
        });

        Desertd.setOnAction(event -> {
            score--;
            scoreLabel.setText("Score: " + score);
            exitButton.requestFocus();
            randomizeButtonPositions(rand, buttons);
        });

        Deserte.setOnAction(event -> {
            score--;
            scoreLabel.setText("Score: " + score);
            exitButton.requestFocus();
            randomizeButtonPositions(rand, buttons);
        });

        Desertf.setOnAction(event -> {
            score--;
            scoreLabel.setText("Score: " + score);
            exitButton.requestFocus();
            randomizeButtonPositions(rand, buttons);
        });

        stage.setScene(scene);
        stage.show();
    }

    private void randomizeButtonPositions(Random rand, Button[] buttons) {
        for (int i = 0; i <buttons.length; i++){
            buttons[i].setLayoutX(rand.nextInt(600));
            buttons[i].setLayoutY(rand.nextInt(400));
        }
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
