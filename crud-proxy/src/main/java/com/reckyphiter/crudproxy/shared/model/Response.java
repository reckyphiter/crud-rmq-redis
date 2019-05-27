package com.reckyphiter.crudproxy.shared.model;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author Recky Phiter
 */
@Data
public class Response implements Serializable {

    private int code;

    private String message;

    public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
