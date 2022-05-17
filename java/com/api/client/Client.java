package com.api.client;

import com.api.converter.StringAndJsonConverter;
import com.model.ClientUIFeedback;
import com.model.Task;

import java.io.*;
import java.net.Socket;

public class Client {
    private final ClientUIFeedback CliUIfeedback;

    private Socket socket;
    private BufferedReader inputReader;
    private BufferedWriter outputReader;

    public Client(ClientUIFeedback callback) {
        CliUIfeedback = callback;
        try {
            socket = new Socket("localhost", 8080);
            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputReader = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            new ReadMsg().start();
        } catch (IOException e) {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                    inputReader.close();
                    outputReader.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    public void setMessage(Task task) {
        send(task);
    }

    public void deleteMessage(Task task) {
        send(task);
    }

    private void parseMessage(String msg) {
        //  Проверить на действие
        Task deserTask = StringAndJsonConverter.deserialize(msg);
        if(!deserTask.getIsAddTask()) {
            CliUIfeedback.deleteTaskOnClient(deserTask.getNumberTask());
        } else {
            CliUIfeedback.addTaskOnClient(deserTask.getTask());
        }

    }

    private void send(Task task) {
        try {
            if (socket != null && !socket.isClosed()) {
                String taskSerializable = StringAndJsonConverter.serialize(task);
                outputReader.write(taskSerializable + "\n");
                outputReader.flush();
            }
        } catch (IOException ignored) {
        }
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String taskInString;
            try {
                while (true) {
                    taskInString = inputReader.readLine();
                    parseMessage(taskInString);
                }
            } catch (IOException e) {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                        inputReader.close();
                        outputReader.close();
                    }
                } catch (IOException ignored) {
                }
            }
        }
    }
}
