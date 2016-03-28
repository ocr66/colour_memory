package com.accedo.colourmemory;

/**
 * Created by o.lopez.cienfuegos on 20/03/2016.
 */
public class HighscoresData {

    private String name;
    private String score;

    public void HighscoreData(){

    }

    public void HighscoreData(String name, String score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
