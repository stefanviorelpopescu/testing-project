package org.example.testing_project.service;

import lombok.RequiredArgsConstructor;
import org.example.testing_project.controller.exception.InvalidPensionerException;
import org.example.testing_project.dao.Pensioner;
import org.example.testing_project.dao.PensionerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PensionerService {

    private final PensionerRepository pensionerRepository;

    public Pensioner createPensioner(Pensioner pensioner) throws InvalidPensionerException {
        validateCreatePayload(pensioner);
        return pensionerRepository.save(pensioner);
    }

    private void validateCreatePayload(Pensioner pensioner) throws InvalidPensionerException {
        if (pensioner.getId() != null) {
            throw new InvalidPensionerException("Id should be null when creating pensioners");
        }
        if (pensioner.getName() == null || pensioner.getName().isBlank()) {
            throw new InvalidPensionerException("Name should be present when creating pensioners");
        }
        if (pensioner.getAge() == null || pensioner.getAge() < 18) {
            throw new InvalidPensionerException("Age should be > 18 when creating pensioners");
        }
    }

    public boolean deletePensioner(long id) {
        return pensionerRepository.findById(id).map(pensioner -> {
            pensionerRepository.delete(pensioner);
            return true;
        }).orElse(false);
    }
}
