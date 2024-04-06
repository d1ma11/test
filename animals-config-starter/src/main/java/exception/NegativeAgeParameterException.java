package exception;

/**
 * Исключение {@code NegativeAgeParameterException} генерируется, когда в качестве параметра age передается
 * отрицательное значение, что недопустимо.
 * Оно расширяет стандартный класс {@link IllegalArgumentException}, указывая, что это unchecked исключение.
 * Данное исключение полезно для проверки параметров возраста, чтобы убедиться, что они неотрицательны
 */
public class NegativeAgeParameterException extends IllegalArgumentException {
    public NegativeAgeParameterException(String errorMessage) {
        super(errorMessage);
    }
}
