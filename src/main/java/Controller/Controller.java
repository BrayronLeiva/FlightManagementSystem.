package Controller;

import View.View;
import Model.Model;
import Model.Vuelo;
import Model.Ciudad;
import org.jdom2.JDOMException;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;


public class Controller implements ActionListener {

    private View vista;
    private Model modelo;
    String modo_actual;
    private Tables_Listener tables_listener;
    private Window_Listener window_listener;

    public Controller() throws Exception {
        this.tables_listener = new Tables_Listener(this); //before view
        this.vista = new View(this);
        this.modelo = new Model(vista.getTbl_listado(), this);
        this.window_listener = new Window_Listener(this);
        this.vista.addWindowListener(window_listener);
        this.modo_actual = "Guardar";
        modelo.recargar_tabla();
    }
    public String getModo_actual() {
        return modo_actual;
    }

    public Tables_Listener getTables_listener() {
        return tables_listener;
    }

    public void setModo_actual(String modo_actual) {
        this.modo_actual = modo_actual;
    }
    public void limpiar_pantalla(){
        this.setModo_actual("Guardar");
        vista.getTxf_numero().setEnabled(true);
        vista.limpiar_interfaz();
    }
    public void reiniciar_interfaz(){
        this.limpiar_pantalla();
        //modelo.recargar_tabla();
        this.setModo_actual("Guardar");

    }
    public boolean txt_field_vacio(){
        return vista.getTxf_numero().getText().isEmpty();
    }
    public void guardar_datos() throws IOException, JDOMException {
        modelo.guardar_datos();
    }
    public boolean combo_box_vacio(){
        return String.valueOf(vista.getCb_llegada().getSelectedItem()).equals("") || String.valueOf(vista.getCb_origen().getSelectedItem()).equals("")
                || String.valueOf(vista.getCb_salida().getSelectedItem()).equals("") || String.valueOf(vista.getCb_destino().getSelectedItem()).equals("");
    }
    public boolean ciudad_identica() {
        return String.valueOf(vista.getCb_origen().getSelectedItem()).equals(String.valueOf(vista.getCb_destino().getSelectedItem()));
    }
    public void buscar_tabla(String str){
        try {
            modelo.buscar_tabla(str);
        }catch (Exception ex) {
            vista.lanzar_mensaje(ex.getMessage());
        }
    }
    public void add_element_combo_box_ciudades(String str , int gmt){
        String s = " GMT " + String.valueOf(gmt);
        vista.add_element_combo_box_ciudades(str+s);
    }


    public Vuelo crear_vuelo(){

        Vuelo vuelo = new Vuelo(vista.getTxf_numero().getText(), seleccionarCiudadxSave(String.valueOf(vista.getCb_origen().getSelectedItem())),
              seleccionarCiudadxSave(String.valueOf(vista.getCb_destino().getSelectedItem())), Integer.valueOf((String) vista.getCb_salida().getSelectedItem()),
                Integer.valueOf((String) vista.getCb_llegada().getSelectedItem()), vista.getRb_dia_siguiente().isSelected());
        System.out.println("En vuelo es el dia siguiente " + vuelo.isDia_siguiente());

        return vuelo;
    }

