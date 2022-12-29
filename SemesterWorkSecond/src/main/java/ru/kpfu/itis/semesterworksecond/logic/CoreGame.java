package ru.kpfu.itis.semesterworksecond.logic;

import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import ru.kpfu.itis.semesterworksecond.logic.model.Holder;
import ru.kpfu.itis.semesterworksecond.logic.model.Point;
import ru.kpfu.itis.semesterworksecond.utils.Utilites;

import java.io.Serializable;

public class CoreGame implements Serializable {

    BattleGrid battleGrid = new BattleGrid();

    private final int sizeAreaX = battleGrid.COLUM;
    private final int sizeAreaY = battleGrid.ROW;

    /**
     * @value Чей ход следущий, первый ход принадлежит первому игроку
     */
    private Holder nextHolderStep = Holder.FIRST;

    Long countAllStep = 0L;

    public CoreGame() {
    }

    public boolean isMyStep(Holder holder){
        return nextHolderStep == holder;
    }



    public boolean step(int indexColum, int indexRow, Holder holder) {
        Holder clikedPointHolder = battleGrid.grid[indexRow][indexColum].getHolder();

        if (countAllStep < 2) {
            if (clikedPointHolder != null) {
                return false;
            }
            Point newPoint = new Point(indexColum, indexRow, holder);
            newPoint.setWeight(3);
            battleGrid.grid[indexRow][indexColum] = newPoint;
            ++countAllStep;
            nextHolderStep = nextHolderStep == Holder.FIRST ? Holder.SECOND : Holder.FIRST;
            return true;
        }

        if (clikedPointHolder != holder) {
            return false;
        }

        if (nextHolderStep.equals(holder)) {
            pushPoint(indexColum, indexRow, holder);
            ++countAllStep;
            nextHolderStep = nextHolderStep == Holder.FIRST ? Holder.SECOND : Holder.FIRST;
            return true;
        }

        return false;
    }

    public void pushPoint(int indexColum, int indexRow, Holder holder) {
        if (holder == null) {
            holder = nextHolderStep;
        }

        if (battleGrid.grid[indexRow][indexColum].increaseWeight(holder)) {

            if (!(indexRow + 1 >= sizeAreaY)) {
                pushPoint(indexColum, indexRow + 1, holder);
            }
            if (!(indexRow - 1 < 0)) {
                pushPoint(indexColum, indexRow - 1, holder);
            }

            if (!(indexColum + 1 >= sizeAreaX)) {
                pushPoint(indexColum + 1, indexRow, holder);
            }
            if (!(indexColum - 1 < 0)) {
                pushPoint(indexColum - 1, indexRow, holder);
            }
        }

    }

    public GridPane draw() {
        return Utilites.drawActivePoint(battleGrid.grid, nextHolderStep);
    }

    public Pair<GridPane, Holder> draw1() {
        Pair<GridPane, Holder> pair = Utilites.drawGridPainAndGameOver(battleGrid.grid, nextHolderStep);
        return new Pair<>(pair.getKey(), countAllStep < 2 ? null : pair.getValue());
    }

    public BattleGrid getBattleGrid() {
        return battleGrid;
    }

    public Long getCountAllStep() {
        return countAllStep;
    }
}
