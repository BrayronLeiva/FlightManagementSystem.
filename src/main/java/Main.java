import Controller.Controller;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    run_aplication();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void run_aplication() throws Exception {
        new Controller();
    }
}
