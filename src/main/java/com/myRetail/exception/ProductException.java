package com.myRetail.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductException extends Throwable{
    private String message;
}
