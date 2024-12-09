package org.example.testing_project.service;

import org.example.testing_project.controller.exception.InvalidPensionerException;
import org.example.testing_project.dao.Pensioner;
import org.example.testing_project.dao.PensionerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.example.testing_project.service.PensionerMother.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PensionerServiceTest {

    @Mock
    private PensionerRepository pensionerRepository;
    @InjectMocks
    private PensionerService pensionerService;

    @Test
    public void saveToRepositorySuccess() throws InvalidPensionerException {
        //given
        Pensioner pensioner = getPensioner(null, DEFAULT_NAME, DEFAULT_AGE);
        Pensioner savedPensioner = getPensioner(1L, DEFAULT_NAME, DEFAULT_AGE);
        when(pensionerRepository.save(eq(pensioner))).thenReturn(savedPensioner);

        //when
        Pensioner actual = pensionerService.createPensioner(pensioner);

        //then
        verify(pensionerRepository).save(eq(pensioner));
        assertEquals(savedPensioner, actual);
    }

    @ParameterizedTest
    @MethodSource("org.example.testing_project.service.PensionerMother#getInvalidPensioners")
    public void whenPayloadInvalidExceptionIsThrown(Pensioner pensioner, String exceptionMessage) {
        //when
        InvalidPensionerException invalidPensionerException =
                assertThrows(InvalidPensionerException.class, () -> pensionerService.createPensioner(pensioner));

        //then
        verifyNoInteractions(pensionerRepository);
        assertEquals(exceptionMessage, invalidPensionerException.getMessage());
    }

    @Test
    public void whenIdIsPresentDeleteReturnsTrue() {
        //given
        long pensionerId = 5;
        Pensioner foundPensioner = getPensioner(pensionerId, DEFAULT_NAME, DEFAULT_AGE);
        when(pensionerRepository.findById(eq(pensionerId)))
                .thenReturn(Optional.of(foundPensioner));

        //when
        boolean result = pensionerService.deletePensioner(pensionerId);

        //then
        verify(pensionerRepository).delete(eq(foundPensioner));
        assertTrue(result);
    }

    @Test
    public void whenIdIsNotPresentDeleteReturnsFalse() {
        //given
        long pensionerId = 5;
        when(pensionerRepository.findById(eq(pensionerId))).thenReturn(Optional.empty());

        //when
        boolean result = pensionerService.deletePensioner(pensionerId);

        //then
        verify(pensionerRepository, never()).delete(any());
        assertFalse(result);
    }

}