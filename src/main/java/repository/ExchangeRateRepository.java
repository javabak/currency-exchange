package repository;

import dto.ExchangeRateDto;
import model.ExchangeRate;

import java.sql.SQLException;
import java.util.List;

public interface ExchangeRateRepository {
    List<ExchangeRate> getAll() throws SQLException;
    ExchangeRateDto create(ExchangeRateDto exchangeRateDto) throws SQLException;
    ExchangeRateDto update(ExchangeRateDto exchangeRateDto) throws SQLException;
}
