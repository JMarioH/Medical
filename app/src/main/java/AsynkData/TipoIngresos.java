package AsynkData;

/**
 * Created by Mario on 07/10/2015.
 */
public class TipoIngresos {
    private int id;
    private String nombre ;
    private int iddoctor ;
    public TipoIngresos(){}

    public TipoIngresos(int id, String nombre, int iddoctor){
        this.id= id;
        this.nombre = nombre;
        this.iddoctor = iddoctor;
    }
    //<editor-fold desc="Getters">
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public int getIddoctor() {
        return iddoctor;
    }
    //</editor-fold>
    //<editor-fold desc="Setters">
    public void setId(int id) {
        this.id = id;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setIddoctor(int iddoctor) {
        this.iddoctor = iddoctor;
    }
    //</editor-fold>
}
