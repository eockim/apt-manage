package com.hj.aptmanage.exception;

@FunctionalInterface
public interface FunctionWithException<T, E extends Exception> {
    void accept(T t) throws E;
}
