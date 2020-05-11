package com.example.diplom1;
import java.io.Serializable;

public class ItemsModel implements Serializable {

    private int id;

    private String name;

    private String image;

    public ItemsModel(int id, String name, String image) {
        this.id     = id;
        this.name   = name;
        this.image  = image;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}