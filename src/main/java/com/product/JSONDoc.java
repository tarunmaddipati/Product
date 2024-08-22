package com.product;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import java.util.Iterator;

public class JSONDoc {
    private JSONObject jsonObject;
    private JSONArray array;

    public JSONDoc() {
        this.jsonObject = new JSONObject();
        this.array = new JSONArray();
    }

    public void fileJson(long id, String name, String description, double price) {
        System.out.println("In fileJson");
        this.jsonObject.put("id", id);
        this.jsonObject.put("name", name);
        this.jsonObject.put("description", description);
        this.jsonObject.put("price", price);

        this.array.add(this.jsonObject);

        System.out.println("After jsonobject");

     //wriete this as another methodd
       /* try (FileWriter file = new FileWriter("product1.json")) {
            file.write(this.jsonObject.toJSONString());
            file.close();
            System.out.println("Copied data to file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        */
    }


    public void jsonArrayToFile(JSONArray array){
        try (FileWriter file = new FileWriter("product1.json")) {
            file.write(array.toJSONString());
            file.close();
            System.out.println("Copied data to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public JSONArray getArray(){
        return this.array;
    }

    public static void main(String[] args) throws IOException {
        JSONDoc jsonDoc = new JSONDoc();
        jsonDoc.fileJson(1, "macbook", "laptop", 1500.0);
      //  jsonDoc.fileJson(2,"iphone","mobile",999);
      //  jsonDoc.fileJson(3,"airpods","earphones",200);
      //  jsonDoc.fileJson(4,"mouse","accessory",25);

        try {
            // Read the entire JSON file into a String
            String jsonContent = new String(Files.readAllBytes(Paths.get("/Users/tarun/Developer/Product/product1.json")));

            // Parse the JSON content using JsonPath
         //  String price = JsonPath.read(jsonContent, "$.price").toString();

            // Print the price
            System.out.println("price");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the IOException
        } catch (Exception e) {
            e.printStackTrace(); // Handle any other exceptions
        }

    }
}