package com.product;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.FileWriter;
import java.io.IOException;

public class JsonSimpleWrite {
    public static void main(String[] args) {

        // JSON String
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.put("id", 2);
        jsonObject1.put("name", "macbook");
        jsonObject1.put("description" , "laptop");
        jsonObject1.put("price", 2000);

        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.put("id", 3);
        jsonObject2.put("name", "mouse");
        jsonObject2.put("description" , "accessory");
        jsonObject2.put("price", 20);




        try (FileWriter fileWriter = new FileWriter("product1.json")) {

            Jsoner.serialize(jsonObject1, fileWriter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
