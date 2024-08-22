package com.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.CSVWriter;
import com.product.JSONDoc;
import com.product.model.Product;
import com.product.service.ProductService;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.Hibernate.map;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/products/name/{name}")
    public List<Product> getProductsByName(@PathVariable String name) {
        return productService.getProductsByName(name);
    }

    @PostMapping("/products/post")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/products/put/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products/writetojsonfile")
    public String writeProductsToJsonFile() {
        JSONDoc jsonDoc = new JSONDoc();
        List<Product> p1 = new ArrayList<>();
        p1 = productService.getAllProducts();
        p1.forEach(p3 -> jsonDoc.fileJson(p3.getId(), p3.getName(), p3.getDescription(), p3.getPrice()));
        jsonDoc.jsonArrayToFile(jsonDoc.getArray());
       /* try (FileWriter file = new FileWriter("products-1.json")) {
            file.write(productsArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error writing to JSON file");
        }

        */
        return "Products written to JSON file successfully";

    }

    @GetMapping("/products/fromjson")
    public ResponseEntity<List<Product>> getProductsFromJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products;
        try {
            // Read JSON file and convert it to a list of Product objects
            products = objectMapper.readValue(new File("products.json"), new TypeReference<List<Product>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
        return ResponseEntity.ok(products);
    }


    @PostMapping("/products/backup/{backupfile}")
    public ResponseEntity<String> backupProductsToJson(
            @PathVariable String backupfile,
            @RequestParam(required = false) boolean toCSV,
            @RequestBody List<Product> products) {

        String filePath = "/Users/tarun/Developer/Product/data/" + backupfile;
        File file = new File(filePath);

        try {
            if (file.exists()) {
                return ResponseEntity.badRequest().body("File already exists, try with another file name.");
            } else {
                file.createNewFile();

                if (toCSV) {
                    try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                        writer.writeNext(new String[]{"ID", "Name", "Description", "Price"});
                        for (Product product : products) {
                            writer.writeNext(new String[]{
                                    product.getId().toString(),
                                    product.getName(),
                                    product.getDescription(),
                                    String.valueOf(product.getPrice())
                            });
                        }
                    }
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(file, products);
                }
                return ResponseEntity.ok("Products backed up to file successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error backing up products to file.");
        }
    }

//http://localhost:8080/products/backup/backupfile.json?toCSV=false

    @PostMapping("/products/restore/{restorefile}")
    public ResponseEntity<List<Product>> restoreProductsFromJson(
            @PathVariable String restorefile,
            @RequestParam(required = false) boolean fromCSV) {

        String filePath = "/Users/tarun/Developer/Product/data/" + restorefile;
        File file = new File(filePath);

        // Validate if the file exists
        if (!file.exists()) {
            return ResponseEntity.status(404).body(null);
        }

        try {
            List<Product> products = new ArrayList<>();

            if (fromCSV) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    // Skip the header line
                    br.readLine();
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data.length == 4) { // Ensure that data has 4 columns
                            Product product = new Product();
                            product.setId(Long.parseLong(data[0]));
                            product.setName(data[1]);
                            product.setDescription(data[2]);
                            product.setPrice(Double.parseDouble(data[3]));
                            products.add(product);
                        }
                    }
                }
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                products = objectMapper.readValue(file, new TypeReference<List<Product>>() {});
            }

            // Save all products back to the database
            products.forEach(productService::addProduct);

            // Return the list of restored products
            return ResponseEntity.ok(products);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}

//the restore needs to be worked when we are trying to call with restore call all the products into arraylist

//backup and restore api calls
//take a parameter as input for the backup and restore
//validation weather the file exist or not return statement
//create file if file doesnot exist
//test with some test data
//populate data into csv and restore





//write a method in prodcontroller with postmapping with /produsts/writetojsonfile input parameter will be jsonarray
//how do read and retrive the json data into array
//first i need to write the data from h2 to jsonfile
//read json file into the class

//where can we use streams
//reading the file from json