package io.spring.batch.example.domain;

import lombok.AllArgsConstructor;

public class CustomerUpdate {
    protected final long customerId;

    public CustomerUpdate(long customerId) {
        this.customerId = customerId;
    }
}
