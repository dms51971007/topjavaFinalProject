package ru.javawebinar.topjava.service.jpa;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.service.AbstractServiceTest;
import ru.javawebinar.topjava.service.RestaurantService;

import java.util.Arrays;

import static ru.javawebinar.topjava.Profiles.JPA;
import static ru.javawebinar.topjava.lunchTestData.*;

/**
 * Created by r2 on 09.03.2017.
 */
@ActiveProfiles(JPA)
public class JpaRestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    private JpaUtil jpaUtil;

    @Autowired
    protected RestaurantService service;

    @Before
    public void setUp() throws Exception {
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testSave() throws Exception {
        Restaurant newR = new Restaurant();
        newR.setName("privet");
        Restaurant created = service.save(newR);
        newR.setId(created.getId());
        MATCHER_RESTAURANT.assertCollectionEquals(Arrays.asList(newR, rest1, rest2), service.getAll());
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(rest1_id);
        MATCHER_RESTAURANT.assertCollectionEquals(Arrays.asList(rest2), service.getAll());
    }

    @Test
    public void testUpdate() throws Exception {

        Restaurant r = service.get(rest1_id);
        r.setName("rest_1");
        service.update(r);

        MATCHER_RESTAURANT.assertCollectionEquals(Arrays.asList(rest2,r), service.getAll());
    }


}
