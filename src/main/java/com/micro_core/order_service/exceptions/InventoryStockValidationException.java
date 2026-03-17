package com.micro_core.order_service.exceptions;


public class InventoryStockValidationException extends RuntimeException {
    public InventoryStockValidationException(String message) {
        super(message);
    }
}
