package Controller;

import org.jdom2.JDOMException;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class Window_Listener implements WindowListener {
    private Controller controller;
    public Window_Listener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            controller.guardar_datos();
        } catch (IOException | JDOMException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
