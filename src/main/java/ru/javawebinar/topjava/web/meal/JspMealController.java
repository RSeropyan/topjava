package ru.javawebinar.topjava.web.meal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
@RequestMapping("/meals")
public class JspMealController {

    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    private final MealService service;

    public JspMealController(MealService service) {
        this.service = service;
    }

    @GetMapping
    public String getMeals(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        List<MealTo> meals = MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", meals);
        return "meals";
    }

}
