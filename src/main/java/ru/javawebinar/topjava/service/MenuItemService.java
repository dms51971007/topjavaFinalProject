package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.MenuItem;
import ru.javawebinar.topjava.repository.jpa.JpaMenuItemRepository;
import ru.javawebinar.topjava.repository.jpa.JpaRestaurantRepository;
import ru.javawebinar.topjava.to.MenuItemTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by r2 on 09.03.2017.
 */
@Service
public class MenuItemService {

    @Autowired
    private JpaMenuItemRepository menuItemRepository;

    @Autowired
    private JpaRestaurantRepository restaurantRepository;

    public MenuItem toMenuItem(MenuItemTo menuItemTo) {
        Assert.notNull(menuItemTo, "menuItem must not be null");
        return new MenuItem(menuItemTo.getId(),
                menuItemTo.getName(),
                menuItemTo.getPrice(), menuItemTo.getData(),
                checkNotFoundWithId(restaurantRepository.get(menuItemTo.getRestaurantId()), menuItemTo.getRestaurantId()));
    }

    public MenuItemTo toMenuItemTo(MenuItem menuItem) {
        return new MenuItemTo(menuItem.getId(),
                menuItem.getName(),
                menuItem.getPrice(), menuItem.getData(),
                menuItem.getRestaurant().getId());
    }


    @CacheEvict(value = "restaurant", allEntries = true)
    public MenuItem save(MenuItemTo menuItemTo) {
        if (menuItemTo.getData() == null) menuItemTo.setData(LocalDate.now());
        return menuItemRepository.save(toMenuItem(menuItemTo));
    }

    @CacheEvict(value = "restaurant", allEntries = true)
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(menuItemRepository.delete(id), id);
    }


    public MenuItemTo get(int id) throws NotFoundException {
        return toMenuItemTo(checkNotFoundWithId(menuItemRepository.get(id), id));
    }


    @Transactional
    @CacheEvict(value = "menu", allEntries = true)
    public MenuItem update(MenuItemTo menuItemUpdated) {
        MenuItem menuItem = toMenuItem(menuItemUpdated);
        return menuItemRepository.save(menuItem);
    }

    @CacheEvict(value = "menu", allEntries = true)
    public void evictCache() {
    }

    @Cacheable("menu")
    public List<MenuItemTo> getAll(Integer restaurantId, LocalDate date) {
        if (date == null) date = LocalDate.now();
        return menuItemRepository.getAll(checkNotFoundWithId(restaurantRepository.get(restaurantId), restaurantId), date).stream()
                .map(menuItem -> toMenuItemTo(menuItem)).collect(Collectors.toList());

    }


}
