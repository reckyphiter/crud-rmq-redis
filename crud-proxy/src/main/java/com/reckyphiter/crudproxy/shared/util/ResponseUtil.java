package com.reckyphiter.crudproxy.shared.util;

import com.reckyphiter.crudproxy.shared.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Recky Phiter
 */
public class ResponseUtil {

    public static ResponseEntity composeResponse(Response response) {
        final HttpStatus httpStatus = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity(response, httpStatus);
    }
}
