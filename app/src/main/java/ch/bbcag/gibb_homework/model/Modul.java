package ch.bbcag.gibb_homework.model;

import java.io.Serializable;

public class Modul implements Serializable {

    private int id;
    private String number;
    private String title;
    private String color;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

    @Override
    public String toString() {
        return number + " - " + title;
    }
}
