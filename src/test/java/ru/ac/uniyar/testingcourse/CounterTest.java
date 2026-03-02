package ru.ac.uniyar.testingcourse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CounterTest {
    Counter counter = new Counter();
    /**
     * Проверка, корректно ли метод getValue возвращает значение счётчика,
     * в случае когда счётчик увеличивается или имеет изначальное состояние
     * Дано: {0, 1, 2, 5, 50, 999}
     * Ожидаемый результат: {0, 1, 2, 5, 50, 999} соответственно
     */
    @DisplayName("Проверка getValue + increase")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 5, 50, 999})
    void CounterGetValueTestPlusIncrease(int numbers) {
        for(int i = 0; i < numbers; i++)
            counter.increase();

        int value = counter.getValue();

        assertThat(value).isEqualTo(numbers);
    }

    /**
     * Проверка, корректно ли метод getValue() возвращает значение счётчика,
     * после сбрасывания счётчика методом reset() + корректно ли метод reset()
     * сбрасывает значение счётчика после метода increase()
     * Дано: {0, 1, 2, 5, 50, 999}
     * Ожидаемый результат: 0
     * Полученный результат: 1
     * Некорректно
     */

    @DisplayName("Проверка getValue + increase + reset")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 5, 50, 999})
    void CounterGetValueTestPlusIncreasePlusReset(int numbers) {
        for(int i = 0; i < numbers; i++)
            counter.increase();

        counter.reset();

        int value = counter.getValue();

        assertThat(value).isEqualTo(0);
    }

    @DisplayName("Статическое поле")
    @Test
    void CounterStatic() {
        for(int i = 0; i < 2; i++)
            counter.increase();

        counter.reset();

        Counter counter_2 = new Counter();
        int value = counter_2.getValue();

        assertThat(value).isEqualTo(0);
    }
}
