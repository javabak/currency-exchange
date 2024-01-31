package service;

import model.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import repository.CurrencyJdbcRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTest {


    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CurrencyJdbcRepository currencyJdbcRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    public void setJdbcTemplate() {
        // Arrange
        List<Currency> expectedCurrencies = Arrays.asList(
                new Currency("USD", "$", "US Dollar"),
                new Currency("EUR", "€", "Euro")
                // Add more currencies as needed
        );

        // Mocking behavior of the query method
        when(jdbcTemplate.query(anyString()))
                .thenReturn(expectedCurrencies);

        // Act
        List<Currency> actualCurrencies = currencyService.getAllCurrencies();

        // Assert
        assertEquals(expectedCurrencies.size(), actualCurrencies.size());

        for (int i = 0; i < expectedCurrencies.size(); i++) {
            Currency expectedCurrency = expectedCurrencies.get(i);
            Currency actualCurrency = actualCurrencies.get(i);

            assertEquals(expectedCurrency.getId(), actualCurrency.getId());
            assertEquals(expectedCurrency.getCode(), actualCurrency.getCode());
            assertEquals(expectedCurrency.getSign(), actualCurrency.getSign());
            assertEquals(expectedCurrency.getFullName(), actualCurrency.getFullName());
        }

        // Verify that the query method was called
        verify(jdbcTemplate, times(1)).query(anyString(), any(CurrencyRowMapper.class));
    }
}
