package ru.ac.uniyar.testingcourse;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class LinearEquationSolverTest {

    /**
     * Проверка, правильно ли находиться корень
     * Дано: a = 2, b = 4
     * Ожидаемый результат: 2.0 (4/2)
     */
    @Test
    public void LinearEquationSolverOneRootTest(){
        assertThat(LinearEquationSolver.solve(2, 4)).isEqualTo(2.0);
    }

    /**
     * Проверка, правильно ли обрабатывается деление на 0
     * Дано: a = 0, b = 2
     * Ожидаемый результат: 0 (2/0)
     */
    @Test
    public void LinearEquationSolverA0(){
        assertThat(LinearEquationSolver.solve(0, 2)).isNull();
    }

    /**
     * Проверка, правильно ли обрабатывается ситуации при бесконечном множестве корней
     * Дано: a = 0, b = 0
     * Ожидаемый результат: Исключение, то есть x - любое
     */
    @Test
    void LinearEquationSolverA0B0(){
        assertThatThrownBy(() -> LinearEquationSolver.solve(0, 0)).isInstanceOf(LinearEquationSolver.AnyNumberIsRootException.class);
    }
}
