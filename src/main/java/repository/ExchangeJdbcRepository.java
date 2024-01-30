package repository;

import dto.ExchangeRateDto;
import model.Currency;
import model.ExchangeRate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


public class ExchangeJdbcRepository implements ExchangeRateRepository {

    private static final String SELECT_ALL_EXCHANGE_RATES = "SELECT * FROM exchange_rates";
    private static final String SELECT_BY_CODE = "SELECT * FROM currencies WHERE code = ?";

    private static final String UPDATE = """
            UPDATE exchange_rates
            SET rate = ?
            WHERE id = ?
            """;

    private static final String SAVE = """
            INSERT INTO exchange_rates (base_currency_id, target_currency_id, rate)
            VALUES (?, ?, ?)
            """;

    private static final String FIND_BY_PAIR_CODE = """
            SELECT
                er.id,
                bc.id AS base_id,
                bc.code AS base_code,
                bc.full_name AS base_full_name,
                bc.sign AS base_sign,
                tc.id AS target_id,
                tc.code AS target_code,
                tc.full_name AS target_full_name,
                tc.sign AS target_sign,
                er.rate
            FROM exchange_rates AS er
            LEFT JOIN currencies AS bc ON er.base_currency_id = bc.id
            LEFT JOIN currencies AS tc ON er.target_currency_id = tc.id
            WHERE bc.code = ? AND tc.code = ?
            """;

    private static final Connection connection;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/alim/Desktop/currency-exchange/identifier.sqlite");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ExchangeRate> getAll() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_EXCHANGE_RATES)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                exchangeRates.add(
                        new ExchangeRate(
                                resultSet.getInt("id"),
                                resultSet.getString("base_currency_id"),
                                resultSet.getString("target_currency_id"),
                                resultSet.getDouble("rate")
                        ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exchangeRates;
    }



    public Optional<ExchangeRateDto> findByBaseAndTargetCodes(String baseCode, String targetCode) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_PAIR_CODE)) {
            preparedStatement.setString(1, baseCode);
            preparedStatement.setString(2, targetCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            ExchangeRateDto exchangeRate = null;
            if (resultSet.next()) {
                exchangeRate = buildExchangeRate(resultSet);
            }
            return Optional.ofNullable(exchangeRate);
        }
    }

    @Override
    public ExchangeRateDto create(ExchangeRateDto exchangeRateDto) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, exchangeRateDto.getBaseCurrency().getId());
            preparedStatement.setInt(2, exchangeRateDto.getTargetCurrency().getId());
            preparedStatement.setDouble(3, exchangeRateDto.getRate());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                exchangeRateDto.setId(generatedKeys.getInt(1));
            }

            return exchangeRateDto;
        }
    }

    public Optional<Currency> getByCode(String code) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_BY_CODE)) {
            ps.setString(1, code);
            ResultSet resultSet = ps.executeQuery();

            Currency currency = Currency
                    .builder()
                    .id(resultSet.getInt("id"))
                    .sign(resultSet.getString("sign"))
                    .fullName(resultSet.getString("full_name"))
                    .code(resultSet.getString("code"))
                    .build();
            return Optional.ofNullable(currency);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ExchangeRateDto update(ExchangeRateDto exchangeRateDto) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setDouble(1, exchangeRateDto.getRate());
            preparedStatement.setInt(2, exchangeRateDto.getId());
            preparedStatement.executeUpdate();
            return exchangeRateDto;
        }
    }

    public ExchangeRateDto buildExchangeRate(ResultSet resultSet) throws SQLException {
        return new ExchangeRateDto(
                resultSet.getInt("id"),
                new Currency(
                        resultSet.getInt("base_id"),
                        resultSet.getString("base_code"),
                        resultSet.getString("base_full_name"),
                        resultSet.getString("base_sign")
                ),
                new Currency(
                        resultSet.getInt("target_id"),
                        resultSet.getString("target_code"),
                        resultSet.getString("target_full_name"),
                        resultSet.getString("target_sign")
                ),
                resultSet.getDouble("rate")
        );
    }
}
