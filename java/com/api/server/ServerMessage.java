package com.api.server;

import com.api.converter.StringAndJsonConverter;
import com.model.ServerFeedback;
import com.model.Task;

import java.io.*;
import java.net.Socket;

class ServerMessage extends Thread {
    private final BufferedReader inputReader;
    private final BufferedWriter outputReader;

    private ServerFeedback Serfeedback = null;

    public ServerMessage(Socket socket) throws IOException {
        inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputReader = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        String TaskInString;
        try {
            while (true) {
                TaskInString = inputReader.readLine();
                Task task = StringAndJsonConverter.deserialize(TaskInString);
                if (Serfeedback != null) {
                    Serfeedback.startActions(task);
                }
            }
        } catch (IOException e) {
        }
    }

    public void setCallback(ServerFeedback callback) {
        Serfeedback = callback;
    }

    public void sendLogs(Task task) {
        try {
            String serTask = StringAndJsonConverter.serialize(task);
            outputReader.write(serTask + "\n");
            outputReader.flush();
        } catch (IOException ignored) {
        }
    }
}
