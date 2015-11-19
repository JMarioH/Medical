package dabiaani.com.medical;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import AsynkData.Consultas;
import AsynkData.MontoCosulta;
import AsynkData.Response;
import AsynkData.ServiceHandler;
import AsynkData.TipoEgresos;
import AsynkData.TipoIngresos;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
/**
 * Created by Mario on 05/11/2015.
 */
public class Consulta extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Spinner sp_atencionConsulta ;
    private Spinner sp_costoConsulta ;
    private TextView tv_nuevoImporte;
    private ProgressDialog pDialog;
    private ProgressDialog pDialogConsulta;
    private ArrayList<MontoCosulta> montoList;
    private String URLatencion = "http://www.meustech.com:8080/medical_new/modelo/db/DBmontoConsultaAndroid.php";
    private String URLSetConsulta = "http://www.meustech.com:8080/medical_new/modelo/db/DBConsultaAltaAndroid.php";
    private String URLGetConsulta = "http://www.meustech.com:8080/medical_new/modelo/db/DBGetConsultaAndroid.php";


    private Toolbar toolbar;
    private String ID_DOCTOR ="";
    private String idpaciente;
    private String nomPaciente;

    private FloatingActionButton FAB ;
    private String TAG = "CONSULTA ALTA";
    private EditText consultaOtroCosto ;
    private EditText consultaAtencion ;
    private EditText consultaEstatura;
    private EditText consultaPeso ;
    private EditText consultaPresion;
    private EditText consultaPulso;
    private EditText consultaSintoma;
    private EditText consultaDiagnostico;
    private EditText consultaTratamiento;

    //* CardView DATOS PACIENTE */
    private TextView pacienteNombre ;
    private TextView pacienteEdad ;
    private TextView pacienteTipoS;
    private TextView pacientePeso;
    private TextView pacienteEstatura ;
    private TextView pacienteHipertencion ;
    private TextView pacienteDiabetes;
    private TextView pacienteFechaCon;
    private TextView pacienteImc;
    String edadPaciente;
    String sangrePaciente;
    String pesoPaciente;
    String estaturaPaciente;
    String hipertencionPaciente;
    String diabetesPaciente;
    String fechaConPaciente;
    String imcPaciente;

    /*formulario Consulta*/
    private String atencion;
    private String costo;
    private String otroCosto;
    private String estatura;
    private String peso;
    private String presion;
    private String ritmo;
    private String sintoma;
    private String diagnostico;
    private String tratamiento;

    private ArrayList<Response> response;
    private ArrayList<Consultas> consultaList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.consulta_alta);
        this.getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE
                        | View.SYSTEM_UI_FLAG_VISIBLE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GlobalClass globalClass = GlobalClass.getInstance();
        ID_DOCTOR = globalClass.getIddoctor();
        /*Recivo los valores del pacientes desde el adaptador */
        Intent extras = getIntent();
        nomPaciente = extras.getStringExtra("nompaciente");
        idpaciente = extras.getStringExtra("idpaciente");
        edadPaciente = extras.getStringExtra("edadpaciente");
        sangrePaciente = extras.getStringExtra("sangrepaciente");
        hipertencionPaciente = extras.getStringExtra("hiperpaciente");
        diabetesPaciente = extras.getStringExtra("diabetespaciente");

        if(hipertencionPaciente.equals("HIPERTENCION") ){
            hipertencionPaciente  = "SI";
        }else{
            hipertencionPaciente  = "NO";
        }
        if(diabetesPaciente.equals("DIABETES")){
            diabetesPaciente = "SI";
        }else{
            diabetesPaciente = "NO";
        }

        montoList = new ArrayList<>();
        consultaList = new ArrayList<>();

        sp_atencionConsulta = (Spinner) findViewById(R.id.sp_atencionConsulta);
        sp_costoConsulta = (Spinner)findViewById(R.id.sp_costoConsulta);
        tv_nuevoImporte = (TextView) findViewById(R.id.ETotroCosto);
        tv_nuevoImporte.setVisibility(View.GONE);
        consultaEstatura = (EditText) findViewById(R.id.ETestaturaPaciente);
        consultaPeso =  (EditText) findViewById(R.id.ETpesoPaciente);
        consultaPresion = (EditText) findViewById(R.id.ETpresionPaciente);
        consultaPulso = (EditText) findViewById(R.id.ETritmoCardPaciente);
        consultaSintoma = (EditText) findViewById(R.id.ETsintomas);
        consultaDiagnostico = (EditText) findViewById(R.id.ETdiagnostico);
        consultaTratamiento = (EditText) findViewById(R.id.ETtratamiento);
        /**Cardview DatosPaciente*/
        pacienteNombre = (TextView)findViewById(R.id.txt_nomPaciente);
        pacienteNombre.setText(nomPaciente);
        pacienteEdad = (TextView) findViewById(R.id.txt_edadPaciente);
        pacienteEdad.setText(edadPaciente);
        pacienteTipoS = (TextView) findViewById(R.id.txt_tipoSangrePaciente);
        pacienteTipoS.setText(sangrePaciente);
        pacientePeso = (TextView) findViewById(R.id.txt_pesoPaciente);

        pacienteEstatura = (TextView) findViewById(R.id.txt_Estatura);

        pacienteHipertencion = (TextView) findViewById(R.id.txt_hipertensionPaciente);
        pacienteHipertencion.setText(hipertencionPaciente);
        pacienteDiabetes = (TextView) findViewById(R.id.txt_diabetesPaciente);
        pacienteDiabetes.setText(diabetesPaciente);
        pacienteFechaCon = (TextView) findViewById(R.id.txt_ultimaconPaciente);

        pacienteImc = (TextView) findViewById(R.id.txt_imcPaciente);


        //**//
        spinerAtencion();
        new GetcostoConsulta().execute();
        new GetConsulta().execute();
        FAB = (FloatingActionButton) findViewById(R.id.btnConsulta);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //damos de alta la consulta
                new SetConsulta().execute();
                // si hay alta limpiamos los campos
                limpiaCampos();
            }
        });

        // TODO completar el spinner de atencion , por un valor numerico..

    }

    private void spinerCostoConsulta(){
        List<String> label = new ArrayList<>();
        for (int i = 0 ; i < montoList.size();i++){
            label.add(montoList.get(i).getNombre());
        }

        label.add("OTRO IMPORTE");
        label.add("SIN COSTO");
        ArrayAdapter<String> spinerAdapter = new ArrayAdapter<>(Consulta.this,
                android.R.layout.simple_spinner_item,label);
        spinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_costoConsulta.setAdapter(spinerAdapter);
        sp_costoConsulta.setOnItemSelectedListener(new costoPiker());

    }
    private void spinerAtencion(){
        List<String> label = new ArrayList<>();
        label.add("PERSONAL");
        label.add("TELEFONICA");
        label.add("DOMICILIARIA");
        ArrayAdapter<String> spinerAdapter = new ArrayAdapter<String>(Consulta.this,
            android.R.layout.simple_spinner_item,label);
        spinerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_atencionConsulta.setAdapter(spinerAdapter);
        sp_atencionConsulta.setSelection(0);
        sp_atencionConsulta.setOnItemSelectedListener(new CustomOnItemSelectedListener());

    }
    private void respConsulta(){
        for (int i = 0; i < response.size() ;i++){
            Toast.makeText(this,""+response.get(i).getMensaje(),Toast.LENGTH_SHORT).show();
        }
    }
    private void datosConsulta(){
        for (int i =0 ;i<consultaList.size();i++){

            pacientePeso.setText(consultaList.get(i).getPeso());
            pacienteEstatura.setText(consultaList.get(i).getEstatura());
            pacienteFechaCon.setText(consultaList.get(i).getFecha());
            float peso = Float.valueOf(consultaList.get(i).getPeso());
            float estatura = Float.valueOf(consultaList.get(i).getEstatura());
            float imc = peso/(estatura*estatura);
            pacienteImc.setText(""+imc);
        }
    }
    private void limpiaCampos(){

        consultaOtroCosto.getText().clear();
        consultaEstatura.getText().clear();
        consultaPeso.getText().clear();
        consultaPresion.getText().clear();
        consultaPulso.getText().clear();
        consultaSintoma.getText().clear();
        consultaDiagnostico.getText().clear();
        consultaTratamiento.getText().clear();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
    class GetcostoConsulta extends AsyncTask<Void, Void,Void>{
        ArrayList<NameValuePair> regPost;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Consulta.this);
            pDialog.setMessage("Cargando Datos");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler jsonParser = new ServiceHandler();
            regPost = new ArrayList<NameValuePair>();
            regPost.add(new BasicNameValuePair("iddoctor", ID_DOCTOR));
            String json = jsonParser.makeServiceCall(URLatencion, ServiceHandler.POST,regPost);
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray costos = jsonObj.getJSONArray("costo_consulta");
                        for (int i = 0; i < costos.length(); i++) {
                            JSONObject catObj = (JSONObject) costos.get(i);
                            MontoCosulta cat = new MontoCosulta(catObj.getInt("id"), catObj.getString("nombre"), catObj.getString("monto"));
                            montoList.add(cat);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
            return null;

        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
                spinerCostoConsulta();
            }
        }
    }

    public class costoPiker implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String seleccion = parent.getItemAtPosition(position).toString();
            switch (seleccion){
                case "OTRO IMPORTE":
                    tv_nuevoImporte.setVisibility(View.VISIBLE);
                    break;
                default:
                    tv_nuevoImporte.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class SetConsulta extends AsyncTask<Void,Void,Void>{
        ArrayList<NameValuePair> consultaDataPsot ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Consulta.this);
            pDialog.setMessage("Guardando Datos del Ingreso ");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog.isShowing()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
                respConsulta();

            }
        }
        @Override
        protected Void doInBackground(Void... params) {
            response = new ArrayList<>();
            atencion = String.valueOf(sp_atencionConsulta.getSelectedItem());
            costo = String.valueOf(sp_costoConsulta.getSelectedItem());
            String monto_consulta = "";
            switch (costo){
                case "OTRO IMPORTE" :
                    monto_consulta= tv_nuevoImporte.getText().toString();
                    break;
                case "SIN COSTO":
                    monto_consulta= "0";
                    break;
                default:
                    monto_consulta ="00";
                    break;
            }

            estatura = consultaEstatura.getText().toString();
            peso = consultaPeso.getText().toString();
            presion = consultaPresion.getText().toString();
            ritmo = consultaPulso.getText().toString();
            sintoma = consultaSintoma.getText().toString();
            diagnostico = consultaDiagnostico.getText().toString();
            tratamiento = consultaTratamiento.getText().toString();
            consultaDataPsot = new ArrayList<NameValuePair>();

            consultaDataPsot.add(new BasicNameValuePair("tipo",atencion));
            consultaDataPsot.add(new BasicNameValuePair("tipo_pago",costo));
            consultaDataPsot.add(new BasicNameValuePair("monto_pago",monto_consulta));
            consultaDataPsot.add(new BasicNameValuePair("peso",peso));
            consultaDataPsot.add(new BasicNameValuePair("presion",presion));
            consultaDataPsot.add(new BasicNameValuePair("estatura",estatura));
            consultaDataPsot.add(new BasicNameValuePair("ritmo",ritmo));
            consultaDataPsot.add(new BasicNameValuePair("dolencia",sintoma));
            consultaDataPsot.add(new BasicNameValuePair("diagnostico",diagnostico));
            consultaDataPsot.add(new BasicNameValuePair("tratamiento",tratamiento));
            consultaDataPsot.add(new BasicNameValuePair("iddoctor",ID_DOCTOR));
            consultaDataPsot.add(new BasicNameValuePair("idpaciente",idpaciente));

            ServiceHandler jsonIng = new ServiceHandler();

            String respJson = jsonIng.makeServiceCall(URLSetConsulta, ServiceHandler.POST, consultaDataPsot);
            Log.d(TAG, respJson.toString());
            try {
                JSONObject Obj = new JSONObject(respJson);
                JSONArray consulta = Obj.getJSONArray("consulta_alta");
                for (int i = 0 ; i < consulta.length(); i++){

                    JSONObject obj = (JSONObject)consulta.get(i);
                    Response respuesta = new Response(obj.getString("success"),obj.getString("message"));

                    response.add(respuesta);
                }



            } catch (JSONException e) {
                e.printStackTrace();

            }
            return null;


        }
    }

    class GetConsulta extends AsyncTask<Void,Void,Void>{
        ArrayList<NameValuePair> datosPacienteConsulta;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogConsulta = new ProgressDialog(Consulta.this);
            pDialogConsulta.setMessage("Cargando Datos.. ");
            pDialogConsulta.setCancelable(false);
            pDialogConsulta.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialogConsulta.isShowing()) {
                pDialogConsulta.dismiss();
                datosConsulta();

            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            datosPacienteConsulta = new ArrayList<NameValuePair>();
            datosPacienteConsulta.add(new BasicNameValuePair("iddoctor",ID_DOCTOR));
            datosPacienteConsulta.add(new BasicNameValuePair("idpaciente",idpaciente));
            ServiceHandler json = new ServiceHandler();

            String respJson = json.makeServiceCall(URLGetConsulta, ServiceHandler.POST, datosPacienteConsulta);
            Log.d(TAG, respJson.toString());
            try {
                JSONObject Obj = new JSONObject(respJson);
                JSONArray consultas = Obj.getJSONArray("get_consulta");
                for (int i = 0 ; i < consultas.length(); i++){

                    JSONObject obj = (JSONObject)consultas.get(i);
                    consultaList.add( new Consultas(obj.getInt("id"),obj.getString("tipo"),obj.getString("fecha"),
                            obj.getString("dolencia"),obj.getString("diagnostico"),obj.getString("peso"),obj.getString("presion"),obj.getString("estatura"),
                            obj.getString("ritmo"),obj.getString("estatus_pago"),obj.getString("monto_pago"),obj.getString("tipo_pago"),obj.getString("descuento"),
                            obj.getString("motivo"),obj.getString("seguro"),obj.getString("tratamiento"),obj.getString("iddoctor"),obj.getString("idpaciente")));
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
            return null;
        }
    }

}
