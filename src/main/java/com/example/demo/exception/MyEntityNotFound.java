package com.example.demo.exception;

public class MyEntityNotFound extends IllegalArgumentException{

    public MyEntityNotFound() {
        super("Entity Not Found");
    }
}
