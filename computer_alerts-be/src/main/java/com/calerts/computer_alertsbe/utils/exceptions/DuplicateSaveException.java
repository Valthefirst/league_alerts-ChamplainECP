package com.calerts.computer_alertsbe.utils.exceptions;

public class DuplicateSaveException extends RuntimeException{
    public DuplicateSaveException() {}
    public DuplicateSaveException(String message) { super(message); }
    public DuplicateSaveException(Throwable cause) { super(cause); }
    public DuplicateSaveException(String message, Throwable cause) { super(message, cause); }
}
