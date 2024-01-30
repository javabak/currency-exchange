package servlet.exchange_rate_servlets;

import dto.ExchangeRateDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import service.ExchangeRateService;
import util.resp.ResponseHandler;

import static util.mapper.ToStringMapper.mapToString;

@WebServlet("/exchangeRate")
public class ExchangeRateServlet extends HttpServlet {

    private final ExchangeRateService exchangeRateService = new ExchangeRateService();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String codes = req.getParameter("codes");

        ExchangeRateDto exchangeRateDto = exchangeRateService.getByCodes(codes);
        ResponseHandler.sendResponse(resp, HttpServletResponse.SC_OK, mapToString(exchangeRateDto));
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String baseCode = req.getParameter("baseCode");
        String targetCode = req.getParameter("targetCode");
        Double rate = Double.parseDouble(req.getParameter("rate"));

        ExchangeRateDto exchangeRateDto = exchangeRateService.updateExchangeRate(baseCode, targetCode, rate);

        ResponseHandler.sendResponse(resp, HttpServletResponse.SC_OK, mapToString(exchangeRateDto));
    }

}
