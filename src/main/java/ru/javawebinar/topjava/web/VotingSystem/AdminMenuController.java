package ru.javawebinar.topjava.web.VotingSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.MenuItem;
import ru.javawebinar.topjava.service.MenuItemService;
import ru.javawebinar.topjava.service.RestaurantService;
import ru.javawebinar.topjava.to.MenuItemTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

/**
 * Created by r2 on 17.03.2017.
 */
@RestController
@RequestMapping(AdminRestaurantController.REST_URL)

public class AdminMenuController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuItemService menuItemService;

    static final String REST_URL = "/rest/admin/";



    @GetMapping(value = "menus/{restaurant_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuItemTo> getAll(@PathVariable("restaurant_id") int restaurant_id, @RequestParam(required = false) LocalDate date) {
        log.info("menuItem getAll for User {}", AuthorizedUser.id());

        if (date == null) date = LocalDate.now();

        return menuItemService.getAll(restaurant_id, date);
    }

    @GetMapping(value = "menu/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuItemTo get(@PathVariable("id") int id) {
        log.info("menuItem getAll for User {}", AuthorizedUser.id());
        MenuItemTo menuItem = menuItemService.get(id);
        return menuItem;
    }


    @PostMapping(value = "menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> create(@RequestBody MenuItemTo menuItemTo) {
        log.info("menuItem create {} for User {}", menuItemTo, AuthorizedUser.id());

        checkNew(menuItemTo);

        MenuItem created = menuItemService.save(menuItemTo);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> update(@RequestBody MenuItemTo menuItem) {
        log.info("menuItem update {} for User {}", menuItem, AuthorizedUser.id());

        MenuItem created = menuItemService.update(menuItem);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "menu/{id}")
    public void delete(@PathVariable("id") int id) {

        log.info("menuItem delete {} for User {}", id, AuthorizedUser.id());

        menuItemService.delete(id);
    }

}
