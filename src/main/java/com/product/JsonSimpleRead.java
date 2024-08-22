package com.product;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class JsonSimpleRead {
    public static void main(String[] args) {

        try (FileReader reader = new FileReader("product2.json")) {

            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(reader);



            // read value one by one manually
            System.out.println( jsonObject.get("id"));  //long
            System.out.println( jsonObject.get("name"));
            System.out.println( jsonObject.get("description"));
            System.out.println( jsonObject.get("price"));  //double

            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if ("id".equals(key) && value instanceof BigDecimal) {
                    System.out.println(((BigDecimal) value).longValue());
                } else if ("price".equals(key) && value instanceof BigDecimal) {
                    System.out.println(((BigDecimal) value).doubleValue());
                } else {
                    System.out.println(value);
                }
            }

            JsonArray msg = (JsonArray) jsonObject.get("messages");
            for (Object o : msg) {
                System.out.println(o);
            }
        }
        catch (IOException | JsonException e) {
            throw new RuntimeException(e);
        }

    }
}
