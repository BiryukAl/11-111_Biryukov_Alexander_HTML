package ru.kpfu.itis.semesterworksecond.main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectionPageController {

    Stage primaryStage;

    @FXML
    private Button createServer;

    @FXML
    private TextField host;

    @FXML
    private Button joinGame;

    @FXML
    private TextField port;

    @FXML
    private Label serverStatus;


    public ConnectionPageController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }



}
