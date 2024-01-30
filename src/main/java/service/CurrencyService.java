package service;

import exceptions.request_exceptions.CurrencyNotCreateException;
import exceptions.validate_exceptions.WrongParametersException;
import lombok.extern.slf4j.Slf4j;
import exceptions.request_exceptions.CurrencyNotFoundException;
import model.Currency;
import repository.CurrencyJdbcRepository;

import java.util.List;
import java.util.Optional;

import static util.validate.CurrencyValidate.*;

@Slf4j
public class CurrencyService {

    private final CurrencyJdbcRepository currencyJdbcRepository = new CurrencyJdbcRepository();


    public List<Currency> getAllCurrencies() {
        return currencyJdbcRepository.getAll();
    }

    public Currency createCurrency(Currency currency) {
        if (validateCode(currency.getCode()) && validateName(currency.getFullName())) {
            Optional<Currency> byCode = currencyJdbcRepository.getByCode(currency.getCode());
            if (byCode.isEmpty()) {
                Currency createdCurrency = currencyJdbcRepository.create(currency);
                return Currency
                        .builder()
                        .id(currency.getId())
                        .code(createdCurrency.getCode())
                        .sign(createdCurrency.getSign())
                        .fullName(createdCurrency.getFullName())
                        .build();
            } else {
                throw new CurrencyNotCreateException("currency_servlets with this code already exist");
            }
        } else {
            throw new WrongParametersException("wrong parameters: code or name");
        }
    }


    public void deleteCurrency(int id) {
        if (validateId(id)) {
            currencyJdbcRepository.delete(id);
        } else {
            throw new WrongParametersException("id contain letters");
        }
    }

    public Currency getCurrencyByCode(String currencyCode) {
        if (validateCode(currencyCode)) {
            Optional<Currency> byCode = currencyJdbcRepository.getByCode(currencyCode);
            if (byCode.isPresent()) {
                return byCode.get();
            } else {
                throw new CurrencyNotFoundException("currency_servlets with this code not found");
            }
        } else {
            throw new WrongParametersException("code contain digits or not uppercase");
        }
    }
}
