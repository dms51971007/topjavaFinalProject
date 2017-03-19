package ru.javawebinar.topjava.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import ru.javawebinar.topjava.HasId;

import java.time.LocalDate;

/**
 * Created by r2 on 10.03.2017.
 */
public class MenuItemTo implements HasId {
    private Integer id;
    private String name;
    private Double price;
    private LocalDate data;
    private Integer restaurantId;

    public MenuItemTo(Integer id, String name, Double price, LocalDate data, Integer restaurantId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.data = data;
        this.restaurantId = restaurantId;
    }

    public Integer getRestaurantId() {

        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public MenuItemTo(@JsonProperty("id") Integer id,
                      @JsonProperty("name") String name,
                      @JsonProperty("price") Double price,
                      @JsonProperty("data") LocalDate data) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.data = data;
    }

    @Override
    public String toString() {
        return "MenuItemTo{" +
                " name='" + name + '\'' +
                ", price=" + price +
                ", data=" + data +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
