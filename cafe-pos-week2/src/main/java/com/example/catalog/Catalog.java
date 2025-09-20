package com.example.catalog;

import java.util.Optional;

import com.example.common.Product;

public interface Catalog {
    void add(Product p);

    Optional<Product> findById(String id);
}