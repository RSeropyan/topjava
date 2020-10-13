package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal, int userId) {
        Meal newMeal = repository.save(meal, userId);
        if (newMeal != null) {
            return newMeal;
        }
        else {
            throw new NotFoundException("Not found entity with id=" + meal.getId());
        }
    }

    public void deleteById(int id, int userId) {

        boolean idDeleted = repository.delete(id, userId);
        if (!idDeleted) {
            throw new NotFoundException("Not found entity with id=" + id);
        }

    }

    public Meal getById(int id, int userId) {

        Meal meal = repository.get(id, userId);
        if (meal != null) {
            return meal;
        }
        else {
            throw new NotFoundException("Not found entity with id=" + id);
        }

    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

}