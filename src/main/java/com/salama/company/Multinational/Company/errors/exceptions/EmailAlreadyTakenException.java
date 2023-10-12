package com.salama.company.Multinational.Company.errors.exceptions;

public class EmailAlreadyTakenException extends RuntimeException {

    public EmailAlreadyTakenException() {
        super("Email already taken");
    }

}
