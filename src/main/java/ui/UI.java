package ui;

import api.ApiApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.concurrent.Flow;

public class UI {
    static ApiApp apiApp;
    public static void createGUI()
    {
        apiApp = new ApiApp();
        JFrame frame = new JFrame("Java - спецкурс");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Введите текст");

        JTextField inputField = new JTextField(15);
        JTextField outputField = new JTextField(15);

        BoundedRangeModel model = new DefaultBoundedRangeModel(80, 0, 0, 200);

        // Создание ползунков
        JSlider slider = new JSlider(model);
        slider.setMinimum(0);
        slider.setMaximum(10);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);
        slider.setValue(5);

        JButton buttonStart = new JButton("Начать");
        buttonStart.setPreferredSize(new Dimension(175, 35));
        buttonStart.addActionListener(e -> apiApp.start(slider.getValue(), inputField.getText(), outputField, slider));

        JButton buttonStop = new JButton("Остановить");
        buttonStop.setPreferredSize(new Dimension(175, 35));
        buttonStop.addActionListener(e -> apiApp.stop(outputField));

        JPanel contents = new JPanel();
        contents.add(new JScrollPane(label));
        contents.add(new JScrollPane(inputField));
        contents.add(new JScrollPane(slider));

        contents.add(new JScrollPane(buttonStart));
        contents.add(new JScrollPane(buttonStop));

        contents.add(new JScrollPane(outputField));



        frame.getContentPane().add(contents);

        frame.setPreferredSize(new Dimension(400, 300));

        frame.pack();
        frame.setVisible(true);
    }
}
