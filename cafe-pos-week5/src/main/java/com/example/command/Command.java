package com.example.command;

public interface Command {
        void execute();
        default void undo() { /* optional */ }
    }
