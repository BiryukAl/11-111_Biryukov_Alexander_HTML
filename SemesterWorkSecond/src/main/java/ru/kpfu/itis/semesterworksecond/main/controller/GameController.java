package ru.kpfu.itis.semesterworksecond.main.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.kpfu.itis.semesterworksecond.logic.CoreGame;
import ru.kpfu.itis.semesterworksecond.logic.model.Holder;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    Stage primaryStage;

    CoreGame coreGame;

    @FXML
    AnchorPane anchorPane;

    Holder nextStep = Holder.FIRST;

    @FXML
    GridPane playingArea;

    @FXML
    private Button restart;

    public GameController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coreGame = new CoreGame();
        GridPane newGridPane = coreGame.draw();
        newGridPane.setId("playingArea");

        EventHandler<MouseEvent> eventPlayingArea = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node clickedNode = event.getPickResult().getIntersectedNode();

                if (clickedNode != newGridPane) {
                    Integer colIndex = GridPane.getColumnIndex(clickedNode);
                    Integer rowIndex = GridPane.getRowIndex(clickedNode);

                    if (colIndex == null) colIndex = 0;
                    if (rowIndex == null) rowIndex = 0;

                    System.out.println("Mouse clicked cell col: " + colIndex + " and row: " + rowIndex);


                    if (!coreGame.step(colIndex, rowIndex, nextStep)) {
                        return;
                    }
                    nextStep = nextStep == Holder.FIRST ? Holder.SECOND : Holder.FIRST;

                    GridPane newGridPane = coreGame.draw1().getKey();

                    Holder gameOver = coreGame.draw1().getValue();


                    anchorPane.getChildren().remove(playingArea);

                    newGridPane.setId("playingArea");

                    newGridPane.setOnMouseClicked(this);

                    anchorPane.getChildren().add(newGridPane);

                    if (gameOver != null) {
                        Label winnerText = new Label();
                        winnerText.setAlignment(Pos.CENTER);
                        winnerText.setContentDisplay(ContentDisplay.CENTER);
                        winnerText.setLayoutX(494);
                        winnerText.setLayoutY(185);
                        winnerText.setFont(new Font(96.0));
                        winnerText.setTextFill(Color.web("#FFFFFF"));
                        winnerText.setText("Winner " + (gameOver == Holder.FIRST ? "Red" : "Blue") + "!!!");

                        anchorPane.getChildren().add(winnerText);
                    }
                }
            }
        };


        newGridPane.setOnMouseClicked(eventPlayingArea);
        anchorPane.getChildren().add(newGridPane);


        restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Click for button[Start]");
                coreGame = new CoreGame();
                nextStep = Holder.FIRST;
                GridPane newGridPane = coreGame.draw();
                newGridPane.setId("playingArea");
                newGridPane.setOnMouseClicked(eventPlayingArea);

                anchorPane.getChildren().remove(playingArea);
                anchorPane.getChildren().add(newGridPane);
            }
        });
    }


}
