package com.cs.project.uber.uberApp.util;


import com.cs.project.uber.uberApp.exceptions.RuntimeConflictException;

public class Utils {

    public static void throwRuntimeException(String message) {
        throw new RuntimeException(message);
    }

    public static void throwRuntimeConflictException(String message) {
        throw new RuntimeConflictException(message);
    }

}
