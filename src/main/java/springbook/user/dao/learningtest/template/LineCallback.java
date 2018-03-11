package springbook.user.dao.learningtest.template;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
