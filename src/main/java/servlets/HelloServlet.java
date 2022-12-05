package servlets;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userInput = (String) session.getAttribute("userInput");
        req.setAttribute("userInput", userInput);
        if (userInput != null) {
            session.removeAttribute("userInput");
        }
        req.setAttribute("pageBody", "index.jsp");
        req.getRequestDispatcher("/WEB-INF/_layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String userInput = req.getParameter("userInput");
        HttpSession session = req.getSession();
        session.setAttribute("userInput", userInput);
        resp.sendRedirect(req.getRequestURI());
    }
}