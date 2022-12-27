package ru.kpfu.itis.semesterworksecond.main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ru.kpfu.itis.semesterworksecond.logic.CoreGame;
import ru.kpfu.itis.semesterworksecond.logic.model.Holder;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    CoreGame coreGame = new CoreGame();
    Stage primaryStage;
    @FXML
    AnchorPane anchorPane;
    @FXML
    GridPane playingArea;

    Holder nextClient = Holder.FIRST;

    @FXML
    private Button start;

    @FXML
    void clickStart(MouseEvent event) {
        this.coreGame = new CoreGame();
        nextClient = Holder.FIRST;
        GridPane newGridPane = coreGame.draw();
        newGridPane.setId("playingArea");
        newGridPane.setOnMouseClicked(mouseEvent -> {
            clickPlayArea(event);
        });

        this.anchorPane.getChildren().add(newGridPane);
    }


    public MainController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void clickPlayArea(MouseEvent event) {
        System.out.println(event);
        try{
            Button b =  (Button) event.getSource();

            if (Objects.equals(b.getId(), "start")) {
                GridPane newGridPane = coreGame.draw();

                anchorPane.getChildren().remove(playingArea);

                newGridPane.setId("playingArea");
                newGridPane.setOnMouseClicked(mouseEvent -> {
                    clickPlayArea(mouseEvent);
                });
                anchorPane.getChildren().add(newGridPane);
                return;
            }
        } catch (ClassCastException ignored){}


        Node clickedNode = event.getPickResult().getIntersectedNode();

        if (clickedNode != playingArea) {
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);

            if (colIndex == null) colIndex = 0;
            if (rowIndex == null) rowIndex = 0;

            System.out.println("Mouse clicked cell col: " + colIndex + " and row: " + rowIndex);


            if (!coreGame.step(colIndex, rowIndex, nextClient)) {
                return;
            }
            nextClient = nextClient == Holder.FIRST ? Holder.SECOND : Holder.FIRST;

            GridPane newGridPane = coreGame.draw();

            anchorPane.getChildren().remove(playingArea);

            newGridPane.setId("playingArea");
            newGridPane.setOnMouseClicked(mouseEvent -> {
                clickPlayArea(mouseEvent);
            });
            anchorPane.getChildren().add(newGridPane);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
    }

        /*Alert h = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        h.setTitle("Test");
        h.setHeaderText("Testing");
        h.showAndWait();*/

/*    void addImageForArea(int x, int y, String path) {
        ImageView v = new ImageView(MyUtils.getImageForResource(path));

        String pathPoint = "assets/point";
        if (path.contains(pathPoint)) {
            v.setFitWidth(79);
            v.setFitHeight(80);
        }
        playingArea.add(v, x, y);
    }*/


}
