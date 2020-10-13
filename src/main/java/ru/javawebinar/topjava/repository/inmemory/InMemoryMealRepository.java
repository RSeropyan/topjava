package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

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
        MealsUtil.meals.forEach(meal -> this.save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {

        if (meal == null) {
            throw new IllegalArgumentException();
        }
        else if (meal.isNew()) {
            // CREATE new entity operation has been requested
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        else {
            // UPDATE an existing entity operation has been requested
            Integer id = meal.getId();
            if (repository.containsKey(id) && repository.get(id).getUserId().equals(userId)) {
                meal.setUserId(userId);
                repository.replace(id, meal);
                return meal;
            }
            return null;
        }

    }

    @Override
    public boolean delete(int id, int userId) {

        if (repository.containsKey(id)) {
            Meal meal = repository.get(id);
            if (meal.getUserId().equals(userId)) {
                repository.remove(id);
                return true;
            }
            return false;
        }
        return false;

    }

    @Override
    public Meal get(int id, int userId) {

        Meal meal = repository.get(id);
        if (meal != null && meal.getUserId().equals(userId)) {
            return meal;
        }
        return null;

    }

    @Override
    public List<Meal> getAll(int userId) {

        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());

    }

}

