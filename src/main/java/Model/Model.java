package Model;

import Controller.Controller;
import org.jdom2.JDOMException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.text.Collator;
import java.util.List;

public class Model {
    private Lista_Vuelos listaVuelos;
    private Lista_Ciudades listaCiudades;
    private Vuelo current;
    private JTable table;
    private XML_Manager xmlManager;
    private Controller controller;

    public Model(JTable table, Controller controller) throws IOException, JDOMException {
        this.table = table;
        this.listaVuelos = new Lista_Vuelos();
        this.listaCiudades = new Lista_Ciudades();
        this.xmlManager = new XML_Manager(this);
        this.controller = controller;
        this.cargar_datos();
    }
    public boolean vuelo_existente(String serie){
        Vuelo obj = seleccionar_vuelo_codigo(serie);
        return obj != null;
    }
    public boolean ciudad_existente(String serie){
        Ciudad obj = seleccionar_ciudad_codigo(serie);
        return obj != null;
    }

    public Ciudad seleccionar_ciudad_codigo(String serie){
        return listaCiudades.obtener(serie);
    }
    public Ciudad seleccionar_ciudad_nombre(String nom){
        return listaCiudades.obtener_x_nombre(nom);
    }
    public Vuelo seleccionar_vuelo_codigo(String serie){
        return listaVuelos.obtener(serie);
    }
    public Vuelo seleccionar_vuelo_ciudad(String serie){
        return listaVuelos.obtener_x_ciudad(serie);
    }

    public boolean guardar_vuelo(Vuelo obj, int dura) throws IOException, JDOMException {
        current = seleccionar_vuelo_codigo(obj.getNumero());
        //System.out.println("Ingreso save model");
        if (!this.vuelo_existente(obj.getNumero())) {
            listaVuelos.agregar(obj);
            //System.out.printf(obj.toString());
            DefaultTableModel modelo = (DefaultTableModel) table.getModel();
            Object[] fila = new Object[]{obj.getNumero(),obj.getOrigen(),obj.getSalida(),obj.getDestino(),obj.getLlegada(), dura, setDia(obj.isDia_siguiente())};
            modelo.addRow(fila);
        }
        else return false;
        return true;
    }
    public boolean guardar_ciudad(Ciudad obj) throws IOException, JDOMException {
        //Ciudad city = se(obj.getNumero());
        //System.out.println("\nIngreso save model\n");
        if (!this.ciudad_existente(obj.getId())) {
            listaCiudades.agregar(obj);
            //System.out.printf("\nGuardando\n "+obj.toString());
        }
        else return false;
        return true;
    }
    public void actualizar_vuelo(Vuelo obj, int dura){
        current.setDestino(obj.getDestino());
        current.setLlegada(obj.getLlegada());
        current.setOrigen(obj.getOrigen());
        current.setSalida(obj.getSalida());
        current.setDia_siguiente(obj.isDia_siguiente());
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        Object[] fila = new Object[]{obj.getNumero(),obj.getOrigen(),obj.getSalida(),
                obj.getDestino(),obj.getLlegada(), dura, setDia(obj.isDia_siguiente())};
        modelo.insertRow(table.getSelectedRow()+1, fila);
        modelo.removeRow(table.getSelectedRow());

    }

    public void buscar_tabla(String target) throws Exception {
        //System.out.println("Se disparo buscar");
        if(!this.buscar_numero(target)) {

            this.buscar_nombre(target);
        }
    }

    public boolean buscar_numero(String target) throws Exception {
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        int c = modelo.getRowCount();
        int row_search;
        current = seleccionar_vuelo_codigo(target);
        if (current != null) {
            this.limpiar_tabla();
            Object[] fila = new Object[]{current.getNumero(), current.getOrigen(), current.getSalida(),
                    current.getDestino(), current.getLlegada(), controller.calcular_duracion(current), setDia(current.isDia_siguiente())};
            modelo.addRow(fila);
            return true;
        } else return false;

    }

    public void buscar_nombre(String target) throws Exception {

        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        int c = modelo.getRowCount();
        int row_search;
        List<Vuelo> list = listaVuelos.obtener_x_ciudad_lista(target);
        if (!list.isEmpty()) {
            this.limpiar_tabla();
            for (int i = 0; i < list.size(); i++) {
                Vuelo obj = list.get(i);
                Object[] fila = new Object[]{obj.getNumero(), obj.getOrigen(), obj.getSalida(),
                        obj.getDestino(), obj.getLlegada(), controller.calcular_duracion(obj), setDia(obj.isDia_siguiente())};
                modelo.addRow(fila);
            }
        } else throw new Exception("El ID o Ciudad Buscado No Esta Registrado");

    }


    public void limpiar_tabla(){
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        for (int i = 0; i < table.getRowCount(); i++){
            modelo.removeRow(i);
            i-=1;
        }

    }
    public String setDia(boolean d){
        if(d){return "Mañana";}
        return "Hoy";
    }

    public void recargar_tabla() throws Exception {

        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        if (table.getModel().getRowCount() > 0)
            this.limpiar_tabla();


        for (int i = 0; i < this.listaVuelos.getList().size(); i++){
            Vuelo obj = this.listaVuelos.getList().get(i);
            Object[] fila = new Object[]{obj.getNumero(),obj.getOrigen(),obj.getSalida(),
                    obj.getDestino(),obj.getLlegada(), controller.calcular_duracion(obj), setDia(obj.isDia_siguiente())};
            modelo.addRow(fila);
        }

    }

    public void guardar_datos() throws IOException, JDOMException {
        System.out.println("Ingreso a guardado de datos");
        for (int i = 0; i < listaCiudades.getList().size(); i++){
            //System.out.printf("Guardando obj: " + listaCiudades.getList().get(i).toString());
            xmlManager.write_ciudad(listaCiudades.getList().get(i), System.out);
        }
        for (int i = 0; i < listaVuelos.getList().size(); i++){
            //System.out.printf("Guardando obj: " + listaVuelos.getList().get(i).toString());
            xmlManager.write_vuelo(listaVuelos.getList().get(i), System.out);
        }

    }

    public void cargar_datos() throws IOException, JDOMException {
        this.xmlManager.cargar_ciudades();
        this.xmlManager.cargar_vuelos();
        this.xmlManager.limpiar_xml();
        //this.recargar_tabla();
    }

    public Controller getController() {
        return controller;
    }
}
