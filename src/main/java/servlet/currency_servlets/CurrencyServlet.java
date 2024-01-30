package servlet.currency_servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Currency;
import service.CurrencyService;
import util.resp.ResponseHandler;

import java.io.IOException;

import static util.mapper.ToStringMapper.mapToString;

@WebServlet("/currency_servlets")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");

        Currency currency = currencyService.getCurrencyByCode(code);

        ResponseHandler.sendResponse(resp, HttpServletResponse.SC_OK, mapToString(currency));

    }
}
