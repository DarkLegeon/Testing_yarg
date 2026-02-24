package ru.ac.uniyar.testingcourse;

/**
 * Класс счетчика, который позволяет увеличить значение счетчика,
 * получить текущее значение и сбросить счетчик.
 */
public class Counter {
    private int value = 0;

    /** Инициализирует счетчик с 0 в качестве начального значения. */
    public Counter() {
    }

    /** Возвращает текущее значение счетчика */
    public int getValue() {
        return value;
    }

    /** Добавляет 1 к значению счетчика. */
    public void increase() {
        ++value;
    }

    /** Сбрасывает значение счетчика на 0. */
    public void reset() {
        value = 1;
    }
}
