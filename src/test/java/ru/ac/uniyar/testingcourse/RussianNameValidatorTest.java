package ru.ac.uniyar.testingcourse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;


public class RussianNameValidatorTest {
    RussianNameValidator name = new RussianNameValidator();


    /**
     * Проверка в случае ввода пустой строки
     * Дано: ""
     * Ожидаемый результат: false
     */
    @DisplayName("Ввод пустой строки")
    @Test
    void RussianNameValidatorIsEmpty() {
        boolean value = name.validate("");
        assertThat(value).isEqualTo(false);
    }


    /**
     * Проверка в случае ввода null
     * Дано: null
     * Ожидаемый результат: Исключение
     */
    @DisplayName("Ввод null")
    @Test()
    void russianNameValidatorNull(){
        assertThatThrownBy(() -> name.validate(null)).isInstanceOf(NullPointerException.class);
    }


    /**
     * Проверка в случае ввода строки из пробелов, табуляций или переносов строк
     * Дано: "   "
     * Ожидаемый результат: false
     */
    @DisplayName("Ввод из пробелов табуляций или переносов строки")
    @ParameterizedTest(name = "{0} = false")
    @ValueSource(strings = {"   ", "\t\t\t", "\n\n\n"})
    void russianNameValidatorSpaces(String fio){
        boolean value = name.validate(fio);
        assertThat(value).isEqualTo(false);
    }


    /**
     * Проверка в случае, когда имя, фамилия или/и отчество - это 1 буква
     * Дано: {"И Иван Иванович", "Иванов И Иванович", "Иванов Иван И", "И А", "И И И"}
     * Ожидаемый результат: true
     */
    @DisplayName("ФИО из 1 буквы")
    @ParameterizedTest(name = "{0} = true")
    @ValueSource(strings = {"И Иван Иванович", "Иванов И Иванович", "Иванов Иван И", "И А", "И И И"})
    void russianNameValidatorMinimalSizeInput(String fio){
        boolean value = name.validate(fio);
        assertThat(value).isEqualTo(true);
    }


    /**
     * Проверка в случае корректного ФИО
     * Дано: {"Иванов Иван Иванович", "Иванов Иван", "Иванов Иван-Петр Иванович", "Иванов-Петров Иван Иванович"
     *        "Иванов-Петров Иван-Петр Иванович"}
     * Ожидаемый результат: true
     */
    @DisplayName("Корректный ввод")
    @ParameterizedTest(name = "{0} = true")
    @ValueSource(strings = {"Иванов Иван Иванович", "Иванов Иван", "Иванов Иван-Петр Иванович",
            "Иванов-Петров Иван Иванович", "Иванов-Петров Иван-Петр Иванович"})
    void russianNameValidatorCorrect(String fio) {
        boolean value = name.validate(fio);
        assertThat(value).isEqualTo(true);
    }


    /**
     * Проверка в случае корректного ФИО, использование букв ё/Ё
     * Имя, фамилия, отчество с буквой ё
     * Имя, фамилия, отчество с буквой Ё
     * Дано: {"Иванов-Пётров Иван Иванович", "Иванов-Петров Иван-Пётр Иванович",
     *             "Иванов Иван Иёанович", "Иванов-Ётров Иван Иванович", "Иванов-Петров Иван-Ётр Иванович",
     *             "Иванов Иван-Петрович Ёванович"}
     * Ожидаемый результат: true (было исправлено)
     */
    @DisplayName("Ввод содержащий букву Ё/ё")
    @ParameterizedTest(name = "{0} = true")
    @ValueSource(strings = {"Иванов-Пётров Иван Иванович", "Иванов-Петров Иван-Пётр Иванович",
            "Иванов Иван Иёанович", "Иванов-Ётров Иван Иванович", "Иванов-Петров Иван-Ётр Иванович",
            "Иванов Иван-Петрович Ёванович"})
    void russianNameValidatorLetterIo(String fio) {
        boolean value = name.validate(fio);
        assertThat(value).isEqualTo(true);
    }


    /**
     * Проверка в случае некорректного ФИО, присутствие специальных символов, цифр, не русских букв
     * Дано: {specialCharacter()}
     * Ожидаемый результат: false
     */
    // ! ? < > / \ | , . ' " : ; @ # $ % ^ & * ( ) { } [ ] ` ~ _
    @DisplayName("Ввод со спец. символами, цифрами и английскими буквами")
    @ParameterizedTest(name = "{0} = false")
    @MethodSource("specialCharacter")
    void russianNameValidatorSpecialCharacter(String fio) {
        boolean value = name.validate(fio);
        assertThat(value).isEqualTo(false);
    }

