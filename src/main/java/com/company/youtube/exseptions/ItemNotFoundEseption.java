package com.company.youtube.exseptions;

public class ItemNotFoundEseption extends RuntimeException {

    public ItemNotFoundEseption(String message) {
        super(message);
    }
}
