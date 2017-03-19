package ru.javawebinar.topjava.to;

import java.time.LocalDate;

/**
 * Created by r2 on 13.03.2017.
 */
public class VoteTo {
    private LocalDate data;
    private int restaurantId;

    public VoteTo(LocalDate data, int restaurantId) {
        this.data = data;
        this.restaurantId = restaurantId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
