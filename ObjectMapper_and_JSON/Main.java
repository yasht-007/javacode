package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

class Car {

    private String color;
    private String type;

    public Car() {
    }

    public Car(String color, String type) {
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

public class Main {

    public static void main(String[] args) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // for writing:-

            Car car = new Car("black", "Honda");
            objectMapper.writeValue(new FileWriter("/home/yash/Java/car.json"), car);

//            String carAsString = objectMapper.writeValueAsString(car);
//            System.out.println(carAsString);
//
//            byte carAsByte[] = objectMapper.writeValueAsBytes(car);
//            for (byte m : carAsByte) {
//                System.out.println(m);
//            }

            // for reading:-

            Car c = objectMapper.readValue(new File("/home/yash/Java/car.json"), Car.class);
            System.out.println(c.getColor() + " " + c.getType());
            // using url

//            Car cUrl = objectMapper.readValue(new URL("/home/yash/Java/car.json"), Car.class);
//            System.out.println(cUrl.getColor() + " " + cUrl.getType());

            // using string
//            String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
//            Car carJson = objectMapper.readValue(json, Car.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}