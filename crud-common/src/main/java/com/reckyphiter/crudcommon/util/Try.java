package com.reckyphiter.crudcommon.util;

import javax.annotation.Nonnull;

/**
 *
 * @author Recky Phiter
 */
public interface Try {

    static <T> T of(@Nonnull LambdaValue<T> lambdaValue) {
        Check.nonNull(lambdaValue, "Lambda expression required !");

        try {
            return lambdaValue.get();
        } catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
    }

    static <T> T of(@Nonnull LambdaValue<T> lambdaValue, @Nonnull String errorMessage) {
        Check.nonNull(lambdaValue, "Lambda expression required !");
        Check.hasText(errorMessage, "Error message required !");

        try {
            return lambdaValue.get();
        } catch (Throwable throwable) {
            throw new IllegalArgumentException(errorMessage, throwable);
        }
    }

    static <T> T ofDefault(@Nonnull LambdaValue<T> lambdaValue, T defaultValue) {
        Check.nonNull(lambdaValue, "Lambda expression required !");

        try {
            return lambdaValue.get();
        } catch (Throwable throwable) {
            return defaultValue;
        }
    }

    static <T> T ofNullable(@Nonnull LambdaValue<T> lambdaValue) {
        return ofDefault(lambdaValue, null);
    }

    static <T> T ofVoid(@Nonnull LambdaVoid lambdaVoid) {
        return of(() -> {
            lambdaVoid.action();
            return null;
        });
    }

    static <T> T ofVoid(@Nonnull LambdaVoid lambdaVoid, @Nonnull String errorMessage) {
        return of(() -> {
            lambdaVoid.action();
            return null;
        }, errorMessage);
    }

    static <T> T ofVoidSafe(@Nonnull LambdaVoid lambdaVoid) {
        return ofNullable(() -> {
            lambdaVoid.action();
            return null;
        });
    }
}
