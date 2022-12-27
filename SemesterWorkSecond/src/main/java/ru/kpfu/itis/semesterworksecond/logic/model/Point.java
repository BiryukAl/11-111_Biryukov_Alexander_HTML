package ru.kpfu.itis.semesterworksecond.logic.model;

import javafx.scene.image.Image;

public class Point {
    private final int MAX_WEIGHT = 3;
    private int x;
    private int y;
    private int weight = 0;

    Holder holder = null;



    public Point(int x, int y, Holder holder) {
        setX(x);
        setY(y);
        setHolder(holder);
    }

    public Point(int x, int y) {
        this(x, y, null);
    }

    public Holder getHolder() {
        return holder;
    }

    public void setHolder(Holder holder) {
        this.holder = holder;
    }

    /**
     * @return  происходит размножение поля (true: ; false: ;)
     *
     * */
    public boolean increaseWeight(final Holder holder) {
        weight++;
        this.holder = holder;
        if (weight > MAX_WEIGHT) {
            weight = 0;
            this.holder = null;
            return true;
        }
        return false;
    }

    private int notNegative(int var) {
        if (var < 0) {
            return 0;
        }
        return var;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = notNegative(x);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = notNegative(y);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = notNegative(weight);
    }


}
