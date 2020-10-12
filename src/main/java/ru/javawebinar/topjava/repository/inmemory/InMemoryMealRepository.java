package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {

        if (meal == null) {
            return null;
        }
        else if (meal.isNew()) {
            // Here we CREATE new entity
            meal.setId(counter.incrementAndGet());
            meal.setUserId(SecurityUtil.authUserId());
            repository.put(meal.getId(), meal);
            return meal;
        }
        else {
            // Here we UPDATE an existing entity
            Integer id = meal.getId();
            return repository.containsKey(id) ? updateExistingMeal(id, meal) : null;
        }

    }

    private Meal updateExistingMeal(Integer id, Meal meal) {

        // Meal comes from Controller layer without userId property
        Meal mealWithUserId = repository.get(id);
        if (mealWithUserId.getUserId().equals(SecurityUtil.authUserId())) {
            meal.setUserId(SecurityUtil.authUserId());
            repository.replace(id, meal);
            return meal;
        }
        else {
            return null;
        }

    }

    @Override
    public boolean delete(int id) {

        if (repository.containsKey(id)) {
            Meal meal = repository.get(id);
            if (meal.getUserId().equals(SecurityUtil.authUserId())) {
                repository.remove(id);
                return true;
            }
            return false;
        }
        return false;

    }

    @Override
    public Meal get(int id) {

        if (repository.containsKey(id)) {
            Meal meal = repository.get(id);
            return meal.getUserId().equals(SecurityUtil.authUserId()) ? meal : null;
        }
        return null;

    }

    @Override
    public List<Meal> getAll() {

        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId().equals(SecurityUtil.authUserId()))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());

    }
}

