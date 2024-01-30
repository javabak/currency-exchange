package repository;

import model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CurrencyJdbcRepository implements CurrencyRepository {

    private static final String SELECT_ALL = "SELECT * FROM currencies";
    private static final String SELECT_BY_CODE = "SELECT * FROM currencies WHERE code = ?";
    private static final String CREATE = "INSERT INTO currencies (code, full_name, sign) VALUES (?, ?, ?)";
    private static final String DELETE = "DELETE FROM currencies WHERE id = ?";

    private static final Connection connection;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/alim/Desktop/currency-exchange/identifier.sqlite");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Currency> getAll() {
        List<Currency> currencies = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Currency currency = of(resultSet);
                currencies.add(currency);
            }
            return currencies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Currency of(ResultSet rs) {
        Currency currency = new Currency();
        try {
            currency.setId(rs.getInt("id"));
            currency.setCode(rs.getString("code"));
            currency.setFullName(rs.getString("full_name"));
            currency.setSign(rs.getString("sign"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currency;
    }

    public static Optional<Currency> getCurrency(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return Optional.empty();
        } else {
            return Optional.of(of(resultSet));
        }
    }

    public Optional<Currency> getByCode(String code) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_CODE)) {
            ps.setString(1, code);
            return getCurrency(ps.executeQuery());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency create(Currency currency) {
        try (PreparedStatement ps = connection.prepareStatement(CREATE, new String[]{"id"})) {
            ps.setString(1, currency.getCode());
            ps.setString(2, currency.getFullName());
            ps.setString(3, currency.getSign());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                currency.setId(generatedKeys.getInt(1));
            }
            return currency;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
