package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

import java.util.List;

@Controller
public class MealRestController {

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal save(Meal meal) throws NotFoundException {
        return service.save(meal, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) throws NotFoundException {
        checkNew(meal);
        return service.save(meal, SecurityUtil.authUserId());
    }


    public void update(Meal meal, int id) throws NotFoundException {
        assureIdConsistent(meal, id);
        service.save(meal, SecurityUtil.authUserId());
    }

    public void deleteById(Integer id) throws NotFoundException {
        service.deleteById(id, SecurityUtil.authUserId());
    }

    public Meal getById(Integer id) throws NotFoundException {
        return service.getById(id, SecurityUtil.authUserId());
    }

    public List<MealTo> getAll() {

        int authUserId = SecurityUtil.authUserId();
        List<Meal> meals = service.getAll(authUserId);
        return MealsUtil.getTos(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);

    }

}