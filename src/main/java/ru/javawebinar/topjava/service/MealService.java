package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Integer userId, Meal meal) {
        return repository.save(userId, meal);
    }

    public void delete(Integer userId, Integer mealId) {
        checkNotFound(repository.delete(userId, mealId), String.format("id= %s", mealId));
    }

    public Meal update(Integer userId, Meal meal) {
        return checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    public List<MealTo> getAll(Integer userId) {
        return MealsUtil.getTos(repository.getAll(userId).stream()
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAllFilteredByDate(Integer userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return MealsUtil.getTos(repository.getAllFilteredByDate(userId, startDateTime, endDateTime).stream()
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }


    public Meal get(Integer userId, Integer mealId) {
        return checkNotFoundWithId(repository.get(userId, mealId), mealId);
    }

}