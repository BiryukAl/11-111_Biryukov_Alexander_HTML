package ru.kpfu.itis.semesterworksecond.utils;

import javafx.scene.image.Image;
import ru.kpfu.itis.semesterworksecond.CollapseApplication;

import java.util.Objects;

public class MyUtils {

    public static Image getImageForResource(String path) {
        return new Image(Objects.requireNonNull(CollapseApplication.class.getResourceAsStream(path)));
    }
}
