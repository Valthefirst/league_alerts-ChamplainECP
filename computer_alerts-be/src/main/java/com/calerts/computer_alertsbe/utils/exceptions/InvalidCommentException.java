package com.calerts.computer_alertsbe.utils.exceptions;

public class InvalidCommentException extends RuntimeException{

        public InvalidCommentException() {}

        public InvalidCommentException(String message) { super(message); }

        public InvalidCommentException(Throwable cause) { super(cause); }

        public InvalidCommentException(String message, Throwable cause) { super(message, cause); }
}
