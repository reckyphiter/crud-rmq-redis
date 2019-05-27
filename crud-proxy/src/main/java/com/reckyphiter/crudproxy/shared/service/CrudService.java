package com.reckyphiter.crudproxy.shared.service;

import javax.annotation.Nonnull;

/**
 *
 * @author Recky Phiter
 */
public interface CrudService<T, ID, Result> {
    
    Result add(@Nonnull T param);

    Result update(@Nonnull T param);

    Result delete(@Nonnull ID param);

    T get(@Nonnull ID param);
}
