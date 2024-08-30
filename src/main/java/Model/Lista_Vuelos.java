package Model;


import java.text.Collator;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Lista_Vuelos {
    static List<Vuelo> list = new ArrayList<>();
    public List<Vuelo> getList() {
        return list;
    }
    public void setList(List<Vuelo> list){
        list = list;
    }
    public void agregar (Vuelo ins){list.add(ins);}

    public Vuelo obtener(String str) {
        for (Vuelo object : list) {
            if (object.getNumero().equals(str)) {
                return object;
            }
        }
        return null;
    }
    public Vuelo obtener_x_ciudad(String str) {
        List<Vuelo> results = new ArrayList<>();
        for (Vuelo object : list) {
            if (object.getDestino().equals(str) || object.getOrigen().equals(str)) {
               return object;
            }
        }
        return null;
    }


    public String normalizar(String input) { //quita tildes y convierte a minuscula

        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalizedString.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
    }

    public List<Vuelo> obtener_x_ciudad_lista(String str) {

        String cadenaNormalizada = normalizar(str);
        String destino = "";
        String origen = "";

        List<Vuelo> results = new ArrayList<>();
        System.out.println("Cadena normalizada "+ cadenaNormalizada);
        for (Vuelo object : list) {
            destino = normalizar(object.getDestino());
            origen = normalizar(object.getOrigen());
            if (destino.contains(cadenaNormalizada)|| origen.contains(cadenaNormalizada)) {
                results.add(object);
            }
        }
        return results;
    }

}