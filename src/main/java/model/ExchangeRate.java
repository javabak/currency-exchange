package model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ExchangeRate {

    Integer id;
    String baseCode;
    String targetCode;
    Double rate;
}