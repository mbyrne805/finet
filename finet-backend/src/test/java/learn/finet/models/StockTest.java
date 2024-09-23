package learn.finet.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void emptyStockShouldFailValidation() {
        Stock stock = new Stock();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Stock>> violations = validator.validate(stock);

        assertEquals(1, violations.size());
        assertEquals("Symbol is required.", violations.iterator().next().getMessage());
    }

    @Test
    void stockWithNonNullIdShouldFailValidation() {
        Stock stock = new Stock();
        stock.setId(1L);
        stock.setSymbol("TST");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Stock>> violations = validator.validate(stock);

        assertEquals(1, violations.size());
        assertEquals("Id must be null.", violations.iterator().next().getMessage());
    }

    @Test
    void stockWithSymbolTooLongShouldFailValidation() {
        Stock stock = new Stock();
        stock.setSymbol("TOOLONGSYMBOL");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Stock>> violations = validator.validate(stock);

        assertEquals(1, violations.size());
        assertEquals("Symbol must be 7 characters or less.", violations.iterator().next().getMessage());
    }

    @Test
    void stockWithValidSymbolShouldPassValidation() {
        Stock stock = new Stock();
        stock.setSymbol("TST");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Stock>> violations = validator.validate(stock);

        assertEquals(0, violations.size());
    }
}