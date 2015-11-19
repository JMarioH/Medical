package AsynkData;

/**
 * Created by Mario on 10/11/2015.
 */
public class Response {

    private String success;
    private String mensaje;

    public Response(String success, String mensaje) {
        this.success = success;
        this.mensaje = mensaje;
    }

    public String getSuccess() {
        return success;
    }

    public String getMensaje() {
        return mensaje;
    }
}
