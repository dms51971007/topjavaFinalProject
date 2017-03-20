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
import ru.javawebinar.topjava.model.Vote;
import ru.javawebinar.topjava.service.MenuItemService;
import ru.javawebinar.topjava.service.RestaurantService;
import ru.javawebinar.topjava.service.VoteService;
import ru.javawebinar.topjava.to.MenuItemTo;
import ru.javawebinar.topjava.to.VoteResultTo;
import ru.javawebinar.topjava.to.VoteTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by r2 on 09.03.2017.
 */
@RestController
@RequestMapping(VotesController.REST_URL)
public class VotesController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService voteService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuItemService menuItemService;

    static final String REST_URL = "/rest/votes/";

    @PostMapping(value = "{restaurant_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> create(@PathVariable("restaurant_id") int restaurantId) {

        log.info("votes for <>  User {}", restaurantId, AuthorizedUser.id());
        VoteTo created = voteService.save(new VoteTo(null, LocalDate.now(), restaurantId), AuthorizedUser.id());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping(value = "restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        log.info("restaurants getAll for User {}", AuthorizedUser.id());
        return restaurantService.getAll();
    }

    @GetMapping(value = "menus/{restaurant_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuItemTo> getAll(@PathVariable("restaurant_id") int restaurant_id, @RequestParam(required = false) LocalDate date) {
        log.info("menuItem getAll for User {}", AuthorizedUser.id());

        if (date == null) date = LocalDate.now();

        return menuItemService.getAll(restaurant_id, date);
    }


    @GetMapping(value = "results/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteResultTo> getAll(@RequestParam(required = false) LocalDate date) {
        if (date == null) date = LocalDate.now();
        return voteService.getResult(date);
    }


}
