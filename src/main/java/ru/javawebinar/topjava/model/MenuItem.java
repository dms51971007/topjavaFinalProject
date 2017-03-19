package ru.javawebinar.topjava.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * Created by r2 on 09.03.2017.
 */

@Entity
@Table(name = "menu")
@NamedQueries({
        @NamedQuery(name = MenuItem.DELETE, query = "DELETE FROM MenuItem r WHERE r.id=:id"),
        @NamedQuery(name = MenuItem.ALL_SORTED, query = "SELECT r FROM MenuItem r ORDER BY r.name"),
        @NamedQuery(name = MenuItem.ALL_RESTAURANT_SORTED, query = "SELECT r FROM MenuItem r WHERE r.restaurant=:restaurant and r.data=:date ORDER BY r.name"),
})

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MenuItem extends NamedEntity {
    public static final String DELETE = "MenuItem.delete";
    public static final String ALL_SORTED = "MenuItem.getAllSorted";
    public static final String ALL_RESTAURANT_SORTED = "MenuItem.getAllRestaurant";


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @JoinColumn
    private Double price;

    @JoinColumn
    private LocalDate data;


    public Double getPrice() {
        return price;
    }

    public LocalDate getData() {
        return data;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public MenuItem() {
    }

    public MenuItem(Integer id, String name, Double price, LocalDate data, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.data = data;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name=" + name +
                ", price=" + price +
                ", data=" + data +
                '}';
    }
}
