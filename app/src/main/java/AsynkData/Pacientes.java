package AsynkData;

/**
 * Created by Mario on 07/10/2015.
 */
public class Pacientes {
    private int id;
    private String nombre;
    private String apellidos ;
    private String fecha_nac;
    private String tipo_sangre;
    private String tel;
    private String cel;
    private String correo;
    private String notas;
    private String hipertencion ;
    private String diabetes ;
    private String sexo ;
    private String antecedentes ;
    private String profesion ;
    public Pacientes(){}
    public Pacientes(int id, String nombre, String apellidos, String fecha_nac, String tipo_sangre,
                     String tel, String cel, String correo, String notas,String hipertencion,
                     String diabetes,String sexo,String antecedentes,String profesion) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fecha_nac = fecha_nac;
        this.tipo_sangre = tipo_sangre;
        this.tel = tel;
        this.cel = cel;
        this.correo = correo;
        this.notas = notas;

        this.hipertencion = hipertencion;
        this.diabetes = diabetes;
        this.sexo = sexo;
        this.antecedentes = antecedentes;
        this.profesion = profesion;


    }

    //<editor-fold desc="Getters">
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHipertencion() {
        return hipertencion;
    }

    public void setHipertencion(String hipertencion) {
        this.hipertencion = hipertencion;
    }

    public String getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(String diabetes) {
        this.diabetes = diabetes;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getFecha_nac() {
        return fecha_nac;
    }

    public String getTipo_sangre() {
        return tipo_sangre;
    }

    public String getTel() {
        return tel;
    }

    public String getCel() {
        return cel;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNotas() {
        return notas;
    }


    //</editor-fold>

    //<editor-fold desc="Setters">
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setFecha_nac(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public void setTipo_sangre(String tipo_sangre) {
        this.tipo_sangre = tipo_sangre;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
    //</editor-fold>
}


