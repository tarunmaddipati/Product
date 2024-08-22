package com.product;

public class Main {
    public static void main(String args[]) {
        CSVReader csvReader = new CSVReader();
        System.out.println(csvReader.readCustomerFromCsv());
    }
}
