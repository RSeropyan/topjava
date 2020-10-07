package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.dto.MealTo;
import ru.javawebinar.topjava.model.entity.Meal;
import ru.javawebinar.topjava.model.service.MealsService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if (action == null) {
            List<MealTo> meals = MealsService.getInstance().findAll();
            req.setAttribute("meals", meals);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/meals.jsp");
            requestDispatcher.forward(req, resp);
        }
        else if (action.equals("add")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/meal-add.jsp");
            requestDispatcher.forward(req, resp);
        }
        else if (action.equals("update")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            Meal meal = MealsService.getInstance().findById(id);

            req.setAttribute("id", id);
            req.setAttribute("datetime", meal.getDateTime());
            req.setAttribute("description", meal.getDescription());
            req.setAttribute("calories", meal.getCalories());

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/meal-update.jsp");
            requestDispatcher.forward(req, resp);
        }
        else {
            doDelete(req, resp);
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idRequestParameter = req.getParameter("id");
        Integer id = Integer.parseInt(idRequestParameter);
        MealsService.getInstance().removeById(id);

        resp.sendRedirect("meals");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");

        LocalDateTime datetime = LocalDateTime.parse(req.getParameter("datetime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        if (id == null) {
            MealsService.getInstance().save(null, datetime, description, calories);
            resp.sendRedirect("meals");
        }
        else {
            doPut(req, resp);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = Integer.parseInt(req.getParameter("id"));
        LocalDateTime datetime = LocalDateTime.parse(req.getParameter("datetime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        MealsService.getInstance().save(id, datetime, description, calories);
        resp.sendRedirect("meals");

    }
}
