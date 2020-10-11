package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

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
        MealsUtil.meals.forEach(meal -> this.save(meal, null));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            // Here we CREATE new entity
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        else {
            // Here we UPDATE an existing entity
            Integer id = meal.getId();
            if (repository.containsKey(id)) {
                if (meal.getUserId().equals(userId)) {
                    repository.replace(id, meal);
                    return repository.get(id);
                }
                else {
                    return null;
                }
            } else {
                return null;
            }
        }
        //  handle case: update, but not present in storage
        //  return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {

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
    public Meal get(int id, Integer userId) {

        if (repository.containsKey(id)) {
            Meal meal = repository.get(id);
            return meal.getUserId().equals(userId) ? meal : null;
        }
        return null;

    }

    @Override
    public List<Meal> getAll(Integer userId) {

        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());

    }
}

