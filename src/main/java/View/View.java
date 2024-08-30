package View;

import Controller.Controller;
import Controller.Tables_Listener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class View extends JFrame {

    private Controller controller;
    private JPanel pnl_vuelo;
    private JTextField txf_numero;
    private JComboBox cb_origen;
    private JComboBox cb_destino;
    private JComboBox cb_salida;
    private JComboBox cb_llegada;
    private JButton btn_guardar;
    private JButton btn_limpiar;
    private JPanel pnl_busqueda;
    private JTextField txf_buscar;
    private JButton btn_buscar;
    private JPanel pnl_listado;
    private JScrollPane scp_listado;
    private JTable tbl_listado;
    private JLabel lb_numero;
    private JLabel lb_origen;
    private JLabel lb_destino;
    private JLabel lb_salida;
    private JLabel lb_llegada;
    private JLabel lb_ciudad;
    private JPanel pnl_principal;
    private JRadioButton rb_dia_siguiente;

    public View(Controller ctl){
        setTitle("VuelaYa - Sistema De Vuelos");

        ImageIcon icono = new ImageIcon("src\\logo.png");
        setIconImage(icono.getImage());

        this.setResizable(false);
        this.setSize(800,800);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(pnl_principal);
        this.controller = ctl;

        this.initTxtFields();
        this.init_combo_box();
        this.init_buttons();
        this.initTable();
    }

    private void initTxtFields(){
        Validaciones val = new Validaciones();
        ((AbstractDocument) txf_numero.getDocument()).setDocumentFilter(val.new ValidarOnlyNum());
        ((AbstractDocument) txf_buscar.getDocument()).setDocumentFilter(val.new ValidarSinEspecialesWSpace());
    }

    private void initTable(){

        DefaultTableModel modelo = new DefaultTableModel();

        // Agrega una columna a la tabla
        modelo.addColumn("Numero");
        modelo.addColumn("Origen");
        modelo.addColumn("Salida");
        modelo.addColumn("Destino");
        modelo.addColumn("Llegada");
        modelo.addColumn("Duracion");
        modelo.addColumn("Dia De Llegada"); //c


        // Asigna el modelo de datos a la tabla
        tbl_listado.setModel(modelo);
        tbl_listado.setVisible(true);
        Tables_Listener tables_listener = controller.getTables_listener();
        tbl_listado.addMouseListener(tables_listener);
        // Muestra la tabla
    }

    public void add_element_combo_box_ciudades(String str){
        getCb_destino().addItem(str);
        getCb_origen().addItem(str);
    }

    public void init_combo_box(){
        getCb_destino().addItem("");
        getCb_origen().addItem("");
        getCb_llegada().addItem("");
        getCb_salida().addItem("");
        for (int i = 0; i < 24; i++) {
            getCb_llegada().addItem(String.valueOf(i));
            getCb_salida().addItem(String.valueOf(i));
        }
    }

    public void init_buttons(){
        btn_limpiar.addActionListener(controller);
        btn_guardar.addActionListener(controller);
        btn_limpiar.addActionListener(controller);
        btn_buscar.addActionListener(controller);
    }

    public void limpiar_interfaz() {
        //txf_codigo.setEnabled(true);
        this.txf_buscar.setText("");
        this.txf_numero.setText("");
        this.txf_buscar.setText("");
        this.cb_destino.setSelectedItem("");
        this.cb_origen.setSelectedItem("");
        this.cb_llegada.setSelectedItem("");
        this.cb_salida.setSelectedItem("");
        this.rb_dia_siguiente.setSelected(false);
        this.tbl_listado.clearSelection();

    }

    public void lanzar_mensaje(String msg){
        JOptionPane.showMessageDialog(this, msg, "Mensaje",JOptionPane.INFORMATION_MESSAGE);
    }

    public JPanel getPnl_vuelo() {
        return pnl_vuelo;
    }

    public void setPnl_vuelo(JPanel pnl_vuelo) {
        this.pnl_vuelo = pnl_vuelo;
    }

    public JTextField getTxf_numero() {
        return txf_numero;
    }

    public void setTxf_numero(JTextField txf_numero) {
        this.txf_numero = txf_numero;
    }

    public JComboBox getCb_origen() {
        return cb_origen;
    }

    public void setCb_origen(JComboBox cb_origen) {
        this.cb_origen = cb_origen;
    }

    public JComboBox getCb_destino() {
        return cb_destino;
    }

    public void setCb_destino(JComboBox cb_destino) {
        this.cb_destino = cb_destino;
    }

    public JComboBox getCb_salida() {
        return cb_salida;
    }

    public void setCb_salida(JComboBox cb_salida) {
        this.cb_salida = cb_salida;
    }

    public JComboBox getCb_llegada() {
        return cb_llegada;
    }

    public void setCb_llegada(JComboBox cb_llegada) {
        this.cb_llegada = cb_llegada;
    }

    public JButton getBtn_guardar() {
        return btn_guardar;
    }

    public void setBtn_guardar(JButton btn_guardar) {
        this.btn_guardar = btn_guardar;
    }

    public JButton getBtn_limpiar() {
        return btn_limpiar;
    }

    public void setBtn_limpiar(JButton btn_limpiar) {
        this.btn_limpiar = btn_limpiar;
    }

    public JPanel getPnl_busqueda() {
        return pnl_busqueda;
    }

    public void setPnl_busqueda(JPanel pnl_busqueda) {
        this.pnl_busqueda = pnl_busqueda;
    }

    public JTextField getTxf_buscar() {
        return txf_buscar;
    }

    public void setTxf_buscar(JTextField txf_buscar) {
        this.txf_buscar = txf_buscar;
    }

    public JButton getBtn_buscar() {
        return btn_buscar;
    }

    public void setBtn_buscar(JButton btn_buscar) {
        this.btn_buscar = btn_buscar;
    }

    public JPanel getPnl_listado() {
        return pnl_listado;
    }

    public void setPnl_listado(JPanel pnl_listado) {
        this.pnl_listado = pnl_listado;
    }

    public JScrollPane getScp_listado() {
        return scp_listado;
    }

    public void setScp_listado(JScrollPane scp_listado) {
        this.scp_listado = scp_listado;
    }

    public JTable getTbl_listado() {
        return tbl_listado;
    }

    public void setTbl_listado(JTable tbl_listado) {
        this.tbl_listado = tbl_listado;
    }

    public JLabel getLb_numero() {
        return lb_numero;
    }

    public void setLb_numero(JLabel lb_numero) {
        this.lb_numero = lb_numero;
    }

    public JLabel getLb_origen() {
        return lb_origen;
    }

    public void setLb_origen(JLabel lb_origen) {
        this.lb_origen = lb_origen;
    }

    public JLabel getLb_destino() {
        return lb_destino;
    }

    public void setLb_destino(JLabel lb_destino) {
        this.lb_destino = lb_destino;
    }

    public JLabel getLb_salida() {
        return lb_salida;
    }

    public void setLb_salida(JLabel lb_salida) {
        this.lb_salida = lb_salida;
    }

    public JLabel getLb_llegada() {
        return lb_llegada;
    }

    public void setLb_llegada(JLabel lb_llegada) {
        this.lb_llegada = lb_llegada;
    }

    public JLabel getLb_ciudad() {
        return lb_ciudad;
    }

    public void setLb_ciudad(JLabel lb_ciudad) {
        this.lb_ciudad = lb_ciudad;
    }

    public JRadioButton getRb_dia_siguiente() {
        return rb_dia_siguiente;
    }
}
