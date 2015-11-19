package AsynkData;

/**
 * Created by Mario on 03/11/2015.
 */
public class Alergias {
    private int  id ;
    private String nombre ;
    private String notas;
    private String tipo ;

    public Alergias(){}


    public Alergias(int id, String nombre, String notas, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.notas = notas;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNotas() {
        return notas;
    }

    public String getTipo() {
        return tipo;
    }
}
