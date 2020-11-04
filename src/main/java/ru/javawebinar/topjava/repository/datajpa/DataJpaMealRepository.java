package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DataJpaMealRepository implements MealRepository {

    private final CrudMealRepository crudMealRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudUserRepository crudUserRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudUserRepository = crudUserRepository;

    }

    @Override
    public Meal save(Meal meal, int userId) {
        User user = crudUserRepository.getOne(userId);
        meal.setUser(user);
        if (meal.isNew()) {
            return crudMealRepository.save(meal);
        }
        else {
            Meal existingMeal = get(meal.getId(), userId);
            if (existingMeal != null) {
                existingMeal.setDateTime(meal.getDateTime());
                existingMeal.setDescription(meal.getDescription());
                existingMeal.setCalories(meal.getCalories());
                return existingMeal;
            }
            return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Optional<Meal> meal = crudMealRepository.findById(id);
        if (meal.isPresent() && meal.get().getUser().getId() == userId) {
            crudMealRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Optional<Meal> meal = crudMealRepository.findById(id);
        return meal.filter(value -> value.getUser().getId() == userId).orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.findAll(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepository.findAllFilteredByDateTime(startDateTime, endDateTime, userId);
    }
}
