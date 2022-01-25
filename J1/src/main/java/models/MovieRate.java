package models;

import java.io.Serializable;

public class MovieRate implements Serializable {

    private int id;
    private int rate;

    public MovieRate(int id, int rate) {
        this.id = id;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
