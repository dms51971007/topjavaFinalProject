package ru.javawebinar.topjava.web.VotingSystem;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.to.MenuItemTo;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.Profiles.JPA;
import static ru.javawebinar.topjava.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.lunchTestData.*;

/**
 * Created by r2 on 17.03.2017.
 */
@ActiveProfiles(JPA)
public class AdminMenuControllerTest extends AbstractControllerTest {


    private static final String REST_URL = AdminMenuController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "menu/" + mi1.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_MENUITEM.contentMatcher(mi1));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL + "menus/" + rest1_id + "?date=2017-03-10")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_MENUITEM.contentListMatcher(Arrays.asList(mi1, mi2)));
    }


    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "menu/" + mi1.getId())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print());

        MATCHER_MENUITEM.assertCollectionEquals(Collections.singletonList(mi2), menuItemService.getAll(mi1.getRestaurantId(), mi1.getData()));

    }

    @Test
    public void testUpdate() throws Exception {
        MenuItemTo updated = new MenuItemTo(200010, "updated", 5.11, LocalDate.of(2017, 3, 10), rest1_id);

        mockMvc.perform(put(REST_URL + "menu/")
                .with(userHttpBasic(ADMIN)).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(updated)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        MATCHER_MENUITEM.assertEquals(updated, menuItemService.get(updated.getId()));

    }

    @Test
    public void testCreate() throws Exception {

        MenuItemTo mi0 = new MenuItemTo(null, "pre_soup", 2.11, null, rest1_id);

        mockMvc.perform(post(REST_URL + "menu")
                .with(userHttpBasic(ADMIN)).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(mi0)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
        mi0.setData(LocalDate.now());
        MATCHER_MENUITEM.assertCollectionEquals(Arrays.asList(mi0), menuItemService.getAll(rest1_id, null));

    }


}
