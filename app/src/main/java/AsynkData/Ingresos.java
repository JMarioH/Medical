package AsynkData;

/**
 * Created by Mario on 15/10/2015.
 */
public class Ingresos {

    private int id;
    private String monto ;
    private String fecha ;
    private String notas ;
    private String tipo_ingreso ;
    private int iddoctor ;
    private int idcirugia;
    private int idconsulta ;
    public Ingresos(){}
    public Ingresos(int id, String monto, String fecha, String notas, String tipo_ingreso, int iddoctor, int idcirugia, int idconsulta) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.notas = notas;
        this.tipo_ingreso = tipo_ingreso;
        this.iddoctor = iddoctor;
        this.idcirugia = idcirugia;
        this.idconsulta = idconsulta;
    }

    //<editor-fold desc="Getters">
    public int getId() { return id; }

    public String getMonto() {
        return monto;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNotas() {
        return notas;
    }

    public String getTipo_ingreso() {
        return tipo_ingreso;
    }

    public int getIddoctor() {
        return iddoctor;
    }

    public int getIdcirugia() {
        return idcirugia;
    }

    public int getIdconsulta() {
        return idconsulta;
    }
    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setId(int id) {
        this.id = id;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public void setTipo_ingreso(String tipo_ingreso) {
        this.tipo_ingreso = tipo_ingreso;
    }

    public void setIddoctor(int iddoctor) {
        this.iddoctor = iddoctor;
    }

    public void setIdcirugia(int idcirugia) {
        this.idcirugia = idcirugia;
    }

    public void setIdconsulta(int idconsulta) {
        this.idconsulta = idconsulta;
    }
    //</editor-fold>
}
