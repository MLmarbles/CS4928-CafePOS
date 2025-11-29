package com.example.ui;


import com.example.app.CheckoutService;
import com.example.domain.LineItem;
import com.example.domain.Order;
import com.example.domain.OrderRepository;
import com.example.factory.ProductFactory;

public final class OrderController {
    private final OrderRepository repo;
    private final CheckoutService checkout;
    private final ProductFactory factory = new ProductFactory();
    public OrderController(OrderRepository repo, CheckoutService
            checkout) {
        this.repo = repo; this.checkout = checkout;
    }
    public long createOrder(long id) {
        repo.save(new Order(id));
        return id;
    }
    public void addItem(long orderId, String recipe, int qty) {
        Order order = repo.findById(orderId).orElseThrow();
        order.addItem(new LineItem(factory.create(recipe), qty));
        repo.save(order);
    }
    public String checkout(long orderId, int taxPercent) {
        return checkout.checkout(orderId, taxPercent);
    }
}