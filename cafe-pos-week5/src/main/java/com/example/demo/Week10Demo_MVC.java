package com.example.demo;

import com.example.infra.Wiring;
import com.example.ui.ConsoleView;
import com.example.ui.OrderController;

// Same as the one under ui/ here for clarity
public final class Week10Demo_MVC {

    public static void main(String[] args) {
        var c = Wiring.createDefault();
        var controller = new OrderController(c.repo(), c.checkout());
        var view = new ConsoleView();
        long id = 4101L;
        controller.createOrder(id);
        controller.addItem(id, "ESP+SHOT+OAT", 1);
        controller.addItem(id, "LAT+L", 2);
        String receipt = controller.checkout(id, 10);
        view.print(receipt);
    }
}