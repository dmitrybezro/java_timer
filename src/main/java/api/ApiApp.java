package api;

import javax.swing.*;

public class ApiApp {
    private Thread thread;
    String label = "";
    int time;

    public void start(int time, String title, JTextField outputField, JSlider slider){
        //  Здесь создается новый поток
        thread = new Thread(() -> {
            int t = time;

            for(int i = time; i > 0 && !thread.isInterrupted(); i--){
                //if(!thread.isInterrupted()) {
                    //  output the value of time
                    slider.setValue(t);
                    outputField.setText(String.valueOf(t));
                    try {
                        thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    t--;
               // } else return;
            }
            //  output the title to panel
            outputField.setText(title);
        });
            thread.start();

    }

    public void stop(JTextField outputField){

            try{

                thread.stop();
                //thread.interrupt();
                throw new InterruptedException("Exception");

            } catch (InterruptedException e){
                outputField.setText("Отменено");
            }


    }
}
