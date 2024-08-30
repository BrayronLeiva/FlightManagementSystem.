package Model;

import java.util.ArrayList;
import java.util.List;

public class Lista_Ciudades {
    static List<Ciudad> list = new ArrayList<>();
    public List<Ciudad> getList() {
        return list;
    }
    public void setList(List<Ciudad> list){
        list = list;
    }
    public void agregar (Ciudad ins){list.add(ins);}

    public Ciudad obtener(String str) {
        for (Ciudad object : list) {
            if (object.getId().equals(str)) {
                return object;
            }
        }
        return null;
    }
    public Ciudad obtener_x_nombre(String str) {
        for (Ciudad object : list) {
            if (object.getNombre().equals(str)) {
                return object;
            }
        }
        return null;
    }
}