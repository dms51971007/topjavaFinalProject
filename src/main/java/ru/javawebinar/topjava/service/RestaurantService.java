package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.repository.jpa.JpaRestaurantRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by r2 on 09.03.2017.
 */
@Service
public class RestaurantService {

    @Autowired
    private JpaRestaurantRepository repository;


    @CacheEvict(value = "restaurant", allEntries = true)
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restoraunt must not be null");
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurant", allEntries = true)
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @CacheEvict(value = "restaurant", allEntries = true)
    public void evictCache() {
    }



    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Transactional
    @CacheEvict(value = "restaurant", allEntries = true)
    public Restaurant update(Restaurant restorauntUpdated) {
        Restaurant restoraunt = get(restorauntUpdated.getId());

        restoraunt.setName(restorauntUpdated.getName());
        return repository.save(restoraunt);
    }

    @Cacheable("restaurant")
    public List<Restaurant> getAll() {
        return repository.getAll();
    }



}
