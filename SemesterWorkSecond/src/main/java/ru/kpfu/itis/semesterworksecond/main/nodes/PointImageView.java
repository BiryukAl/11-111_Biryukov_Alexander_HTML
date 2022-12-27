package ru.kpfu.itis.semesterworksecond.main.nodes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.kpfu.itis.semesterworksecond.logic.model.Holder;

public class PointImageView extends ImageView {

    public PointImageView(String s, int x, int y) {
        this(s, x, y, null);
    }

    public PointImageView(String s, int x, int y, Holder holder) {
        super(s);
        setX(x);
        setY(y);
    }

    public PointImageView(Image image, int x, int y) {
        this(image, x, y, null);
    }

    public PointImageView(Image image, int x, int y, Holder holder) {
        super(image);
        setX(x);
        setY(y);

    }


    private final int MAX_WEIGHT = 3;
    private int x;
    private int y;
    private int weight = 0;

    Holder holder = null;


    public Holder getHolder() {
        return holder;
    }

    public void setHolder(ru.kpfu.itis.semesterworksecond.logic.model.Holder holder) {
        this.holder = holder;
    }

    /**
     * @return происходит размножение поля (true: ; false: ;)
     */
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


    public int getXPoint() {
        return x;
    }

    public void setXPoint(int x) {
        this.x = notNegative(x);
    }

    public int getYPoint() {
        return y;
    }

    public void setYPoint(int y) {
        this.y = notNegative(y);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = notNegative(weight);
    }


}
