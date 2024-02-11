package hw3.service.helper;

import java.time.LocalDate;
import java.time.Period;

public final class SearchUtilityClass {

    private SearchUtilityClass() {}

    /**
     * Проверка года на високосность
     *
     * @param year
     * @return true - если високосный, иначе - false
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    /**
     * Возвращает разницу в годах между текущей и переданной даты.
     *
     * @param birthDate
     * @return возраст животноого
     */
    public static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
