package com.example.cli;

public class MenuEntry<T>{
    String description;
    T value;

    public MenuEntry(String description, T value){
        this.description = description;
        this.value = value;
    }

    @Override
    public String toString(){
        return description;
    }

    public T getValue(){
        return value;
    }
}
