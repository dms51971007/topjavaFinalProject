package ru.javawebinar.topjava.web.VotingSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.service.MenuItemService;
import ru.javawebinar.topjava.service.RestaurantService;
import ru.javawebinar.topjava.to.RestaurantTo;

import java.net.URI;
import java.util.List;

/**
 * Created by r2 on 09.03.2017.
 */
@RestController
@RequestMapping(AdminRestaurantController.REST_URL)

public class AdminRestaurantController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuItemService menuItemService;

    static final String REST_URL = "/rest/admin";

    @GetMapping(value = "restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        log.info("restaurants getAll for User {}", AuthorizedUser.id());
        return restaurantService.getAll();
    }

    @GetMapping(value = "restaurant/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id) {
        log.info("restaurants get {} for User {}", id, AuthorizedUser.id());
        return restaurantService.get(id);
    }

    @PostMapping(value = "restaurant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody RestaurantTo restaurantTo) {

        Restaurant restaurant = new Restaurant(restaurantTo.getName());
        log.info("restaurants create {} for User {}", restaurantTo.getName(), AuthorizedUser.id());
        Restaurant created = restaurantService.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "restaurant", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> update(@RequestBody RestaurantTo restaurantTo) {

        Restaurant restaurant = new Restaurant(restaurantTo.getId(), restaurantTo.getName());
        Restaurant updated = restaurantService.update(restaurant);

        log.info("restaurants update {} for User {}", restaurantTo.getName(), AuthorizedUser.id());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "restaurant/{id}")
                .buildAndExpand(updated.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(updated);

    }

    @DeleteMapping(value = "restaurant/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("restaurants delete {} for User {}", id, AuthorizedUser.id());
        restaurantService.delete(id);
    }


}
