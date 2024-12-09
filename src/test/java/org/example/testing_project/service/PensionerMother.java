package org.example.testing_project.service;

import org.example.testing_project.dao.Pensioner;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class PensionerMother {

    public static long DEFAULT_ID = 1L;
    public static String DEFAULT_NAME = "Dorel";
    public static int DEFAULT_AGE = 18;

    public static Pensioner getPensioner(Long id, String name, Integer age) {
        return Pensioner.builder()
                .id(id)
                .name(name)
                .age(age)
                .build();
    }

    public static Stream<Arguments> getInvalidPensioners() {
        return Stream.of(
                Arguments.of(PensionerMother.getPensioner(DEFAULT_ID, DEFAULT_NAME, DEFAULT_AGE), "Id should be null when creating pensioners"),
                Arguments.of(PensionerMother.getPensioner(null, null, DEFAULT_AGE), "Name should be present when creating pensioners"),
                Arguments.of(PensionerMother.getPensioner(null, "  ", DEFAULT_AGE), "Name should be present when creating pensioners"),
                Arguments.of(PensionerMother.getPensioner(null, DEFAULT_NAME, 5), "Age should be > 18 when creating pensioners"),
                Arguments.of(PensionerMother.getPensioner(null, DEFAULT_NAME, 17), "Age should be > 18 when creating pensioners"),
                Arguments.of(PensionerMother.getPensioner(null, DEFAULT_NAME, null), "Age should be > 18 when creating pensioners")
        );
    }
}
