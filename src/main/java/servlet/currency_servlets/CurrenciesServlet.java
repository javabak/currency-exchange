package servlet.currency_servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Currency;
import service.CurrencyService;
import util.resp.ResponseHandler;

import java.io.IOException;
import java.util.List;

import static util.mapper.ToStringMapper.mapToString;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {

    private final CurrencyService currencyService = new CurrencyService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Currency> currencies = currencyService.getAllCurrencies();
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
        ResponseHandler.sendResponse(resp, HttpServletResponse.SC_OK, mapToString(createdCurrency));
    }
}
