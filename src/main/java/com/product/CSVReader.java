package com.product;

import com.product.model.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


    public class CSVReader {


            public List<Customer> readCustomerFromCsv () {
                String customerCsvFilePath = "/Users/tarun/Developer/foodie-cli-app-java/data/customer.csv";
                List<Customer> customersList = new ArrayList<>();
                String line;
                String csvSplitBy = ",";
                try (BufferedReader br = new BufferedReader(new FileReader(customerCsvFilePath))) {
                    br.readLine();
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(csvSplitBy);
                        Customer customer = new Customer();
                        customer.setId(data[0]);
                        customer.setName(data[1]);
                        customer.setEmail(data[2]);
                        customer.setPassword(data[3]);

                        customersList.add(customer);
                    }
                } catch (IOException e) {
                    System.out.println("File not found" + customerCsvFilePath);
                    System.exit(0);
                    e.printStackTrace();
                }

                return customersList;
            }


    }

