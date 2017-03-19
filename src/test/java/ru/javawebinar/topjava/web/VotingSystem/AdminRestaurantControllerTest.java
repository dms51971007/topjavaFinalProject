package ru.javawebinar.topjava.web.VotingSystem;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.service.RestaurantService;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.Profiles.JPA;
import static ru.javawebinar.topjava.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.lunchTestData.*;

/**
 * Created by r2 on 16.03.2017.
 */
@ActiveProfiles(JPA)
public class AdminRestaurantControllerTest extends AbstractControllerTest {


    private static final String REST_URL = AdminRestaurantController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "restaurant/" + rest2_id)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_RESTAURANT.contentMatcher(rest2));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "restaurant/" + rest1_id)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print());

        mockMvc.perform(get(REST_URL + "restaurant/" + rest1_id)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

        mockMvc.perform(get(REST_URL + "restaurant/" + rest1_id + 154)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().is4xxClientError())
                .andDo(print());

    }

    @Test
    public void testUpdate() throws Exception {

        Restaurant updated = new Restaurant(rest1_id,"updated");

        mockMvc.perform(put(REST_URL + "restaurant/")
                .with(userHttpBasic(ADMIN)).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(updated)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        mockMvc.perform(get(REST_URL + "restaurant/" + rest1_id)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_RESTAURANT.contentMatcher(updated));

    }

    @Test
    public void testCreate() throws Exception {

        Restaurant created = new Restaurant(null,"new");

        mockMvc.perform(post(REST_URL + "restaurant")
                .with(userHttpBasic(ADMIN)).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(created)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        MATCHER_RESTAURANT.assertCollectionEquals(Arrays.asList(created, rest1,rest2),restaurantService.getAll());

    }


}
