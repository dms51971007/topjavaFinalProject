package ru.javawebinar.topjava.to;

import java.time.LocalDate;

/**
 * Created by r2 on 13.03.2017.
 */
public class VoteTo {
    private Integer id;

    private LocalDate data;
    private int restaurantId;

    public VoteTo(Integer id, LocalDate data, int restaurantId) {
        this.id = id;
        this.data = data;
        this.restaurantId = restaurantId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
