package com.api.converter;

import com.google.gson.Gson;
import com.model.Task;

public class StringAndJsonConverter {
    public static Task deserialize(String taskSerializable) {
        Gson gson = new Gson();
        Task taskDeserializable = gson.fromJson(taskSerializable, Task.class);
        return taskDeserializable;
    }

    public static String serialize(Task task){
        Gson gson = new Gson();
        String taskSerializable = gson.toJson(task);
        return taskSerializable;
    }
}
