package exceptions;

public class SpecialeTekensInNaamException extends RuntimeException {

    public SpecialeTekensInNaamException() {
    }
    
    public SpecialeTekensInNaamException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public SpecialeTekensInNaamException(Throwable cause)
    {
        super(cause);
    }

    public SpecialeTekensInNaamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public SpecialeTekensInNaamException(String msg) {
        super(msg);
    }
}
    


    