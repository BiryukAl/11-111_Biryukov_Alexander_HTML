package ru.kpfu.itis.semesterworksecond.main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.kpfu.itis.semesterworksecond.myExceptions.ClientException;
import ru.kpfu.itis.semesterworksecond.server.Client;
import ru.kpfu.itis.semesterworksecond.server.GameServer;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectionPageController implements Initializable {

    Stage primaryStage;
    GameServer server;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        createServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (server != null) {
                    server.stop();
                    server = null;
                    serverStatus.setText("Сервер отключен");
                    createServer.setText("Создать сервер");
                    createServer.setDisable(true);
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            createServer.setDisable(false);
                        }
                    }, 2000);
                    return;
                }
                if (!port.getText().matches("\\d{3,}")) {
                    return;
                }

                server = new GameServer(Integer.valueOf(port.getText()));
                server.start();
                serverStatus.setText("Сервер запущен на порту " + port.getText());
                createServer.setText("Отключить сервер");
            }
        });
        joinGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!port.getText().matches("\\d{3,}")) {
                    return;
                }

                Client client = null;
                try {
                    client = new Client(host.getText(), Integer.valueOf(port.getText()));
                } catch (ConnectException e) {
                    System.out.println("Server connection error!");
                    Alert a = new Alert(Alert.AlertType.ERROR, "Server connection error", ButtonType.OK);
                    a.setTitle("Error");
                    a.setHeaderText("Server not found!");
                    a.showAndWait();
                } catch (IOException e) {
                    throw new ClientException(e);
                }

                assert client != null;
                if (client.checkConnect()) {
                    client.startGame(primaryStage);
                } else {
                    Alert al = new Alert(Alert.AlertType.WARNING, "Full Server!", ButtonType.OK);
                    al.setHeaderText("Error");
                    al.setTitle("Error");
                    al.showAndWait();
                }


            }
        });


    }
}
