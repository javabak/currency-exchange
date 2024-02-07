package servlet.currency_servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import model.Currency;
import service.CurrencyService;
import util.resp.ResponseHandler;

import java.io.IOException;
import java.util.List;

import static util.mapper.ToStringMapper.mapToString;

@WebServlet("/currencies")
@Log
public class CurrenciesServlet extends HttpServlet {

    private final CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<Currency> currencies = currencyService.getAllCurrencies();
        req.setAttribute("currencies", currencies);

        log.info("get all currencies".concat(mapToString(currencies)));
        req.getRequestDispatcher("currency.jsp").forward(req, resp);
        ResponseHandler.sendResponse(resp, HttpServletResponse.SC_OK, mapToString(currencies));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");

        Currency currency = Currency
                .builder()
                .fullName(name)
                .sign(sign)
                .code(code)
                .build();

        Currency createdCurrency = currencyService.createCurrency(currency);

        log.info("created currency".concat(mapToString(createdCurrency)));
        ResponseHandler.sendResponse(resp, HttpServletResponse.SC_OK, mapToString(createdCurrency));
    }
}
