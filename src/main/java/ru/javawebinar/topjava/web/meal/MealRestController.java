package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
public class MealRestController {

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal) {
        return service.save(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        if (meal.getId() == id) {
            service.save(meal, SecurityUtil.authUserId());
        }
    }

    public void deleteById(Integer id) {
        service.deleteById(id, SecurityUtil.authUserId());
    }

    public Meal getById(Integer id) {
        return service.getById(id, SecurityUtil.authUserId());
    }

    public List<MealTo> getAll() {

        int authUserId = SecurityUtil.authUserId();
        List<Meal> meals = service.getAll(authUserId);
        return MealsUtil.getTos(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);

    }

}