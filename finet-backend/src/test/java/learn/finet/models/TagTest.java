package learn.finet.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    @Test
    void emptyTagShouldFailValidation() {
        Tag tag = new Tag();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Tag>> violations = validator.validate(tag);

        assertEquals(1, violations.size());
        assertEquals("Name is required.", violations.iterator().next().getMessage());
    }

    @Test
    void tagWithNonNullIdShouldFailValidation() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("TST");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Tag>> violations = validator.validate(tag);

        assertEquals(1, violations.size());
        assertEquals("Id must be null.", violations.iterator().next().getMessage());
    }

    @Test
    void tagWithNameTooLongShouldFailValidation() {
        Tag tag = new Tag();
        tag.setName("TOOLONGNAMETOOLONGNAME");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Tag>> violations = validator.validate(tag);

        assertEquals(1, violations.size());
        assertEquals("Name must be 20 characters or less.", violations.iterator().next().getMessage());
    }

    @Test
    void tagWithValidNameShouldPassValidation() {
        Tag tag = new Tag();
        tag.setName("TST");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Tag>> violations = validator.validate(tag);

        assertEquals(0, violations.size());
    }
}