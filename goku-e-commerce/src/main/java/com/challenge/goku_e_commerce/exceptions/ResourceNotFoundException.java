package com.challenge.goku_e_commerce.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Object id){
        super("Object Id: " + id + " not Found:");
    }
}
