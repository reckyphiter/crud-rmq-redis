package com.reckyphiter.crudcommon.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author Recky Phiter
 */
public class Check {

    public static void nonNull(@Nullable Object object, @Nonnull String errorMessage) {
        if (null == object) {
            throw new NullPointerException(errorMessage);
        }
    }

    public static void nonEmpty(@Nullable Collection collection, @Nonnull String errorMessage) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void hasText(@Nullable CharSequence text, @Nonnull String errorMessage) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static <T> void validateModel(@Nonnull Validator validator, @Nonnull T object) {
        nonNull(validator, "Validator required !");
        nonNull(object, "Model required");

        final Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!CollectionUtils.isEmpty(violations)) {
            final StringBuilder sb = new StringBuilder();

            for (ConstraintViolation<T> violation : violations) {
                final String comma = StringUtils.isBlank(sb.toString()) ? StringUtils.EMPTY : ", ";
                sb.append(StringUtils.join(comma, violation.getMessage()));
            }

            throw new IllegalArgumentException(sb.toString());
        }
    }
}
