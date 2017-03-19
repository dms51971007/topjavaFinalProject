package ru.javawebinar.topjava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by r2 on 09.03.2017.
 */
@Entity
@Table
@NamedQueries({
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT r FROM Restaurant r ORDER BY r.name"),
})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Restaurant extends NamedEntity {
    public static final String DELETE = "Restoraunt.delete";
    public static final String ALL_SORTED = "Restoraunt.getAllSorted";

    public Restaurant(String name) {
        setName(name);
    }

    public Restaurant(Restaurant r) {
        setName(r.getName());
        setId(r.getId());
    }


    public Restaurant(Integer id,  String name) {
        setId(id);
        setName(name);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @JsonIgnore
    protected List<MenuItem> menu;

    public Restaurant() {
    }

}
