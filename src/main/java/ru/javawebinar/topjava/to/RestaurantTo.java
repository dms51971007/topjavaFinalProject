package ru.javawebinar.topjava.to;

/**
 * Created by r2 on 17.03.2017.
 */
public class RestaurantTo {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
