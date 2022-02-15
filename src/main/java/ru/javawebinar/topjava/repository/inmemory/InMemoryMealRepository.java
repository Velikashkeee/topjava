package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(1, meal));
        MealsUtil.meals.forEach(meal -> this.save(0, meal));
    }

    @Override
    public Meal save(Integer userId, Meal meal) {
        if (!userId.equals(meal.getUserId())) {
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(Integer userId, int id) {
        Meal meal = repository.get(id);
        return userId.equals(meal.getUserId()) && repository.remove(id) != null;
    }

    @Override
    public Meal get(Integer userId, int id) {
        Meal meal = repository.get(id);
        return userId.equals(meal.getUserId()) ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return repository.values().stream()
                .filter(meal ->userId.equals(meal.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllFilteredByDate(Integer userId, LocalDateTime start, LocalDateTime end) {
        return repository.values().stream()
                .filter(meal -> userId.equals(meal.getUserId()))
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), start, end))
                .collect(Collectors.toList());
    }
}

