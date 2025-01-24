package com.example.Todo.entity;

public enum Gender {
    MALE('M'),
    FEMALE('F'),
    NONE('N');

    private final char gender;

    Gender(char gender) {
        this.gender = gender;
    }

}
