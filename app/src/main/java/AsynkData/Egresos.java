package AsynkData;

/**
 * Created by Mario on 19/10/2015.
 */
public class Egresos {
    private int id;
    private String comprobante;
    private String monto;
    private String fecha_egreso;
    private String fecha_reg;
    private String notas;
    private String tipo_egreso;
    private String tipo_proveedor;

    public Egresos(){}

    public Egresos(int id, String comprobante, String monto, String fecha_egreso, String fecha_reg, String notas, String tipo_egreso, String tipo_proveedor) {
        this.id = id;
        this.comprobante = comprobante;
        this.monto = monto;
        this.fecha_egreso = fecha_egreso;
        this.fecha_reg = fecha_reg;
        this.notas = notas;
        this.tipo_egreso = tipo_egreso;
        this.tipo_proveedor = tipo_proveedor;
    }

    public int getId() {
        return id;
    }

    public String getComprobante() {
        return comprobante;
    }

    public String getMonto() {
        return monto;
    }

    public String getFecha_egreso() {
        return fecha_egreso;
    }

    public String getFecha_reg() {
        return fecha_reg;
    }

    public String getNotas() {
        return notas;
    }

    public String getTipo_egreso() {
        return tipo_egreso;
    }

    public String getTipo_proveedor() {
        return tipo_proveedor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public void setFecha_egreso(String fecha_egreso) {
        this.fecha_egreso = fecha_egreso;
    }

    public void setFecha_reg(String fecha_reg) {
        this.fecha_reg = fecha_reg;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public void setTipo_egreso(String tipo_egreso) {
        this.tipo_egreso = tipo_egreso;
    }

    public void setTipo_proveedor(String tipo_proveedor) {
        this.tipo_proveedor = tipo_proveedor;
    }
}
