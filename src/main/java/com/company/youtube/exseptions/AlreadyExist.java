package com.company.youtube.exseptions;

public class AlreadyExist extends RuntimeException {

    public AlreadyExist(String message) {
        super(message);
    }
}
