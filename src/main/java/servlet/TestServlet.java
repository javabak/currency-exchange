package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/getName")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String name = req.getParameter("name");
        resp.getWriter().println("<html>");
        resp.getWriter().println();
        resp.getWriter().println(HttpServletResponse.SC_OK);
//        resp.getWriter().println("<h1>" + "Hello, " + name + "!" + "</h1>");
        resp.getWriter().println("</html>");
    }
}
