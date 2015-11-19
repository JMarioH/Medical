package dabiaani.com.medical;
import android.app.Application;
/**
 * Created by Mario on 26/10/2015.
 */
public class GlobalClass {

    private static GlobalClass instance;

//Variables
    private String iddoctor  ;
    private String nomDoctor ;

    private GlobalClass(){}

    public String getIddoctor() {
        return iddoctor;
    }
    public String getNomDoctor() {
        return nomDoctor;
    }

    public void setIddoctor(String iddoctor) {
        this.iddoctor = iddoctor;
    }

    public void setNomDoctor(String nomDoctor) {
        this.nomDoctor = nomDoctor;
    }

    public static synchronized GlobalClass getInstance(){
        if(instance == null){
            instance = new GlobalClass();
        }
        return instance;
    }

}
