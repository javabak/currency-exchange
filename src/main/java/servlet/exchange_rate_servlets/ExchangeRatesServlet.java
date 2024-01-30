package servlet.exchange_rate_servlets;

import dto.ExchangeRateDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import model.ExchangeRate;
import service.ExchangeRateService;
import util.resp.ResponseHandler;

import java.io.IOException;
import java.util.List;

import static util.mapper.ToStringMapper.mapToString;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {

    private final ExchangeRateService exchangeRateService = new ExchangeRateService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ExchangeRate> allExchangeRates = exchangeRateService.getAllExchangeRates();
        ResponseHandler.sendResponse(resp, HttpServletResponse.SC_OK, mapToString(allExchangeRates));
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        Double rate = Double.parseDouble(req.getParameter("rate"));


        ExchangeRateDto createdExchangeRateDto = exchangeRateService.createExchangeRate(baseCurrencyCode, targetCurrencyCode, rate);
        ResponseHandler.sendResponse(resp, HttpServletResponse.SC_OK, mapToString((createdExchangeRateDto)));
    }
}