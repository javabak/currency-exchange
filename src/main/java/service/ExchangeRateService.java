package service;

import dto.ExchangeRateDto;
import exceptions.request_exceptions.ExchangeRateNotFoundException;
import exceptions.validate_exceptions.WrongParametersException;
import model.Currency;
import model.ExchangeRate;
import repository.ExchangeJdbcRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static util.validate.CurrencyValidate.validateCode;
import static util.validate.CurrencyValidate.validateRate;

public class ExchangeRateService {

    private final ExchangeJdbcRepository exchangeJdbcRepository = new ExchangeJdbcRepository();

    public List<ExchangeRate> getAllExchangeRates() {
        return exchangeJdbcRepository.getAll();
    }

    public ExchangeRateDto createExchangeRate(String baseCurrencyCode, String targetCurrencyCode, Double rate) throws SQLException {
        if (validateCode(baseCurrencyCode) && validateCode(targetCurrencyCode)) {
            Currency byBaseCode = exchangeJdbcRepository.getByCode(baseCurrencyCode).get();
            Currency byTargetCode = exchangeJdbcRepository.getByCode(targetCurrencyCode).get();
                ExchangeRateDto exchangeRateDto = ExchangeRateDto
                        .builder()
                        .baseCurrency(byBaseCode)
                        .targetCurrency(byTargetCode)
                        .rate(rate)
                        .build();
                return exchangeJdbcRepository.create(exchangeRateDto);
        } else {
            throw new WrongParametersException("codes contains digits or not uppercase");
        }
    }

    public ExchangeRateDto getByCodes(String codes) throws SQLException {
        if (validateCode(codes)) {
            String baseCode = codes.substring(0, 3);
            String targetCode = codes.substring(3);

            Optional<ExchangeRateDto> byBaseAndTargetCodes = exchangeJdbcRepository.findByBaseAndTargetCodes(baseCode, targetCode);
            if (byBaseAndTargetCodes.isPresent()) {
                return byBaseAndTargetCodes.get();
            } else {
                throw new ExchangeRateNotFoundException("exchange rate not found");
            }
        } else {
            throw new WrongParametersException("codes contains digits or not uppercase");
        }
    }

    public ExchangeRateDto updateExchangeRate(String baseCode, String targetCode, Double rate) throws SQLException {
        if (validateRate(rate)) {
            Optional<Currency> byBaseCode = exchangeJdbcRepository.getByCode(baseCode);
            Optional<Currency> byTargetCode = exchangeJdbcRepository.getByCode(targetCode);
            if (byBaseCode.isPresent() && byTargetCode.isPresent()) {
                ExchangeRateDto exchangeRateDto = ExchangeRateDto
                        .builder()
                        .baseCurrency(byBaseCode.get())
                        .targetCurrency(byTargetCode.get())
                        .rate(rate)
                        .build();
                return exchangeJdbcRepository.update(exchangeRateDto);
            } else {
                throw new ExchangeRateNotFoundException("exchange rate not found");
            }
        } else {
            throw new WrongParametersException("rate contain letters or not have '.'");
        }
    }
}
