package dabiaani.com.medical;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.util.ArrayList;

import AsynkData.Consultas;
import AsynkData.ConsultasAdapter;
import AsynkData.Pacientes;
import AsynkData.ServiceHandler;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;


public class ConsultaHistorial extends AppCompatActivity {
    private ArrayList<Consultas> consultasList;
    String ID_DOCTOR="";
    Toolbar toolbar;
    private ArrayList<NameValuePair> registrosConsulta ;
    private String URLconsulta  = "http://www.meustech.com:8080/medical_new/modelo/db/DBGetHistorialConsultaAndroid.php";
    private String idpaciente = "";
    private ProgressDialog pDialog;
    private String TAG= "Consulta Historial";

    private RecyclerView mRecyclerView;
    private ConsultasAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_historial);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GlobalClass g = GlobalClass.getInstance();
        ID_DOCTOR = g.getIddoctor();
        consultasList = new ArrayList<Consultas>();

        Bundle extras = getIntent().getExtras();
        idpaciente = extras.getString("idpaciente");

        mRecyclerView = (RecyclerView) findViewById(R.id.rcv_consultaHistorial);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ConsultasAdapter(consultasList);
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);

        new GetHistorialConsulta().execute();

    }

    private void historicoConsultas(){
     consultasList = new ArrayList<Consultas>();
        for (int i = 0; i<consultasList.size();i++){
            consultasList.add(new Consultas(
                    consultasList.get(i).getId(),
                    consultasList.get(i).getTipo(),
                    consultasList.get(i).getFecha(),
                    consultasList.get(i).getDolecia(),
                    consultasList.get(i).getDiagnostico(),
                    consultasList.get(i).getPeso(),
                    consultasList.get(i).getPresion(),
                    consultasList.get(i).getEstatura(),
                    consultasList.get(i).getRitmo_c(),
                    consultasList.get(i).getEstatus_pago(),
                    consultasList.get(i).getMonto_pago(),
                    consultasList.get(i).getTipo_pago(),
                    consultasList.get(i).getDescuento(),
                    consultasList.get(i).getMotivo_desc(),
                    consultasList.get(i).getSeguro(),
                    consultasList.get(i).getTratamiento(),
                    consultasList.get(i).getIddoctor(),
                    consultasList.get(i).getIdpaciente()));
        }

    }


    private class GetHistorialConsulta extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ConsultaHistorial.this);
            pDialog.setMessage("Cargando Datos");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
                historicoConsultas();
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler jsonParser = new ServiceHandler();
            registrosConsulta = new ArrayList<NameValuePair>();
            registrosConsulta.add(new BasicNameValuePair("iddoctor",ID_DOCTOR));
            registrosConsulta.add(new BasicNameValuePair("idpaciente",idpaciente));

            String json = jsonParser.makeServiceCall(URLconsulta, ServiceHandler.POST,registrosConsulta);


            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);

                    if (jsonObj != null) {
                        JSONArray registro = jsonObj.getJSONArray("get_historial_consulta");

                        for (int i = 0; i < registro.length(); i++) {
                            JSONObject obj = registro.getJSONObject(i);

                            consultasList.add( new Consultas(obj.getInt("id"),obj.getString("tipo"),obj.getString("fecha"),
                                    obj.getString("dolencia"),obj.getString("diagnostico"),obj.getString("peso"),obj.getString("presion"),obj.getString("estatura"),
                                    obj.getString("ritmo"),obj.getString("estatus_pago"),obj.getString("monto_pago"),obj.getString("tipo_pago"),obj.getString("descuento"),
                                    obj.getString("motivo"),obj.getString("seguro"),obj.getString("tratamiento"),obj.getString("iddoctor"),obj.getString("idpaciente")));
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
    }

}
