package springbook.user.dao.learningtest.template;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

public class CalSumTest {
    Calculator calculator;
    String newFilepath;

    @Before
    public void setUp() {
        this.calculator = new Calculator();
        this.newFilepath = getClass().getResource("/numbers.txt").getPath();

    }
    @Test
    public void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(this.newFilepath), is(10));
    }
    @Test
    public void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiply(this.newFilepath), is(24));
    }
    @Test
    public void concatenateStrings() throws IOException {
        assertThat(calculator.concatenate(this.newFilepath), is("1234"));
    }

}
