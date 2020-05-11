package com.example.diplom1;

import java.io.Serializable;

public class Car  implements Serializable {

    private int    id;

    private String name;

    private String image;

    public Car(int id, String name, String image) {
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
