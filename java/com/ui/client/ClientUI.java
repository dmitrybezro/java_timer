package com.ui.client;

import com.api.client.Client;
import com.api.jdbc.JDBCPostgreSQL;
import com.model.ClientUIFeedback;
import com.model.Task;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ClientUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonAdd;
    private JButton buttonDelete;
    private JList list1;
    private JTextPane textPane1;
    DefaultListModel listModel = new DefaultListModel();

    private Client client;

    private ClientUIFeedback CliUIfeedback = new ClientUIFeedback() {
        @Override
        public void addTaskOnClient(String text) {
            listModel.addElement(text);
        }

        @Override
        public void deleteTaskOnClient(Integer numberTask){
            listModel.remove(numberTask);
        }
    };

    public ClientUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonAdd);
        client = new Client(CliUIfeedback);
        setTitle("Клиент");
        list1.setModel(listModel);

        ArrayList<String> tasks = JDBCPostgreSQL.getInstance().getAll();
        listModel.addAll(tasks);

        buttonAdd.addActionListener(e -> onAdd());

        buttonDelete.addActionListener(e -> onDelete());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onDelete();
            }
        });

        contentPane.registerKeyboardAction(e -> onDelete(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onAdd() {
        String message = textPane1.getText();
        Task task = new Task(message, true);
        client.setMessage(task);
        textPane1.setText("");
    }

    private void onDelete() {
        Integer numberTask = list1.getSelectedIndex();
        String textTask = list1.getSelectedValue().toString();
        Task task = new Task(textTask, numberTask, false);
        client.deleteMessage(task);
    }

    public static void main(String[] args) {
        ClientUI dialog = new ClientUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
