package ru.javawebinar.topjava.web.VotingSystem;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.to.VoteResultTo;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.lunchTestData.*;

/**
 * Created by r2 on 18.03.2017.
 */
public class UserVotingSystemTest extends AbstractControllerTest {

    private static final String REST_URL = VotesController.REST_URL;

    @Test
    public void testRestaurants() throws Exception {

        mockMvc.perform(get(REST_URL + "restaurants")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_RESTAURANT.contentListMatcher(rest1, rest2));

    }

    @Test
    public void testMenu() throws Exception {

        mockMvc.perform(get(REST_URL + "menus/" + rest1_id + "?date=2017-03-10")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_MENUITEM.contentListMatcher(mi1, mi2));
    }

    @Test
    public void testVote() throws Exception {
        VoteResultTo vr1 = new VoteResultTo(1, rest1);
        VoteResultTo vr2 = new VoteResultTo(1, rest2);

        mockMvc.perform(post(REST_URL + rest1_id)
                .with(userHttpBasic(USER)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());


        mockMvc.perform(post(REST_URL + rest2_id)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        mockMvc.perform(get(REST_URL + "results/")
                .with(userHttpBasic(USER)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_VOTE.contentListMatcher(vr1, vr2));


    }


}
