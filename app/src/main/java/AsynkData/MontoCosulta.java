package AsynkData;

/**
 * Created by Mario on 09/11/2015.
 */
public class MontoCosulta {
    private int id;
    private String nombre;
    private String monto;

    public MontoCosulta(int id, String nombre, String monto) {
        this.id = id;
        this.nombre = nombre;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMonto() {
        return monto;
    }
}
