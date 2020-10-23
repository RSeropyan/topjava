package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Before
    public void setUp() {
        initializeTestData();
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherUserMeal() throws Exception {
        service.get(adminMeal1.getId(), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() throws Exception {
        service.delete(userMeal1.getId(), ADMIN_ID);
    }

    @Test
    public void getById() throws Exception {
        Meal actual1 = service.get(100002, USER_ID);
        Meal expected1 = userMeal1;
        assertThat(actual1).usingRecursiveComparison().isEqualTo(expected1);
        Meal actual2 = service.get(100005, ADMIN_ID);
        Meal expected2 = adminMeal1;
        assertThat(actual2).usingRecursiveComparison().isEqualTo(expected2);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> actualMeals = service.getAll(USER_ID);
        List<Meal> expectedMeals = Arrays.asList(userMeal3, userMeal2, userMeal1);
        assertThat(actualMeals).usingRecursiveComparison().isEqualTo(expectedMeals);
    }

    @Test
    public void deleteById() throws Exception {
        service.delete(100002, USER_ID);
        List<Meal> actualMeals = service.getAll(USER_ID);
        List<Meal> expectedMeals = Arrays.asList(userMeal3, userMeal2);
        assertThat(actualMeals).usingRecursiveComparison().isEqualTo(expectedMeals);
    }

    @Test(expected = DataAccessException.class)
    public void createMealWithTheSameDateTime() throws Exception {
        // When id is null the new entity is created
        userMeal1.setId(null);
        service.create(userMeal1, USER_ID);
    }

    @Test(expected = DataAccessException.class)
    public void trickyDateTimeDuplicateCreation() throws Exception {
        // When id is not null the existing entity is updated
        userMeal1.setDateTime(userMeal2.getDateTime());
        service.create(userMeal1, USER_ID);
    }

}