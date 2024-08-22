package com.product;

import com.product.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class ProductSpliterator {
    public static void main(String args[]){
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "iphone", "mobile phone", 1000));
        products.add(new Product(2, "macbook", "laptop", 1800));
        products.add(new Product(3, "led tv", "appliance", 750));
        products.add(new Product(4, "refridgrator", "cooling appliance", 600));
        products.add(new Product(5, "oven", "kitchen", 100));

        Spliterator<Product> productSpliterator = products.spliterator();

        productSpliterator.forEachRemaining(System.out::println);

    }
}
