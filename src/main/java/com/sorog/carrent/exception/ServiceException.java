package com.sorog.carrent.exception;

public class ServiceException extends Exception {
    public ServiceException(Exception e) {
        super(e);
    }
}
