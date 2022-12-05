package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.CarDAO;
import entities.Car;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class CarServlet extends HttpServlet {
    @Inject
    CarDAO carDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        req.setAttribute("car", carDAO.getById(id));
        req.setAttribute("pageBody", "car.jsp");
        req.getRequestDispatcher("/WEB-INF/_layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String method = req.getParameter("method");
        try {
            if (method != null) {
                switch (method) {
                    case "PUT" -> carDAO.update(new Car(req));
                    case "DELETE" -> {
                        System.out.println(carDAO.deleteById(id));
                        resp.sendRedirect("/");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //req.
        resp.sendRedirect(req.getRequestURI() + "?" + req.getQueryString());
//        req.setAttribute("car", carDAO.getById(id));
//        req.setAttribute("pageBody", "car.jsp");
//        req.getRequestDispatcher("/WEB-INF/_layout.jsp").forward(req, resp);
    }
}
