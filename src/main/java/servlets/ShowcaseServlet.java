package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.CarDAO;
import entities.Car;
import entities.Filters;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class ShowcaseServlet extends HttpServlet {
    @Inject
    CarDAO carDAO;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Filters requestedFilter = new Filters(req);
        List<Car> allCars = carDAO.getAll();
        List<Car> cars = requestedFilter.apply(allCars);
        req.setAttribute("cars", cars);
        req.setAttribute("filters", new Filters(allCars));
        req.setAttribute("query", requestedFilter);
        req.setAttribute("pageBody", "showcase.jsp");
        req.getRequestDispatcher("/WEB-INF/_layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println(carDAO.add(new Car(req)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getRequestURI());
    }

}
