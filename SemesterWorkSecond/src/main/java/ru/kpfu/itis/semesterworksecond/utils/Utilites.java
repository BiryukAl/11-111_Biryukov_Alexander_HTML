package ru.kpfu.itis.semesterworksecond.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import ru.kpfu.itis.semesterworksecond.logic.model.Holder;
import ru.kpfu.itis.semesterworksecond.logic.model.Point;

import java.io.File;

import static ru.kpfu.itis.semesterworksecond.utils.MyUtils.getImageForResource;

public class Utilites {


    public static Image getImageActivePoint(Point p, Holder holder) {

        if (p.getWeight() == 0 || p.getHolder() == null) {
            return getImageForResource("assets" + File.separator + "background_point.png");
        }


        String stringBuilder = "assets" + File.separator + "point" +
                p.getWeight() + "_" +
                switch (p.getHolder()) {
                    case FIRST -> "blue";
                    case SECOND -> "red";
                } + (p.getHolder() == holder ? "_activ" : "") +
                "_background.png";
        return getImageForResource(stringBuilder);
    }


    public static GridPane drawActivePoint(Point[][] battleGrid, Holder holder) {
        GridPane newGridPane = new GridPane();


        //Нужно подгонять поле под себя
        if (battleGrid.length == 8 && battleGrid[0].length == 10) {
            //Размеры самого поля
            newGridPane.setLayoutX(401.0);
            newGridPane.setLayoutY(45.0);

            //Размеры растояния между столбцами
            newGridPane.setHgap(2);
            newGridPane.setVgap(2);
        }


        for (int r = 0; r < battleGrid.length; r++) {
            for (int c = 0; c < battleGrid[r].length; c++) {
                Image img = Utilites.getImageActivePoint(battleGrid[r][c], holder);
                ImageView imgView = new ImageView(img);
                //Размеры ImageView
                imgView.setFitWidth(78);
                imgView.setFitHeight(80);

                newGridPane.add(imgView, c, r);
            }
        }

        return newGridPane;
    }




    public static Pair<GridPane, Holder> drawGridPainAndGameOver(Point[][] battleGrid, Holder holder) {
        GridPane newGridPane = new GridPane();

        boolean isExistPointFirst = false;
        boolean isExistPointSecond = false;



        //Нужно подгонять поле под себя
        if (battleGrid.length == 8 && battleGrid[0].length == 10) {
            //Размеры самого поля
            newGridPane.setLayoutX(401.0);
            newGridPane.setLayoutY(45.0);

            //Размеры растояния между столбцами
            newGridPane.setHgap(2);
            newGridPane.setVgap(2);


        }


        for (int r = 0; r < battleGrid.length; r++) {
            for (int c = 0; c < battleGrid[r].length; c++) {

                if( battleGrid[r][c].getHolder() == Holder.FIRST){
                    isExistPointFirst = true;
                }

                if( battleGrid[r][c].getHolder() == Holder.SECOND){
                    isExistPointSecond = true;
                }

                Image img = Utilites.getImageActivePoint(battleGrid[r][c], holder);
                ImageView imgView = new ImageView(img);
                //Размеры ImageView
                imgView.setFitWidth(78);
                imgView.setFitHeight(80);

                newGridPane.add(imgView, c, r);
            }
        }

        Holder gameOver = null;

        if (!isExistPointFirst & isExistPointSecond){
            gameOver = Holder.FIRST;
        }
        if (!isExistPointSecond & isExistPointFirst){
            gameOver =  Holder.SECOND;
        }


        return new Pair<>(newGridPane, gameOver);
    }



}
