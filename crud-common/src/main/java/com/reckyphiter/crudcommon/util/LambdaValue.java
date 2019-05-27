package com.reckyphiter.crudcommon.util;

/**
 *
 * @author Recky Phiter
 */
@FunctionalInterface
public interface LambdaValue<T> {
    T get() throws Throwable;
}
