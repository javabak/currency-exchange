package dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import model.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ExchangeRateDto {
    Integer id;
    Currency baseCurrency;
    Currency targetCurrency;
    Double rate;
}
