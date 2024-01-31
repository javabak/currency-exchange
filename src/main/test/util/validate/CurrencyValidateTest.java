package util.validate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.validate.CurrencyValidate.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyValidateTest {

    @Test
    public void testValidateId() {
        assertTrue(validateId(1));

        assertFalse(validateId(-1));
        assertFalse(validateId(0));
    }

    @Test
    public void testValidateRate() {
        assertTrue(validateRate(3.14));

        assertFalse(validateRate(-3.14));
        assertFalse(validateRate(Double.NaN));
        assertFalse(validateRate(Double.POSITIVE_INFINITY));

    }

    @Test
    public void testValidateCode() {
        assertTrue(validateCode("RUB"));
        assertTrue(validateCode("USD"));

        assertFalse(validateCode("rub"));
        assertFalse(validateCode("Rub"));
        assertFalse(validateCode("RUb"));
        assertFalse(validateCode("rub1"));
        assertFalse(validateCode("RUB12"));
    }

    @Test
    public void testValidateName() {
        assertTrue(validateName("Euro"));
        assertTrue(validateName("Dollar"));
        assertTrue(validateName("Rubble"));

        assertFalse(validateName("RUB12"));
        assertFalse(validateName("Rub12"));
        assertFalse(validateName("rub1"));
        assertFalse(validateName("RuB312"));
        assertFalse(validateName("12"));

    }
}
