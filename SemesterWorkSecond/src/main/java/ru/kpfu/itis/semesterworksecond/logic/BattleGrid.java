package ru.kpfu.itis.semesterworksecond.logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import ru.kpfu.itis.semesterworksecond.logic.model.Point;
import ru.kpfu.itis.semesterworksecond.utils.Utilites;

public class BattleGrid {
    /**
     * @value значения по x
     */
    final int COLUM = 10;

    /**
     * @value значение по y
     */
    final int ROW = 8;

    /**
     * @value Само поле с точками
     */
    public Point[][] grid;

    public BattleGrid() {
        grid = new Point[ROW][COLUM];

        for (int c = 0; c < COLUM; c++) {
            for (int r = 0; r < ROW; r++) {
                grid[r][c] = new Point(r, c);
            }
        }
    }

}