    private static Stream<Arguments> specialCharacter() {
        String str = "!?<>/\\|,.'\":;@#$%^&*(){}[]`~_abcd0123456789";
        Stream<Arguments> result = str.chars().
                mapToObj(c -> "Ив" + (char)c + "анов Иван") // Превращаем один объект в другой
                .map(s -> arguments(s)); // Делаем аргументы
        return result; // Преобразует каждый символ в строку
    }


    /**
     * Проверка в случае некорректного ФИО
     * Отсутствие имени, фамилии, имя с маленькой буквы, фамилия с маленькой буквы, отчество с маленькой буквы,
     * второе имя с маленькой буквы, вторая фамилия с маленькой буквы.
     * Использование заглавных букв не в начале имени, фамилии и/или отчества
     * Дано: {"Иванов  Иванович", " Иван Иванович", "Иванов иван Иванович", "иванов Иван Иванович",
     *        "Иванов Иван иванович", "Иванов-петров Иван Иванович", "Иванов Иван-петр Иванович"}
     * Ожидаемый результат: false
     */
    @DisplayName("Ввод не удовлетворяющий формату ввода")
    @ParameterizedTest(name = "{0} = false")
    @ValueSource(strings = {"Иванов  Иванович", "  Иван Иванович", "Иванов иван Иванович", "иванов Иван Иванович",
                            "Иванов Иван иванович", "Иванов-петров Иван Иванович", "Иванов Иван-петр Иванович",
                            "ИВАНОВ Иван Иванович", "Иванов ИВАН Иванович", "Ивано Иван ИВАНОВИЧ",
                            "ИВАНОВ ИВАН ИВАНОВИЧ"})
    void russianNameValidatorNoCorrectFormFio(String fio) {
        boolean value = name.validate(fio);
        assertThat(value).isEqualTo(false);
    }


    /**
     * Проверка в случае некорректного ФИО
     * Пробелы в конце ввода, табуляции или переноса строки в начале и в конце ввода и между именем, фамилий и отчеством.
     * Дано: {"Иванов Иванович     ", "       Иван Иванович", " Иван      Иванович",
     *             "Иван Иванович       ", "Иванов Иван        Иванович", "Иванов Иван Иванович        "}
     * Ожидаемый результат: false
     */
    @DisplayName("Ввод содержащий \\t, \\n, вместо пробелов")
    @ParameterizedTest(name = "{0} = false")
    @ValueSource(strings = {"Иванов Иванович\t", "\tИван Иванович", " Иван\tИванович",
            "Иван Иванович\t", "Иванов Иван\tИванович", "Иванов Иван Иванович\t",
            "Иванов Иванович\n", "\nИван Иванович", " Иван\nИванович", "Иван Иванович\n", "Иванов Иван\nИванович",
            "Иванов Иван Иванович\n"})
    void russianNameValidatorNoCorrectSpaceSymbols(String fio) {
        boolean value = name.validate(fio);
        assertThat(value).isEqualTo(false);
    }


    /**
     * Проверка в случае некорректного ФИО
     * Дефис в начале или конце ввода, попытка ввода тройного имени или фамилии, попытка ввода двойного отчества,
     * ввод двойного тире между именами или фамилиями
     * Дано: {"Иванов-Ив-Ив Иван Иванович", "Иванов Иван-Ив-Ив Иванович", "Иванов Иван Иванович-Ив",
     *        "Иванов--Ив Иван Иванович", "Иванов Иван--Ив Иванович", "-Иванов Иван Иванович",
     *        "Иванов Иван Иванович-", "Иванов Иван-"}
     * Ожидаемый результат: false
     */
    @DisplayName("Ввод с лишними дефис и тройными имена и фамилиями")
    @ParameterizedTest(name = "{0} = false")
    @ValueSource(strings = {"Иванов-Ив-Ив Иван Иванович", "Иванов Иван-Ив-Ив Иванович", "Иванов Иван Иванович-Ив",
                            "Иванов--Ив Иван Иванович", "Иванов Иван--Ив Иванович", "-Иванов Иван Иванович",
                            "Иванов Иван Иванович-", "Иванов Иван-"})
    void russianNameValidatorNoCorrectMoreDash(String fio) {
        boolean value = name.validate(fio);
        assertThat(value).isEqualTo(false);
    }
}