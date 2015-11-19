package AsynkData;

/**
 * Created by Mario on 12/11/2015.
 */
public class Consultas {

    private int id;
    private String tipo;
    private String fecha ;
    private String dolecia ;
    private String diagnostico ;
    private String peso ;
    private String presion ;
    private String estatura ;
    private String ritmo_c;
    private String estatus_pago ;
    private String monto_pago ;
    private String tipo_pago;
    private String descuento;
    private String motivo_desc ;
    private String seguro ;
    private String tratamiento ;
    private String iddoctor;
    private String idpaciente;

    public Consultas(int id, String tipo, String fecha, String dolecia, String diagnostico, String peso,
                     String presion, String estatura, String ritmo_c, String estatus_pago, String monto_pago,
                     String tipo_pago, String descuento, String motivo_desc, String seguro, String tratamiento,
                     String iddoctor, String idpaciente) {
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.dolecia = dolecia;
        this.diagnostico = diagnostico;
        this.peso = peso;
        this.presion = presion;
        this.estatura = estatura;
        this.ritmo_c = ritmo_c;
        this.estatus_pago = estatus_pago;
        this.monto_pago = monto_pago;
        this.tipo_pago = tipo_pago;
        this.descuento = descuento;
        this.motivo_desc = motivo_desc;
        this.seguro = seguro;
        this.tratamiento = tratamiento;
        this.iddoctor = iddoctor;
        this.idpaciente = idpaciente;
    }

    //<editor-fold desc="Getters">
    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDolecia() {
        return dolecia;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getPeso() {
        return peso;
    }

    public String getPresion() {
        return presion;
    }

    public String getEstatura() {
        return estatura;
    }

    public String getRitmo_c() {
        return ritmo_c;
    }

    public String getEstatus_pago() {
        return estatus_pago;
    }

    public String getMonto_pago() {
        return monto_pago;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public String getDescuento() {
        return descuento;
    }

    public String getMotivo_desc() {
        return motivo_desc;
    }

    public String getSeguro() {
        return seguro;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public String getIddoctor() {
        return iddoctor;
    }

    public String getIdpaciente() {
        return idpaciente;
    }
    //</editor-fold>
}
