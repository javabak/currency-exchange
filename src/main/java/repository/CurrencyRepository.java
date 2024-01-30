package repository;

import model.Currency;

import java.sql.SQLException;
import java.util.List;

public interface CurrencyRepository {
    List<Currency> getAll() throws SQLException;

    Currency create(Currency entity) throws SQLException;

}