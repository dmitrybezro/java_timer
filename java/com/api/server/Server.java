package com.api.server;

import com.api.jdbc.JDBCPostgreSQL;
import com.model.ServerFeedback;
import com.model.ServerUIFeedback;
import com.model.Task;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static ArrayList<ServerMessage> serverList = new ArrayList<>();

    private final ServerFeedback Serfeedback = task -> {
        if (task == null) {
            return;
        }
        checkAndSsend(task);
    };

    private final ServerUIFeedback SerUIfeedback;

    private void checkAndSsend(Task task) {
        //  Проверить на действие
        if(!task.getIsAddTask()){
            SerUIfeedback.deleteTask(task.getNumberTask());
            JDBCPostgreSQL.getInstance().deleteTask(task);
            for(ServerMessage servermessage : serverList) {
                servermessage.sendLogs(task);
            }
        } else {
            String textTask = task.getTask();
            JDBCPostgreSQL.getInstance().saveTask(task);
            SerUIfeedback.addTask(textTask);
            for(ServerMessage servermessage : serverList) {
                servermessage.sendLogs(task);
            }
        }

    }

    public Server(ServerUIFeedback callback) {
        SerUIfeedback = callback;
        Thread thread = new Thread(() -> {
            try {
                ServerSocket server = new ServerSocket(8080);
                try {
                    while (true) {
                        Socket socket = server.accept();
                        try {
                            ServerMessage sl = new ServerMessage(socket);
                            sl.setCallback(Serfeedback);
                            serverList.add(sl);
                        } catch (IOException e) {
                            socket.close();
                        }
                    }
                } finally {
                    server.close();
                }
            } catch (IOException e) {
            }
        });
        thread.start();
    }
}
