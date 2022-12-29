package ru.kpfu.itis.semesterworksecond.main;


import java.io.File;
import java.nio.file.Path;

public class Test {
    public static void main(String[] args) {

        int colum = 10;
        int row = 8;

        String[][] grid = new String[row][colum];


        for (int c = 0; c < colum; c++) {
            for (int r = 0; r < row; r++) {
                grid[r][c] = "c:" + c + "r:" + r;
            }
        }


        for (int i = 0; i < grid.length; i++) {
//            System.out.println(grid.length);
        }

        String pathAssets = "assets"+ File.separator +"point";
        StringBuilder stringBuilder = new StringBuilder(pathAssets);
        stringBuilder.append(1);
        stringBuilder.append("_");
        stringBuilder.append("blue");
        stringBuilder.append(".png");

        System.out.println(stringBuilder);
    }
}
