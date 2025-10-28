package com.example;

import com.example.cli.MenuChooser;
import com.example.cli.MenuEntry;
import com.example.common.Money;
import com.example.common.Priced;
import com.example.common.Product;
import com.example.common.SimpleProduct;
import com.example.decorator.*;
import com.example.domain.*;
import com.example.payment.CardPayment;
import com.example.payment.CashPayment;
import com.example.payment.PaymentStrategy;
import com.example.payment.WalletPayment;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

public class Main2 {
    public static void main(String[] args) {
        // All menus for the CLI
        Map<Integer, Product> products = new HashMap<>() {{
            put(1, new SimpleProduct("ESP", "Espresso", Money.of(3.00)));
            put(2, new SimpleProduct("LAT", "Latte", Money.of(4.00)));
            put(3, new SimpleProduct("CAP", "Cappuccino", Money.of(4.50)));
            put(4, new SimpleProduct("AMER", "Americano", Money.of(2.50)));
        }};

        Map<Integer, MenuEntry<Function<Product, Product>>> extras = new HashMap<>() {{
            put(1, new MenuEntry<>("Extra Shot", ExtraShot::new));
            put(2, new MenuEntry<>("Oat Milk", OatMilk::new));
            put(3, new MenuEntry<>("Syrup", Syrup::new));
            put(4, new MenuEntry<>("No Extras", p -> p));
        }};

        Map<Integer, String> payments = new HashMap<>() {{
            put(1, "Cash");
            put(2, "Credit Card");
            put(3, "Wallet");
        }};

        MenuChooser<Product> productChooser = new MenuChooser<>(products, "a product");
        MenuChooser<MenuEntry<Function<Product, Product>>> extraChooser = new MenuChooser<>(extras, "an extra");
        MenuChooser<String> paymentChooser = new MenuChooser<>(payments, "a payment method");

        Scanner scanner = new Scanner(System.in);
        List<Product> order = new ArrayList<>();

        System.out.println("\nWelcome to Meowcafe!");

        boolean product_loop = true;
        while (product_loop) { // allows multiple products to be added
            Product baseProduct = productChooser.display();

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            Product decorated = baseProduct;
            boolean addingExtras = true;
            while (addingExtras) { // allow multiple extrs to be added
                MenuEntry<Function<Product, Product>> extraChoice = extraChooser.display();
                decorated = extraChoice.getValue().apply(decorated);
                if(extraChoice.toString().equals("No Extras")) {
                    addingExtras = false;
                }
            }

            for (int i = 0; i < quantity; i++) {
                order.add(decorated);
            }

            System.out.print("Add another product? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("n")) {
                product_loop = false;
            }
        }

        // Order
        var kitchen_display = new KitchenDisplay();
        var delivery_desk = new DeliveryDesk();
        var customer_notifier = new CustomerNotifier();
        Order product_order = new Order(OrderIds.next());

        for (Product p : order) {
            product_order.register(kitchen_display);
            product_order.register(delivery_desk);
            product_order.register(customer_notifier);
            product_order.addItem(new LineItem(p, 1));
        }

        product_order.markReady();

        String chosenPayment = paymentChooser.display();
        PaymentStrategy paymentMethod =  null;
        switch(chosenPayment) {
            case "Cash":
                paymentMethod= new CashPayment();
                break;
            case "Credit Card":
                System.out.print("Enter card number: ");
                String cardNumber = scanner.nextLine();
                paymentMethod = new CardPayment(cardNumber);
                break;
            case "Wallet":
                System.out.print("Enter wallet ID: ");
                String walletId = scanner.nextLine();
                paymentMethod = new WalletPayment(walletId);
                break;
            default:
                System.out.println("Invalid payment method selected");
                System.exit(1);
        }
        paymentMethod.pay(product_order);

        System.out.println("\n--- Order Summary ---");
        float subtotal = 0;
        for(LineItem li : product_order.items()) {
            System.out.println(" - " + li.product().name() + " x"
                    + li.quantity() + " = " + li.lineTotal());
            subtotal += li.lineTotal().amount().floatValue();
        }
        System.out.println("Subtotal: €" + subtotal);
        System.out.println("Tax: €" + subtotal * 0.10);
        System.out.println("Total: €"+ product_order.totalWithTax(10));
        System.out.println("Thank you for your purchase!");
    }
}