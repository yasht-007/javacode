package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONExample {
    public static void main(String[] args) throws ParseException {
        // 1) Java JSON Encode

        //normal way
        JSONObject obj = new JSONObject();
        obj.put("name", "yash");
        obj.put("age", 22);
        obj.put("address", "Surat");
        System.out.println(obj);

        // Java JSON Encode using Map

        Map mapObj = new HashMap();
        mapObj.put("name", "yash");
        mapObj.put("age", 22);
        mapObj.put("address", "Surat");

        String jsonText = JSONValue.toJSONString(mapObj);
        System.out.println(jsonText);

        // Java JSON Array encode
        JSONArray arr = new JSONArray();
        arr.add(0, "yash");
        arr.add(1, 22);
        arr.add(2, "Surat");

        System.out.println(arr);


        //Java JSON using List

        List larr = new ArrayList();
        larr.add("Mks");
        larr.add(27);
        larr.add(11111);
        String jsonText1 = JSONValue.toJSONString(arr);
        System.out.println(jsonText1);

        // 1) Java JSON Decode

        System.out.println("After Decoding:- ");

        String s = "{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}";
        Object jObj = JSONValue.parse(s);
        JSONObject jsonObject = (JSONObject) jObj;

        String name = (String) jsonObject.get("name");
        double salary = (Double) jsonObject.get("salary");
        long age = (Long) jsonObject.get("age");
        System.out.println(name + " " + salary + " " + age);

        // using json parser

        JSONParser jsonParser = new JSONParser();
        String dem = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";

        Object p = jsonParser.parse(dem);
        System.out.println(p);

    }
}
