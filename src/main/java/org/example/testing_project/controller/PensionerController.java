package org.example.testing_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.testing_project.controller.exception.InvalidPensionerException;
import org.example.testing_project.dao.Pensioner;
import org.example.testing_project.service.PensionerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pensioners")
@RequiredArgsConstructor
public class PensionerController {

    private final PensionerService pensionerService;

    @PostMapping
    public Pensioner createPensioner(@RequestBody Pensioner pensioner) throws InvalidPensionerException {
        return pensionerService.createPensioner(pensioner);
    }

    @DeleteMapping("/{id}")
    public boolean deletePensioner(@PathVariable long id) {
        return pensionerService.deletePensioner(id);
    }

    @ExceptionHandler(InvalidPensionerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidPensionerException(InvalidPensionerException exception) {
        return exception.getMessage();
    }

}
