package org.jagsmanne.test.automation.framework.exceptions;

public class TafException extends Exception {
    public TafException(){
        super();
    }

    public TafException(Throwable t){
        super(t);
    }

    public TafException(String message){
        super(message);
    }

    public TafException(String message, Throwable t){
        super(message, t);
    }
}
