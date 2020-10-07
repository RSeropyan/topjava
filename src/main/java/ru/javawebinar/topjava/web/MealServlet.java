package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            List<MealTo> meals = MealsService.getInstance().findAll();
            req.setAttribute("meals", meals);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/meals.jsp");
            requestDispatcher.forward(req, resp);
        }
        else {
            doDelete(req, resp);
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("doDelete method has been invoked!!!");

        String idRequestParameter = req.getParameter("id");
        Integer id = Integer.parseInt(idRequestParameter);
        MealsService.getInstance().removeById(id);

        resp.sendRedirect("meals");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
    }
}