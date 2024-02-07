package servlet.currency_servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import service.CurrencyService;
import util.resp.ResponseHandler;

import java.io.IOException;

@WebServlet("/delete")
@Log
public class CurrencyDeleteServlet extends HttpServlet {
    private final CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        currencyService.deleteCurrency(id);
        log.info("currency successfully deleted");
        ResponseHandler.sendResponse(resp, HttpServletResponse.SC_OK, "successfully deleted");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doDelete(req, resp);
    }
}
