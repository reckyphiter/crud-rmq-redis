package com.reckyphiter.crudproxy.converter;

import com.reckyphiter.crudproxy.employee.model.EmployeeId;
import com.reckyphiter.crudproxy.shared.model.AbstractEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 *
 * @author Recky Phiter
 */
public class StringToAbstractEntityConverterFactory implements ConverterFactory<String, AbstractEntity> {

    @Override
    public <T extends AbstractEntity> Converter<String, T> getConverter(Class<T> targetClass) {
        return new StringToAbstractEntityConverter<>(targetClass);
    }

    private static class StringToAbstractEntityConverter<T extends AbstractEntity>
            implements Converter<String, T> {

        private Class<T> targetClass;

        public StringToAbstractEntityConverter(Class<T> targetClass) {
            this.targetClass = targetClass;
        }

        @Override
        public T convert(String source) {
            if(this.targetClass == EmployeeId.class) {
                final EmployeeId employeeId = new EmployeeId();
                employeeId.setId(source);
                return (T) employeeId;
            } else {
                return null;
            }
        }
    }
}