    public String seleccionarCiudadxSave(String ciu){
        System.out.println("Ingresando a seleccionar ciudad");
        String r = "";
        switch (ciu){
            case "San José GMT -6": r= "San José"; break;
            case  "Miami GMT -5": r= "Miami"; break;
            case  "Tokyo GMT 9": r= "Tokyo";; break;
            case  "Dubái GMT 4": r= "Dubái"; break;
            case  "Estambul GMT 2": r = "Estambul"; break;
            case  "Berna GMT 1": r= "Berna"; break;
            case  "Lisboa GMT 0": r= "Lisboa"; break;
            case  "Sídney GMT 10": r= "Sídney"; break;
            case "Santiago GMT -4" : r= "Santiago"; break;
            case  "Nairobi GMT 3": r= "Nairobi"; break;
            case  "Honiara GMT 11": r= "Honiara"; break;
            case  "Bangladés GMT 6": r= "Bangladés"; break;
            default: break;
        }
        System.out.println("VALOR A GUARDAR: " + r);
        return r;
    }
    public String seleccionarCiudadxSelect(String ciu) {
        System.out.println("Ingresando a seleccionar ciudad");
        String r = "";
        switch (ciu) {
            case "San José": r = "San José GMT -6"; break;
            case "Miami": r = "Miami GMT -5"; break;
            case "Tokyo": r = "Tokyo GMT 9"; break;
            case "Dubái": r = "Dubái GMT 4"; break;
            case "Estambul": r = "Estambul GMT 2"; break;
            case "Berna": r = "Berna GMT 1"; break;
            case "Lisboa": r = "Lisboa GMT 0"; break;
            case "Sídney": r = "Sídney GMT 10"; break;
            case "Santiago": r = "Santiago GMT -4"; break;
            case "Nairobi": r = "Nairobi GMT 3"; break;
            case "Honiara": r = "Honiara GMT 11"; break;
            case "Bangladés": r = "Bangladés GMT 6"; break;
            default: break;
        }
        System.out.println("VALOR A Seleccionar: " + r);
        return r;
    }

    public void guardar_vuelo() throws Exception {
        System.out.println("Ingresando a guardar vuelo");

        if(validar_excepciones(vista.getTxf_numero().getText())) {
            try {
                Vuelo vuelo = this.crear_vuelo();
                validarHoras(vuelo);
                if (this.getModo_actual().equals("Guardar")) {
                    if (!modelo.guardar_vuelo(vuelo, this.calcular_duracion(vuelo))) {
                        vista.lanzar_mensaje("Este ID de activo ya existe");
                    } else vista.lanzar_mensaje("Objeto agregado correctamente");
                } else if (this.getModo_actual().equals("Consultar")) {
                    if (!modelo.guardar_vuelo(vuelo, this.calcular_duracion(vuelo))) { //si vuelo ya existe y estoy en modo: consulta-uptade
                        if (vista.getRb_dia_siguiente().isSelected()) {
                            vuelo.setDia_siguiente(true);
                        } else vuelo.setDia_siguiente(false);
                            this.actualizar_vuelo(vuelo);
                    }
                }
            }catch (Exception ex){
                vista.lanzar_mensaje(ex.getMessage());
            }
        }
        vista.getRb_dia_siguiente().setSelected(false);
        this.reiniciar_interfaz();

    }

    public void actualizar_vuelo(Vuelo vuelo) throws Exception {
        int opcion = JOptionPane.showConfirmDialog(vista,
                "¿Estas seguro que deseas actualizar el vuelo?", "Confirmación", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            validarHoras(vuelo);
            modelo.actualizar_vuelo(vuelo, this.calcular_duracion(vuelo));
            vista.lanzar_mensaje("Objeto actualizado correctamente");
        } else {
            vista.lanzar_mensaje("No se actualizo el vuelo");
        }
    }

    public int obtener_diferencia_horaria(Vuelo obj){
        Ciudad origen = modelo.seleccionar_ciudad_nombre(obj.getOrigen());
        Ciudad destino = modelo.seleccionar_ciudad_nombre(obj.getDestino());
        int origen_gmt = origen.getGmt();
        int destino_gmt = destino.getGmt();

        return origen_gmt - destino_gmt; // origen first
    }

    public int obtener_hora_salida_destino(int h_exit_o, int dif){
        int r = h_exit_o - dif;
        if (r == 24) r=0; // para representar las 00 horas correctamente
        return r;
    }

