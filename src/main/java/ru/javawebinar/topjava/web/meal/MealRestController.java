package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController extends AbstractMealRestController {

    public List<MealTo> getUserMeals() {
        return super.getUserMeals(authUserId());
    }

    public List<MealTo> getUserMealsFilteredByDate(LocalDateTime start, LocalDateTime end) {
        return super.getUserMealsFilteredByDate(authUserId(), start, end);
    }

    public Meal getUserMeal(Integer mealId) {
        return super.getUserMeal(authUserId(), mealId);
    }

    public Meal createMeal(Meal meal) {
        return super.createMeal(authUserId(), meal);
    }

    public Meal updateMeal(Meal meal) {
        return super.updateMeal(authUserId(), meal);
    }

    public void deleteMeal(Integer mealId) {
        super.deleteMeal(authUserId(), mealId);
    }


}