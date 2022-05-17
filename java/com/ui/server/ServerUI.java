package com.ui.server;

import com.api.jdbc.JDBCPostgreSQL;
import com.api.server.Server;
import com.model.ServerUIFeedback;

import javax.swing.*;
import java.util.ArrayList;

public class ServerUI extends JDialog {
    private JPanel contentPane;
    private JList list1;
    DefaultListModel listModel = new DefaultListModel();

    private Server server;

    private ServerUIFeedback SerUIfeedback = new ServerUIFeedback() {
        @Override
        public void addTask(String log) {
            listModel.addElement(log);
        }

        @Override
        public void deleteTask(Integer numberTask) {
            listModel.remove(numberTask);
        }
    };

    public ServerUI() {
        setContentPane(contentPane);
        setModal(true);

        server = new Server(SerUIfeedback);

        list1.setModel(listModel);

        ArrayList<String> tasks = JDBCPostgreSQL.getInstance().getAll();
        listModel.addAll(tasks);

        setTitle("Сервер");
    }

    public static void main(String[] args) {
        ServerUI dialog = new ServerUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
