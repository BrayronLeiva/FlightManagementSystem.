package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Tables_Listener implements MouseListener {
    private Controller controller;
    public Tables_Listener(Controller ctl) {
        this.controller = ctl;
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {
        controller.recuperar_informacion(e);
    }

}