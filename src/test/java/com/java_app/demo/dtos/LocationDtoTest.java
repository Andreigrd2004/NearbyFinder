package com.java_app.demo.dtos;

import com.java_app.demo.location.dto.LocationDto;
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

class LocationDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest(name = "Field ''{2}'' should be invalid with value: ''{1}''")
    @MethodSource("invalidFieldProvider")
    void whenFieldIsInvalid_thenValidationFails(
            BiConsumer<LocationDto, String> fieldSetter, String invalidValue, String fieldName) {

        LocationDto dto = getValidDto();

        fieldSetter.accept(dto, invalidValue);

        Set<ConstraintViolation<LocationDto>> violations = validator.validate(dto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<LocationDto> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo(fieldName);
        assertThat(violation.getMessage()).isEqualTo("must not be blank");
    }

    private static Stream<Arguments> invalidFieldProvider() {

        Stream<String> invalidValues = Stream.of(null, "", "   ");


        return invalidValues.flatMap(value -> Stream.of(
                Arguments.of((BiConsumer<LocationDto, String>) LocationDto::setQuery, value, "query"),
                Arguments.of((BiConsumer<LocationDto, String>) LocationDto::setStatus, value, "status"),
                Arguments.of((BiConsumer<LocationDto, String>) LocationDto::setCountry, value, "country"),
                Arguments.of((BiConsumer<LocationDto, String>) LocationDto::setRegionName, value, "regionName"),
                Arguments.of((BiConsumer<LocationDto, String>) LocationDto::setCity, value, "city"),
                Arguments.of((BiConsumer<LocationDto, String>) LocationDto::setZip, value, "zip"),
                Arguments.of((BiConsumer<LocationDto, String>) LocationDto::setLat, value, "lat"),
                Arguments.of((BiConsumer<LocationDto, String>) LocationDto::setLon, value, "lon"),
                Arguments.of((BiConsumer<LocationDto, String>) LocationDto::setIsp, value, "isp"),
                Arguments.of((BiConsumer<LocationDto, String>) LocationDto::setCurrency, value, "currency")
        ));
    }

    private LocationDto getValidDto() {
        return new LocationDto(
                "85.186.22.132",
                "success",
                "Romania",
                "Suceava",
                "Suceava",
                "727234",
                "47.6333",
                "26.2500",
                "UPC Romania Suceava",
                "RON"
        );
    }
}