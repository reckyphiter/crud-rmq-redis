package com.reckyphiter.crudworker.shared.model;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author Recky Phiter
 */
@Data
public class QueueMessage<T> implements Serializable {

    private String id;

    private T message;

    public QueueMessage() {
    }

    public QueueMessage(String id, T message) {
        this.id = id;
        this.message = message;
    }
}
