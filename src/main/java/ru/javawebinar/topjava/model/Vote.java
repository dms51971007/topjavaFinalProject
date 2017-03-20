package ru.javawebinar.topjava.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by r2 on 10.03.2017.
 */
@Entity
@Table(name = "votes")
@NamedQueries({
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id=:id"),
        @NamedQuery(name = Vote.GET_USER_DATA, query = "SELECT v FROM Vote v where v.user.id=:user_id and v.data=:data"),
        @NamedQuery(name = Vote.GET_RESULT, query = "SELECT new ru.javawebinar.topjava.to.VoteResultTo(count(v), v.restaurant) FROM Vote v where v.data=:data GROUP BY v.restaurant")

})

public class Vote extends BaseEntity {

    public static final String GET_USER_DATA = "Vote.getByReastaurantByData";
    public static final String ALL_SORTED = "Vote.getAllSorted";
    public static final String GET_RESULT = "Vote.getResult";
    public static final String DELETE = "Vote.Delete";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private LocalDate data;

    public Vote(LocalDate data) {
        this.data = data;
    }

    public Vote() {
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getData() {
        return data;
    }

    public void setRestaurant(Restaurant restaurant) {

        this.restaurant = restaurant;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Vote(Integer id, Restaurant restaurant, LocalDate data) {
        super(id);
        this.restaurant = restaurant;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "restaurant=" + restaurant.getId() +
                ", user=" + user.getId() +
                ", data=" + data +
                '}';
    }
}
