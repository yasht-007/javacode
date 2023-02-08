package org.example;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Cars {

    private String color;
    private String type;

    public Cars() {
    }

    public Cars(String color, String type) {
        this.color = color;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
// standard getters setters
}

public class GsonExample {
    public static void main(String[] args) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        String jsonString = gson.toJson(new Cars("red", "Jaguar"));
        System.out.println(jsonString);

        Cars c = gson.fromJson(jsonString, Cars.class);

        System.out.println(c.getColor() + " " + c.getType());

        // create tree from json

        //first create jsonparser instance

        JsonParser parser = new JsonParser();
        String jsonData = "{\"name\":\"Mahesh Kumar\", \"age\":21,\"verified\":false,\"marks\": [100,90,85]}";
        //create tree from JSON

        System.out.println("Now using JSON Tree method :- ");
        JsonElement rootNode = parser.parse(jsonData);

        // now traverse json tree

        JsonObject details = rootNode.getAsJsonObject();

        JsonElement nameNode = details.get("name");
        System.out.println("Name: " + nameNode.getAsString());

        JsonElement ageNode = details.get("age");
        System.out.println("Age: " + ageNode.getAsInt());

    }
}
