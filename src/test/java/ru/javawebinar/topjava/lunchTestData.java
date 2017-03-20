package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.MenuItem;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MenuItemTo;
import ru.javawebinar.topjava.to.VoteResultTo;
import ru.javawebinar.topjava.to.VoteTo;

import java.time.LocalDate;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * Created by r2 on 09.03.2017.
 */
public class lunchTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static final ModelMatcher<Restaurant> MATCHER_RESTAURANT = ModelMatcher.of(Restaurant.class);
    public static final ModelMatcher<MenuItemTo> MATCHER_MENUITEM = ModelMatcher.of(MenuItemTo.class);
    public static final ModelMatcher<VoteResultTo> MATCHER_VOTE = ModelMatcher.of(VoteResultTo.class);

    public static Integer rest1_id = 300010;
    public static Integer rest2_id = 300011;

    public static Restaurant rest1 = new Restaurant(rest1_id,"rest1");
    public static Restaurant rest2 = new Restaurant(rest2_id,"rest2");

    public static MenuItemTo mi1 = new MenuItemTo(200010, "pizza", 5.11, LocalDate.of(2017, 3, 10), rest1_id);
    public static MenuItemTo mi2 = new MenuItemTo(200011, "soup", 2.12, LocalDate.of(2017, 3, 10), rest1_id);


}
