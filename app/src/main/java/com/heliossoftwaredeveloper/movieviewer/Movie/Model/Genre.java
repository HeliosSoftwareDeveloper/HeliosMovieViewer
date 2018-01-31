package com.heliossoftwaredeveloper.movieviewer.Movie.Model;

import java.io.Serializable;

/**
 * Created by rngrajo on 30/01/2018.
 */

public class Genre implements Serializable{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