    public int calcular_duracion(Vuelo obj) throws Exception {
        int h_salida_origen = obj.getSalida();
        int h_llegada_destino = obj.getLlegada();


        int dife_hora = obtener_diferencia_horaria(obj); // origen first
        System.out.println("Diferencia " + dife_hora);

        int h_salida_destino = obtener_hora_salida_destino(h_salida_origen, dife_hora);

        //System.out.println("Hora de salida destino " + h_salida_destino);

        int duracion = h_llegada_destino - h_salida_destino;


        if (obj.isDia_siguiente()){duracion = duracion+24;}
        System.out.println("Hora llegada destino: " + h_llegada_destino);
        System.out.println("Hora salida destino: " + h_salida_destino);
        //this.validarHoras(obj);



        return duracion;
    }

    public void validarHoras(Vuelo obj) throws Exception {

        int h_llegada_destino = obj.getLlegada();
        int h_salida_destino = obtener_hora_salida_destino(obj.getSalida(), obtener_diferencia_horaria(obj));

        if (h_salida_destino == h_llegada_destino && !obj.isDia_siguiente()) {
            throw new Exception("Las Horas Identicas - Digite Horas Posibles");
        }
        if(h_salida_destino>h_llegada_destino && !obj.isDia_siguiente()){
            throw new Exception("La Hora De Llegada Es Previa A La Hora De Salida - Digite Horas Posibles");
        }

    }

    public boolean validar_excepciones(String serie){
        try {
            if (txt_field_vacio()) {
                throw new Exception("Hay campos vacios, por favor revisar");
            }
            if (this.combo_box_vacio()){
                throw new Exception("Hay selecciones vacias, por favor revisar");
            }
            if (this.ciudad_identica()){
                throw new Exception("Se selecciono la misma ciudad de origen y destino");
            }
        }catch (Exception ex) {
            vista.lanzar_mensaje(ex.getMessage());
            return false;
        }
        return true;
    }


    public void recuperar_informacion(MouseEvent e){
        System.out.println("Se disparo el recuperar informacion");
        vista.getTxf_numero().setEnabled(false);
        this.setModo_actual("Consultar");
        try {
            if (vista.getTbl_listado().getSelectedRow() != -1) {
                if (e.getClickCount() == 1) {
                    //DefaultTableModel modelo = (DefaultTableModel) vista.getTbl_listado().getModel();
                    vista.getTxf_numero().setText(String.valueOf(vista.getTbl_listado().getValueAt(vista.getTbl_listado().getSelectedRow(), 0)));
                    System.out.println("origen: "+String.valueOf(vista.getTbl_listado().getValueAt(vista.getTbl_listado().getSelectedRow(), 1)));
                    vista.getCb_origen().setSelectedItem(seleccionarCiudadxSelect(String.valueOf(vista.getTbl_listado().getValueAt(vista.getTbl_listado().getSelectedRow(), 1))));
                    System.out.println("salida: "+String.valueOf(vista.getTbl_listado().getValueAt(vista.getTbl_listado().getSelectedRow(), 2)));
                    vista.getCb_salida().setSelectedItem(String.valueOf(vista.getTbl_listado().getValueAt(vista.getTbl_listado().getSelectedRow(), 2)));
                    vista.getCb_destino().setSelectedItem((seleccionarCiudadxSelect(String.valueOf(vista.getTbl_listado().getValueAt(vista.getTbl_listado().getSelectedRow(), 3)))));
                    vista.getCb_llegada().setSelectedItem((String.valueOf(vista.getTbl_listado().getValueAt(vista.getTbl_listado().getSelectedRow(), 4))));
                    Vuelo obj = modelo.seleccionar_vuelo_codigo(String.valueOf(vista.getTbl_listado().getValueAt(vista.getTbl_listado().getSelectedRow(), 0)));//
                    vista.getRb_dia_siguiente().setSelected(obj.isDia_siguiente());//
                }
            } else {
                throw new Exception("Selecciona una columna primero");
            }
        }catch (Exception ex) {
            vista.lanzar_mensaje(ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Guardar": {
                try {
                    this.guardar_vuelo();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
            case "Buscar": {
                this.buscar_tabla(vista.getTxf_buscar().getText());
                break;
            }
            case "Limpiar": {
                this.reiniciar_interfaz();
                try {
                    modelo.recargar_tabla();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
            default:
        }

    }


}
