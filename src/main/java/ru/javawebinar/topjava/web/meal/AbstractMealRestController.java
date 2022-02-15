package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractMealRestController {

    @Autowired
    private MealService service;

    public List<MealTo> getUserMeals(Integer userId) {
        return service.getAll(userId);
    }

    public List<MealTo> getUserMealsFilteredByDate(Integer userId, LocalDateTime start, LocalDateTime end) {
        return getUserMealsFilteredByDate(userId, start, end);
    }

    public Meal getUserMeal(Integer userId, Integer mealId) {
        return service.get(userId, mealId);
    }

    public Meal createMeal(Integer userId, Meal meal) {
        return service.create(userId, meal);
    }

    public Meal updateMeal(Integer userId, Meal meal) {
        return service.update(userId, meal);
    }

    public void deleteMeal(Integer userId, Integer mealId) {
        service.delete(userId, mealId);
    }

}
