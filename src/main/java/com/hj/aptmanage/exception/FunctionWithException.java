package com.hj.aptmanage.exception;

@FunctionalInterface
public interface FunctionWithException<E extends Exception> {
    void accept() throws E;
}
