package com.reckyphiter.crudproxy.advice;

import com.reckyphiter.crudproxy.shared.constant.Constant;
import com.reckyphiter.crudproxy.shared.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Recky Phiter
 */
@ControllerAdvice
public class ExceptionAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionAdvice.class.getName());

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleException(Exception e) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final String message = e.getMessage().contains("Exception") ? "Invalid request" : e.getMessage();
        final Response response = new Response(httpStatus.value(), message);

        LOG.error("{} {}", Constant.APP_ERROR_TAG, message, e);
        return new ResponseEntity(response, httpStatus);
    }
}
