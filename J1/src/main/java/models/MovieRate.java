package models;

import java.io.Serializable;

public class MovieRate implements Serializable {

    private int id;
    private int rate;
    private int userId;

    public MovieRate(int id, int rate, int userId) {
        this.id = id;
        this.rate = rate;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
