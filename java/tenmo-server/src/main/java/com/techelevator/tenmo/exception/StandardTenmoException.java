package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "You broke the internet. Something went wrong.")
public class StandardTenmoException extends Exception {
    private static final long serialVersionUID = 1L;

    public StandardTenmoException() { super("You broke the internet. Something went wrong."); }
}
