package com.ticket_to_ride.common;

import java.io.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class Serializer {

    private Gson gson;

    public Serializer() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public Object deserialize(InputStream is, Class type) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        reader.setLenient(true);
        if (reader.hasNext()) {
            Object res = gson.fromJson(reader, type);
            System.out.println("\nIn Message:\n" + res.toString());
            return res;
        }
        return null;
    }

    public Object deserialize(String str, Class type) {
        return gson.fromJson(str, type);
    }

    public Object deserialize(String str, Type type) {
        return gson.fromJson(str, type);
    }

    public void serialize(OutputStream os, Object obj) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        System.out.println("\nOut Message:\n" + obj.toString());
        gson.toJson(obj, obj.getClass(), writer);
        writer.flush();
    }

    public String serialize(Object obj) {
        return gson.toJson(obj);
    }
}
