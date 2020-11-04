package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Query(value = "SELECT * FROM meals WHERE user_id = ?1 order by date_time DESC ", nativeQuery = true)
    List<Meal> findAll(int userId);

    @Query(value = "SELECT * FROM meals WHERE user_id = ?3 AND date_time >= ?1 AND date_time < ?2  order by date_time DESC", nativeQuery = true)
    List<Meal> findAllFilteredByDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

}
