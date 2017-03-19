package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.MenuItem;
import ru.javawebinar.topjava.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by r2 on 09.03.2017.
 */
@Repository
@Transactional(readOnly = true)
public class JpaMenuItemRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public MenuItem save(MenuItem menuItem) {
        if (!menuItem.isNew() && get(menuItem.getId()) == null) {
            return null;
        }

        if (menuItem.isNew()) {
            em.persist(menuItem);
            return menuItem;
        } else {
            return em.merge(menuItem);
        }
    }

    public MenuItem get(int id) {
        MenuItem menuItem = em.find(MenuItem.class, id);
        return menuItem != null ? menuItem : null;
    }

    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(MenuItem.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    public List<MenuItem> getAll(Restaurant restaurant, LocalDate date) {
        return em.createNamedQuery(MenuItem.ALL_RESTAURANT_SORTED, MenuItem.class).setParameter("date", date).setParameter("restaurant", restaurant).getResultList();
    }
}
