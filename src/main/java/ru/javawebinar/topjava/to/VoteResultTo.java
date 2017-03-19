package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.model.Restaurant;

/**
 * Created by r2 on 14.03.2017.
 */
public class VoteResultTo {
    private long numVotes;
    private Restaurant restaurant;

    public VoteResultTo(long numVotes, Restaurant restaurant) {
        this.numVotes = numVotes;
        this.restaurant = restaurant;
    }

    public long getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public VoteResultTo() {
    }

    @Override
    public String toString() {
        return "VoteResultTo{" +
                "numVotes=" + numVotes +
                ", restaurant=" + restaurant.getId() +
                '}';
    }
}
