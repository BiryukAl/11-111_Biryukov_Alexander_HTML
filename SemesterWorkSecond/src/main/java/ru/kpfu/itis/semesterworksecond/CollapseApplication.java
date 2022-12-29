package ru.kpfu.itis.semesterworksecond;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.kpfu.itis.semesterworksecond.main.controller.GameController;
import ru.kpfu.itis.semesterworksecond.main.controller.MainController;

import java.io.IOException;
import java.util.Objects;


public class CollapseApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(CollapseApplication.class.getResource("view/main.fxml"));

        /*Работает со старой версеей контроллера */
//        fxmlLoader.setController(new MainController(stage));
        fxmlLoader.setController(new GameController(stage));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1600, 758 + 27);

        stage.setResizable(false);
        stage.setTitle("Collapse");
        stage.getIcons().add(getImageForResource("assets/icon.png"));

        stage.setScene(scene);
        stage.show();

    }

    private  Image getImageForResource(String path) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    }

    public static void main(String[] args) {
        launch(args);
    }
}