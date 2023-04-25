package org.task;

import org.json.JSONArray;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Main
{
    private static int metricCount = 1;
    private static final HashMap<String, String> oidToVendor = new HashMap<>();

    public static void main(String[] args)
    {

        try
        {
            loadVendors();

            File directory = new File("/home/yash/Desktop/Task/device-signature");

            File[] files = directory.listFiles();

            HashMap<String, JSONArray> data = new HashMap<>();

            assert files != null;

            for (File file : files)
            {

                Yaml yaml = new Yaml();

                String name = oidToVendor.get(file.getName().substring(0, file.getName().lastIndexOf('.')));

                if (name == null)
                {
                    continue;
                }

                HashMap<String, Object> yamlData = yaml.load(new FileInputStream(file));

                if (yamlData == null) continue;

                if (yamlData.containsKey("metrics"))
                {

                    List<HashMap<String, String>> scalar = (List<HashMap<String, String>>) yamlData.get("metrics");

                    JSONObject json = writeData(scalar, "scalar");

                    JSONArray array;

                    if (data.containsKey(name))
                    {
                        array = data.get(name);
                    }
                    else
                    {
                        array = new JSONArray();
                    }

                    array.put(json);

                    data.put(name, array);

                }

                if (yamlData.containsKey("table-metrics"))
                {

                    List<HashMap<String, List<HashMap<String, String>>>> tabular = (List<HashMap<String, List<HashMap<String, String>>>>) yamlData.get("table-metrics");

                    for (HashMap<String, List<HashMap<String, String>>> stringListHashMap : tabular)
                    {
                        List<HashMap<String, String>> tab = stringListHashMap.values().stream().flatMap(List::stream).toList();

                        JSONObject json = writeData(tab, "tabular");

                        JSONArray array;

                        if (data.containsKey(name))
                        {
                            array = data.get(name);
                        }
                        else
                        {
                            array = new JSONArray();
                        }

                        array.put(json);

                        data.put(name, array);
                    }

                }

            }

            data.forEach((k, v) ->
            {
                FileWriter fileWriter = null;
                try
                {
                    fileWriter = new FileWriter(k + ".json");

                    fileWriter.write(v.toString());

                    fileWriter.flush();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
                finally
                {
                    try
                    {
                        if (fileWriter != null)
                        {
                            fileWriter.close();
                        }
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            });

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private static void loadVendors() throws IOException
    {
        String jsonData = new String(Files.readAllBytes(Paths.get("/home/yash/Desktop/Task/snmp-devices.json")));

        JSONArray jsonArray = new JSONArray(jsonData);

        for (int index = 0; index < jsonArray.length(); index++)
        {
            oidToVendor.put(jsonArray.getJSONObject(index).getString("snmp.device.catalog.oid"), jsonArray.getJSONObject(index).getString("snmp.device.catalog.vendor"));
        }
    }

    private static JSONObject writeData(List<HashMap<String, String>> data, String type)
    {

        JSONObject json = new JSONObject();

        json.put("oid.group.id", UUID.randomUUID());

        json.put("oid.group.device.type", "SNMP Device");

        json.put("oid.group.type", type);

        if (type.equalsIgnoreCase("scalar"))
        {
            json.put("oid.group.name", "Metric " + metricCount++);
        }

        if (type.equalsIgnoreCase("tabular"))
        {
            json.put("oid.group.name", data.get(0).get("name"));

            json.put("oid.group.parent.oid", data.get(0).get("oid"));
        }

        json.put("oid.group.polling.timeout.sec", 60);

        json.put("oid.group.polling.interval.sec", 600);

        JSONObject jsonObject = new JSONObject();

        for (HashMap<String, String> jsonData : data)
        {
            String name = jsonData.get("name").toLowerCase().replace(" ", ".").replace("(", "").replace(")", "");

            if (name.contains("%"))
            {
                String[] nameContainingPercent = name.split("\\.");

                jsonObject.put(jsonData.get("oid"), "system." + nameContainingPercent[nameContainingPercent.length - 2] + ".percent");

            }
            else
            {
                jsonObject.put(jsonData.get("oid"), name);
            }

            json.put("oid.group.oids", jsonObject);

        }

        return json;
    }

}