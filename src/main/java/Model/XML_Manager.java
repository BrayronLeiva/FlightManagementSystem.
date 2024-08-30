package Model;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.xml.XMLConstants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class XML_Manager {

    Model model;
    private Document doc;
    SAXBuilder sax;
    File file;
    private static final String FILENAME = "src\\data.xml";

    public XML_Manager(Model model) throws IOException, JDOMException {
        this.init_xml_file();
        this.model = model;
    }
    public void init_xml_file() throws IOException, JDOMException {
        this.sax = new SAXBuilder();
        file = new File(FILENAME);
        try {
            sax.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            sax.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            if (file.exists() && file.length() != 0){
                doc = sax.build(file);
            }
            else {
                doc = new Document(new Element("data"));
                doc.getRootElement().addContent(new Element("ciudades"));
                doc.getRootElement().addContent(new Element("vuelos"));
                //this.escribir_xml(System.out);
            }

        } catch (JDOMException | IOException e) {
            throw new RuntimeException(e);
        }
        // doc.setRootElement(new Element("Activos"));
    }

    public void write_vuelo(Vuelo obj, OutputStream output) throws JDOMException, IOException{

        Element vuelo = new Element("vuelo");

        Element numero = new Element("numero");
        numero.setText(obj.getNumero());
        vuelo.addContent(numero);

        Element origen = new Element("origen");
        origen.setText(obj.getOrigen());
        vuelo.addContent(origen);

        Element destino = new Element("destino");
        destino.setText(String.valueOf(obj.getDestino()));
        vuelo.addContent(destino);

        Element salida = new Element("salida");
        salida.setText(String.valueOf(obj.getSalida()));
        vuelo.addContent(salida);

        Element llegada = new Element("llegada");
        llegada.setText(String.valueOf(obj.getLlegada()));
        vuelo.addContent(llegada);

        Element dia = new Element("dia");
        dia.setText(String.valueOf(obj.isDia_siguiente()));
        vuelo.addContent(dia);

        doc.getRootElement().getChild("vuelos").addContent(vuelo);
        this.escribir_xml(output);


    }
    public void write_ciudad(Ciudad obj, OutputStream output) throws JDOMException, IOException{

        Element ciudad = new Element("ciudad");

        Element id = new Element("id");
        id.setText(obj.getId());
        ciudad.addContent(id);

        Element nombre = new Element("nombre");
        nombre.setText(obj.getNombre());
        ciudad.addContent(nombre);

        Element gmt = new Element("gmt");
        gmt.setText(String.valueOf(obj.getGmt()));
        ciudad.addContent(gmt);

        doc.getRootElement().getChild("ciudades").addContent(ciudad);
        this.escribir_xml(output);


    }
    public void cargar_vuelos() throws IOException, JDOMException {
        System.out.println("Ingreso al sector carga de datos");
        try {
            Element root = doc.getRootElement().getChild("vuelos");
            List<Element> lista_vuelos = root.getChildren("vuelo");
            //System.out.println(root.getName() + " padre\n");
            //System.out.println(lista_vuelos.size());

            for (Element element : lista_vuelos) {

                String numero = element.getChild("numero").getText();
                String origen = element.getChild("origen").getText();
                String destino = element.getChild("destino").getText();
                int salida = Integer.valueOf(element.getChild("salida").getText());
                int llegada = Integer.valueOf(element.getChild("llegada").getText());
                boolean dia_sig = Boolean.parseBoolean(element.getChild("dia").getText());
                Vuelo obj = new Vuelo(numero, origen, destino, salida, llegada, dia_sig);

                model.guardar_vuelo(obj, 0); //por default pero se actualiza al iniciar
            }
            //this.limpiar_xml();

        }catch (IOException | JDOMException e) {
            //System.out.println("Excepcion lanzada");
            throw new RuntimeException(e);
        }

    }

    public void cargar_ciudades() throws IOException, JDOMException {

        try {
            Element root = doc.getRootElement().getChild("ciudades");
            List<Element> lista_ciudades = root.getChildren("ciudad");
            System.out.println(root.getName() + " padre\n");
            System.out.println(lista_ciudades.size());

            for (Element element : lista_ciudades) {
                String id = element.getChild("id").getText();
                String nombre = element.getChild("nombre").getText();;
                int gmt = Integer.valueOf(element.getChild("gmt").getText());
                Ciudad city = new Ciudad(id, nombre, gmt);
                model.guardar_ciudad(city);
                //System.out.println("ciudad: "+city.toString()+"\n");
                model.getController().add_element_combo_box_ciudades(city.getNombre(), gmt);
            }
            //'this.limpiar_xml();

        }catch (IOException | JDOMException e) {
            //System.out.println("Excepcion lanzada");
            throw new RuntimeException(e);
        }

    }

    public void limpiar_xml(){
        Element root = doc.getRootElement().getChild("ciudades");
        root.removeChildren("ciudad");
        root = doc.getRootElement().getChild("vuelos");
        root.removeChildren("vuelo");
    }
    public void escribir_xml(OutputStream output) throws IOException {
        XMLOutputter xmlOutputter = new XMLOutputter();

        xmlOutputter.setFormat(Format.getPrettyFormat());
        xmlOutputter.output(doc, output);

        try(FileWriter fileWriter =
                    new FileWriter(FILENAME)){
            xmlOutputter.output(doc, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
