package com.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class SpliteratorSequentialIteration {
    public static void main(String args[]){
        List <String> prod = new ArrayList<>();
        prod.add("Tarun");
        prod.add("Ram");
        prod.add("sita");
        prod.add("name");
        prod.add("toyota");

        Spliterator<String> namesSpliterator = prod.spliterator();

        namesSpliterator.forEachRemaining(System.out::println);
    }
}
