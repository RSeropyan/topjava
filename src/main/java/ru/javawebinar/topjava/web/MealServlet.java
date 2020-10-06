package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.dto.MealTo;
import ru.javawebinar.topjava.model.service.MealsService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MealServlet extends HttpServlet {

//    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<MealTo> meals = MealsService.getInstance().getAllMeals();
        req.setAttribute("meals", meals);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/meals.jsp");
        requestDispatcher.forward(req, resp);

    }
}
