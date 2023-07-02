package org.mankala.entity;

import java.util.List;

/**
 * Player object holds information about the id of the player and the stones in his/her pits and manKala.
 */
public class Player {

    private Integer id;
    private List<Integer> pits;
    private Integer manKala;

    // Getter and setters
    public Player (Integer id){
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        id = id;
    }

    public List<Integer> getPits() {
        return pits;
    }

    public void setPits(List<Integer> pits) {
        this.pits = pits;
    }

    public Integer getManKala() {
        return manKala;
    }

    public void setManKala(Integer manKala) {
        this.manKala = manKala;
    }
}
