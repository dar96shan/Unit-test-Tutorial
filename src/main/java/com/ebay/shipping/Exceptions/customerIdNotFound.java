package com.ebay.shipping.Exceptions;

public class customerIdNotFound extends Exception{

    String resourceName;
     Long id;

    public customerIdNotFound(String resourcename, Long id) {
        super(String.format("%s id is not found in the database : %s " ,resourcename, id));
        this.resourceName = resourcename;
        this.id = id;
    }
}
