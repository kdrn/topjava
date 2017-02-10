package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> findAll(@Param("userId") Integer userId);

    @Override
    @Transactional
    Meal save(Meal entity);

    @Query("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId ORDER BY m.dateTime DESC")
    Meal findOne(@Param("id")Integer id, @Param("userId") Integer userId);

    @Override
    @Transactional
    void delete(Integer integer);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    List<Meal> findBetween(@Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate,
                           @Param("userId")int userId);
}
