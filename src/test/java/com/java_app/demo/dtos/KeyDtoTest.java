package com.java_app.demo.dtos;


import com.java_app.demo.apikey.model.KeyDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class KeyDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest(name = "Field ''{2}'' should be invalid with value: ''{1}''")
    @MethodSource("invalidFieldProvider")
    void whenFieldIsInvalid_thenValidationFails(
            BiConsumer<KeyDto, String> fieldSetter, String invalidValue, String fieldName) {

        KeyDto dto = getValidDto();

        fieldSetter.accept(dto, invalidValue);

        Set<ConstraintViolation<KeyDto>> violations = validator.validate(dto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<KeyDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo(fieldName);
        assertThat(violation.getMessage()).isEqualTo("must not be blank");
    }

    private static Stream<Arguments> invalidFieldProvider() {

        Stream<String> invalidValues = Stream.of(null, "", "   ");


        return invalidValues.flatMap(value -> Stream.of(
                Arguments.of((BiConsumer<KeyDto, String>) KeyDto::setValue, value, "value"),
                Arguments.of((BiConsumer<KeyDto, String>) KeyDto::setName, value, "name")
        ));
    }

    private KeyDto getValidDto() {
    return new KeyDto(
        "wkedborgnflowfe",
            "CeCheie");
    }
}
