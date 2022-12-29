module ru.kpfu.itis.semesterworksecond {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires org.apache.commons.lang3;


    opens ru.kpfu.itis.semesterworksecond to javafx.fxml;
    exports ru.kpfu.itis.semesterworksecond;
    opens ru.kpfu.itis.semesterworksecond.main.controller to javafx.fxml;


    exports ru.kpfu.itis.semesterworksecond.main.controller;
    exports ru.kpfu.itis.semesterworksecond.main;
    opens ru.kpfu.itis.semesterworksecond.main to javafx.fxml;
}