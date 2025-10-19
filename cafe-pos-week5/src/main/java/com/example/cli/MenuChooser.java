package com.example.cli;

import java.util.Map;
import java.util.Scanner;

public class MenuChooser<T> {
    private final Map<Integer, T> options;
    private final String menu_title;

    public MenuChooser(Map<Integer, T> options, String menu_title) {
        this.options = options;
        this.menu_title = menu_title;
    }

    public T display() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nPlease choose " + menu_title + ":");
            for (Map.Entry<Integer, T> entry : options.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            int choice = scanner.nextInt();
            if (!options.containsKey(choice)) {
                System.out.println("Invalid choice");
                continue;
            }
            return options.get(choice);
        }
    }
}
