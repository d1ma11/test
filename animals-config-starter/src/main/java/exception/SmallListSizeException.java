package exception;

/**
 * Исключение {@code SmallListSizeException} генерируется, когда размер списка меньше ожидаемого.
 * Оно расширяет стандартный класс {@link Exception}, указывая, что это checked исключение.
 * Это исключение полезно для проверки размера списков перед выполнением операций, требующих минимального размера.
 */
public class SmallListSizeException extends Exception {
    public SmallListSizeException(String errorMessage) {
        super(errorMessage);
    }
}
