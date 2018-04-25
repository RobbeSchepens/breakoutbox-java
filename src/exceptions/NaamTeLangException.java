package exceptions;

public class NaamTeLangException extends RuntimeException {

    public NaamTeLangException() {
    }
    
    public NaamTeLangException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public NaamTeLangException(Throwable cause)
    {
        super(cause);
    }

    public NaamTeLangException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public NaamTeLangException(String msg) {
        super(msg);
    }
}
    


    