package com.revolut.bank.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String s) {
    super(s);
  }
}
