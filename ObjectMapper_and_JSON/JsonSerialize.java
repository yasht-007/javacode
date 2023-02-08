package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class JsonSerialize {
    public static void main(String[] args) throws ParseException, IOException, ClassNotFoundException {
        String jsonData = "{\"name\":\"john\",\"age\":22,\"class\":\"mca\"}";
        JSONObject json = (JSONObject) new JSONParser().parse(jsonData);
        ObjectOutput output = new ObjectOutputStream(new FileOutputStream("/home/yash/Java/jsonserialize.txt"));
        output.writeObject(json);

        ObjectInputStream oin = new ObjectInputStream(new FileInputStream("/home/yash/Java/jsonserialize.txt"));
        JSONObject o = (JSONObject) oin.readObject();
        System.out.println(o.toJSONString());

    }
}
