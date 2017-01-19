package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator populator;

    @Before
    public void setUp() throws Exception {
        populator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal gotMeal = service.get(MealTestData.MEAL_ID, UserTestData.USER_ID);
        MATCHER.assertEquals(MealTestData.TEST_MEAL_WITH_ID, gotMeal);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_ID, USER_ID);
        MATCHER.assertCollectionEquals(MEALS_LIST_WITHOUT_100003, service.getAll(USER_ID));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        LocalDateTime ldt1 = LocalDateTime.of(2015, Month.MAY, 30, 10, 0);
        LocalDateTime ldt2 = LocalDateTime.of(2015, Month.MAY, 30, 13, 0);
        List<Meal> got = new ArrayList<Meal>(service.getBetweenDateTimes(ldt1, ldt2, USER_ID));
        MATCHER.assertCollectionEquals(MEALS_LIST_BETWEEN, got);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(MEALS_LIST_ALL, service.getAll(USER_ID));
    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void save() throws Exception {

    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testAlienGet() throws Exception {
        Meal newMeal = service.update(TEST_MEAL_ALIEN, ADMIN_ID);
        service.get(newMeal.getId(), USER_ID);
    }
}