package com.product;

import com.product.model.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVWriter implements AutoCloseable {
    private final PrintWriter writer;

    public CSVWriter(FileWriter fileWriter) {
        this.writer = new PrintWriter(fileWriter);
    }

    // Writes a single line to the CSV file
    public void writeNext(String[] data) {
        String line = String.join(",", data);
        writer.println(line);
    }

    // Writes all products at once to a predefined file path
    public void writeCSV(List<Product> products) throws IOException {
        String filePath = "/Users/tarun/Developer/Product/data/product.csv";

        try (FileWriter fileWriter = new FileWriter(filePath);
             CSVWriter csvWriter = new CSVWriter(fileWriter)) {

            // Write the header line
            csvWriter.writeNext(new String[]{"Id", "Name", "Description", "Price"});

            // Write product data
            for (Product product : products) {
                csvWriter.writeNext(new String[]{
                        String.valueOf(product.getId()),
                        product.getName(),
                        product.getDescription(),
                        String.valueOf(product.getPrice())
                });
            }
        }
    }

    @Override
    public void close() {
        writer.flush();
        writer.close();
    }
}
