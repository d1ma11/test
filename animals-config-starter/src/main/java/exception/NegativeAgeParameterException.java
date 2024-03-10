package exception;

public class NegativeAgeParameterException extends IllegalArgumentException{
    public NegativeAgeParameterException(String errorMessage) {
        super(errorMessage);
    }
}
