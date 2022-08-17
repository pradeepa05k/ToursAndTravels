package com.toursandtravels.util;

/**
* The TATException wraps all unchecked exception in TAT App and enriches them with a custom error message.
*/

public class TATException extends RuntimeException {
    public TATException(String message, Throwable cause) {
        super(message, cause);
    }   
    
    public TATException(String message) {
        super(message);
    }       
}