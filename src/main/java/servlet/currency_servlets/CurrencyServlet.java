package servlet.currency_servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import model.Currency;
import service.CurrencyService;
import util.resp.ResponseHandler;

import java.io.IOException;

import static util.mapper.ToStringMapper.mapToString;

@WebServlet("/currency")
@Log
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("code");

        Currency currency = currencyService.getCurrencyByCode(code);

        log.info("get currency by code".concat(mapToString(currency)));
        ResponseHandler.sendResponse(resp, HttpServletResponse.SC_OK, mapToString(currency));

    }
}
